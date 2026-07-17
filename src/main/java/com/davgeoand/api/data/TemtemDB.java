package com.davgeoand.api.data;

import com.davgeoand.api.ServiceProperties;
import com.davgeoand.api.model.temtem.Technique;
import com.davgeoand.api.model.temtem.Temtem;
import com.davgeoand.api.model.temtem.TemtemDetail;
import com.davgeoand.api.model.temtem.battle.Battle;
import com.davgeoand.api.model.temtem.battle.BattleDetail;
import com.davgeoand.api.model.temtem.battle.TeamTemtem;
import com.surrealdb.*;
import com.surrealdb.signin.RootCredential;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class TemtemDB {
    public final Surreal driver;
    private final String SURREALDB_CONNECT = ServiceProperties.getProperty("surrealdb.connect");
    private final String SURREALDB_NAMESPACE = ServiceProperties.getProperty("surrealdb.namespace");
    private final String SURREALDB_USERNAME = ServiceProperties.getProperty("surrealdb.username");
    private final String SURREALDB_PASSWORD = ServiceProperties.getProperty("surrealdb.password");

    public TemtemDB() {
        try {
            log.info("Initializing Temtem db");
            driver = new Surreal();
            connect();
            log.debug("SurrealDB Server Version: {}", driver.version());
            log.info("Initialized Temtem db");
        } catch (Exception exception) {
            log.error("Issue initializing Temtem db", exception);
            throw new RuntimeException();
        }
    }

    @WithSpan(kind = SpanKind.CLIENT)
    private void connect() {
        log.info("Connecting to Temtem db");
        driver.connect(SURREALDB_CONNECT)
                .useNs(SURREALDB_NAMESPACE)
                .useDb("temtem")
                .signin(new RootCredential(SURREALDB_USERNAME, SURREALDB_PASSWORD));
        log.info("Connected to Temtem db");
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public Iterator<Temtem> allTemtems() {
        return driver.select(Temtem.class, "temtems");
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public Iterator<Technique> allTechniques() {
        return driver.select(Technique.class, "techniques");
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public Iterator<Battle> allBattles() {
        return driver.select(Battle.class, "battles");
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public Optional<Temtem> temtem(String id) {
        log.debug("id - {}", id);
        return driver.select(Temtem.class, new RecordId("temtems", id));
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public TemtemDetail temtemDetail(RecordId id) {
        log.debug("id - {}", id);
        Response response = driver.query(
                "SELECT *, (SELECT level, out[*] AS technique FROM $parent->temtem_levelTechniques ORDER BY level\n" +
                        ") AS levelTechniques, ->temtem_courseTechniques.out[*] AS courseTechniques FROM ONLY $id;",
                Map.of("id", id));
        return response.take(0).get(TemtemDetail.class);
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public Temtem updateTemtem(Temtem temtem) {
        log.debug("temtem - {}", temtem);
        return driver.update(Temtem.class, temtem.getId(), UpType.CONTENT, temtem);
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public Optional<Technique> technique(String id) {
        log.debug("id - {}", id);
        return driver.select(Technique.class, new RecordId("techniques", id));
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public Technique updateTechnique(Technique technique) {
        log.debug("technique - {}", technique);
        return driver.update(Technique.class, technique.getId(), UpType.CONTENT, technique);
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public Optional<Battle> battle(String id) {
        log.debug("id - {}", id);
        return driver.select(Battle.class, new RecordId("battles", id));
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public BattleDetail battleDetail(RecordId id) {
        log.debug("id - {}", id);
        Response response = driver.query(
                """
                        SELECT *,
                            (SELECT level,
                            out[*] AS temtem
                            FROM $parent->battle_temtems
                            ORDER BY level) AS levelTemtems
                        FROM ONLY $id;""",
                Map.of("id", id));
        return response.take(0).get(BattleDetail.class);
    }

    public Iterator<TeamTemtem> teamTemtems(int level) {
        log.debug("level - {}", level);
        Response response = driver.query(
                """
                        SELECT *,
                        array::concat(
                            ->(temtem_levelTechniques WHERE level <= $level AND out.damage > 0).out[*],
                            ->(temtem_courseTechniques WHERE out.courseStatus == true AND out.damage > 0).out[*]) AS techniques
                        FROM temtems
                        WHERE teamStatus == true;""",
                Map.of("level", level));
        Array results = response.take(0).getArray();
        log.debug("results - {}", results);
        return results.iterator(TeamTemtem.class);
    }
}
