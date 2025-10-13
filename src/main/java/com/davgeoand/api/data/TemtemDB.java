package com.davgeoand.api.data;

import com.davgeoand.api.helper.Constants;
import com.davgeoand.api.model.temtem.Battle;
import com.davgeoand.api.model.temtem.Technique;
import com.davgeoand.api.model.temtem.Temtem;
import com.surrealdb.RecordId;
import com.surrealdb.Surreal;
import com.surrealdb.signin.Root;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Optional;

@Slf4j
public class TemtemDB {
    private final Surreal driver;

    public TemtemDB() {
        log.info("Initializing temtem db");
        driver = new Surreal();
        driver.connect(Constants.SURREALDB_CONNECT)
                .useNs(Constants.SURREALDB_NAMESPACE)
                .useDb("temtem")
                .signin(new Root(Constants.SURREALDB_USERNAME, Constants.SURREALDB_PASSWORD));
        log.info("Initialized temtem db");
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public Iterator<Temtem> allTemtems() {
        return driver.select(Temtem.class, "temtems");
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public Temtem createItem(Temtem temtem) {
        log.debug("temtem - {}", temtem);
        return driver.create(Temtem.class, new RecordId("temtems", temtem.getName()), temtem);
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public Optional<Temtem> temtem(String id) {
        log.debug("id - {}", id);
        return driver.select(Temtem.class, new RecordId("temtems", id));
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public Iterator<Technique> allTechniques() {
        return driver.select(Technique.class, "techniques");
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public Technique createTechnique(Technique technique) {
        log.debug("technique - {}", technique);
        return driver.create(Technique.class, new RecordId("techniques", technique.getName()), technique);
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public Optional<Technique> technique(String id) {
        log.debug("id - {}", id);
        return driver.select(Technique.class, new RecordId("techniques", id));
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public Iterator<Battle> allBattles() {
        return driver.select(Battle.class, "battles");
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public Battle createBattle(Battle battle) {
        log.debug("battle - {}", battle);
        return driver.create(Battle.class, new RecordId("battles", battle.getName()), battle);
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public Optional<Battle> battle(String id) {
        log.debug("id - {}", id);
        return driver.select(Battle.class, new RecordId("battles", id));
    }
}
