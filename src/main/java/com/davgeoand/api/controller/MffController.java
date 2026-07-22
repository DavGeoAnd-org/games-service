package com.davgeoand.api.controller;

import com.davgeoand.api.model.mff.Character;
import com.davgeoand.api.model.mff.ShadowlandLevel;
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
                post(MffController::addCharacter);
                path("{id}", () -> {
                    put(MffController::updateCharacter);
                });
            });
            path("shadowlands", () -> {
                get(MffController::allShadowlands);
                post(MffController::addShadowland);
                get("characters", MffController::allCharactersForShadowland);
                path("{id}", () -> {
                    put("addLevel", MffController::addShadowlandLevel);
                    post("finish", MffController::finishShadowland);
                    get(MffController::shadowland);
                });
            });
        };
    }

    private static void shadowland(@NotNull Context context) {
        log.debug("{} - {}", context.method(), context.path());
        long id = Long.parseLong(context.pathParam("id"));
        log.debug("id: {}", id);
        context.json(mffService.shadowland((id)))
                .status(HttpStatus.OK);
    }

    private static void finishShadowland(@NotNull Context context) {
        log.debug("{} - {}", context.method(), context.path());
        long id = Long.parseLong(context.pathParam("id"));
        log.debug("id: {}", id);
        mffService.finishShadowland(id);
        context.json(new MessageResponse("Finished Shadowland"))
                .status(HttpStatus.OK);
    }

    private static void addShadowlandLevel(@NotNull Context context) {
        log.debug("{} - {}", context.method(), context.path());
        long id = Long.parseLong(context.pathParam("id"));
        log.debug("id: {}", id);
        ShadowlandLevel shadowlandLevel = context.bodyAsClass(ShadowlandLevel.class);
        log.debug("shadowlandLevel: {}", shadowlandLevel);
        mffService.addShadowlandLevel(id, shadowlandLevel);
        context.json(new MessageResponse("Updated Shadowland"))
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
        boolean appQueryParam = Boolean.parseBoolean(StringUtils.defaultIfBlank(context.queryParam("app"), "false"));
        log.debug("appQueryParam: {}", appQueryParam);
        context.json((appQueryParam) ? mffService.allShadowlandsApp() : mffService.allShadowlands())
                .status(HttpStatus.OK);
    }

    private static void updateCharacter(@NotNull Context context) {
        log.debug("{} - {}", context.method(), context.path());
        String id = context.pathParam("id");
        log.debug("id: {}", id);
        Character character = context.bodyAsClass(Character.class);
        log.debug("character: {}", character);
        mffService.updateCharacter(id, character);
        context.json(new MessageResponse("Updated Character"))
                .status(HttpStatus.OK);
    }

    private static void addCharacter(@NotNull Context context) {
        log.debug("{} - {}", context.method(), context.path());
        Character character = context.bodyAsClass(Character.class);
        log.debug("character: {}", character);
        context.json(new RecordIdResponse("Added Character", mffService.addCharacter(character)))
                .status(HttpStatus.CREATED);
    }

    private static void allCharacters(@NotNull Context context) {
        log.debug("{} - {}", context.method(), context.path());
        boolean appQueryParam = Boolean.parseBoolean(StringUtils.defaultIfBlank(context.queryParam("app"), "false"));
        log.debug("appQueryParam: {}", appQueryParam);
        context.json((appQueryParam) ? mffService.allCharactersApp() : mffService.allCharacters())
                .status(HttpStatus.OK);
    }
}
