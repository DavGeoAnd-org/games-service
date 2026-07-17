package com.davgeoand.api.controller;

import com.davgeoand.api.model.mff.Character;
import com.davgeoand.api.model.mff.Shadowland;
import com.davgeoand.api.model.response.MessageResponse;
import com.davgeoand.api.model.response.RecordIdResponse;
import com.davgeoand.api.service.MffService;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import static io.javalin.apibuilder.ApiBuilder.*;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MffController {
    public final static MffService mffService = new MffService();

    public static @NotNull EndpointGroup getMffEndpoints() {
        return () -> {
            path("characters", () -> {
                get(MffController::allCharacters);
                put(MffController::updateCharacter);
                post(MffController::addCharacter);
            });
            path("shadowlands", () -> {
                get(MffController::allShadowlands);
                post(MffController::addShadowland);
                get("characters", MffController::allCharactersForShadowland);
                put(MffController::updateShadowland);
            });
        };
    }

    private static void updateShadowland(@NotNull Context context) {
        log.debug("{} - {}", context.method(), context.path());
        Shadowland shadowland = context.bodyAsClass(Shadowland.class);
        log.debug("shadowland - {}", shadowland);
        context.json(new RecordIdResponse("Updated Shadowland", mffService.updateShadowland(shadowland)))
                .status(HttpStatus.OK);
    }

    private static void allCharactersForShadowland(@NotNull Context context) {
        log.debug("{} - {}", context.method(), context.path());
        context.json(mffService.allCharactersForShadowland())
                .status(HttpStatus.OK);
    }

    private static void addShadowland(@NotNull Context context) {
        log.debug("{} - {}", context.method(), context.path());
        mffService.addShadowland();
        context.json(new MessageResponse("Added Shadowland"))
                .status(HttpStatus.CREATED);
    }

    private static void allShadowlands(@NotNull Context context) {
        log.debug("{} - {}", context.method(), context.path());
        context.json(mffService.allShadowlands())
                .status(HttpStatus.OK);
    }

    private static void addCharacter(@NotNull Context context) {
        log.debug("{} - {}", context.method(), context.path());
        Character character = context.bodyAsClass(Character.class);
        log.debug("character - {}", character);
        context.json(new RecordIdResponse("Added Character", mffService.addCharacter(character)))
                .status(HttpStatus.CREATED);
    }

    private static void updateCharacter(@NotNull Context context) {
        log.debug("{} - {}", context.method(), context.path());
        Character character = context.bodyAsClass(Character.class);
        log.debug("character - {}", character);
        context.json(new RecordIdResponse("Updated Character", mffService.updateCharacter(character)))
                .status(HttpStatus.OK);
    }

    private static void allCharacters(@NotNull Context context) {
        log.debug("{} - {}", context.method(), context.path());
        boolean appQueryParam = Boolean.parseBoolean(StringUtils.defaultIfBlank(context.queryParam("app"), "false"));
        log.debug("appQueryParam - {}", appQueryParam);
        context.json((appQueryParam) ? mffService.allCharactersApp() : mffService.allCharacters())
                .status(HttpStatus.OK);
    }
}
