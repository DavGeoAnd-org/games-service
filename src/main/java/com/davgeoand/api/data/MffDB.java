package com.davgeoand.api.data;

import com.davgeoand.api.ServiceProperties;
import com.davgeoand.api.model.mff.Character;
import com.davgeoand.api.model.mff.Shadowland;
import com.surrealdb.RecordId;
import com.surrealdb.Surreal;
import com.surrealdb.UpType;
import com.surrealdb.signin.RootCredential;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Optional;

@Slf4j
public class MffDB {
    public final Surreal driver;
    private final String SURREALDB_CONNECT = ServiceProperties.getProperty("surrealdb.connect");
    private final String SURREALDB_NAMESPACE = ServiceProperties.getProperty("surrealdb.namespace");
    private final String SURREALDB_USERNAME = ServiceProperties.getProperty("surrealdb.username");
    private final String SURREALDB_PASSWORD = ServiceProperties.getProperty("surrealdb.password");

    public MffDB() {
        try {
            log.info("Initializing Marvel Future Fight db");
            driver = new Surreal();
            connect();
            log.debug("SurrealDB Server Version: {}", driver.version());
            log.info("Initialized Marvel Future Fight db");
        } catch (Exception exception) {
            log.error("Issue initializing Marvel Future Fight db", exception);
            throw new RuntimeException();
        }
    }

    @WithSpan(kind = SpanKind.CLIENT)
    private void connect() {
        log.info("Connecting to Marvel Future Fight db");
        driver.connect(SURREALDB_CONNECT).useNs(SURREALDB_NAMESPACE).useDb("marvelfuturefight").signin(new RootCredential(SURREALDB_USERNAME, SURREALDB_PASSWORD));
        log.info("Connected to Marvel Future Fight db");
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public Iterator<Character> allCharacters() {
        return driver.select(Character.class, "characters");
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public Character addCharacter(Character character) {
        log.debug("character: {}", character);
        return driver.create(Character.class, new RecordId("characters", character.getName()), character);
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public Optional<Character> character(String id) {
        log.debug("id: {}", id);
        return driver.select(Character.class, new RecordId("characters", id));
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public void updateCharacter(Character character) {
        log.debug("character: {}", character);
        driver.update(Character.class, character.getId(), UpType.CONTENT, character);
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public Iterator<Shadowland> allShadowlands() {
        return driver.select(Shadowland.class, "shadowlands");
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public void addShadowland(Shadowland shadowland) {
        log.debug("shadowland - {}", shadowland);
        driver.create(shadowland.getId(), shadowland);
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public Optional<Shadowland> shadowland(long id) {
        log.debug("id: {}", id);
        return driver.select(Shadowland.class, new RecordId("shadowlands", id));
    }

    @WithSpan(kind = SpanKind.CLIENT)
    public void updateShadowland(Shadowland shadowland) {
        log.debug("shadowland - {}", shadowland);
        driver.update(Shadowland.class, shadowland.getId(), UpType.CONTENT, shadowland);
    }
}
