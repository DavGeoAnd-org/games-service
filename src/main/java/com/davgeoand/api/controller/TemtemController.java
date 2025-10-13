package com.davgeoand.api.controller;

import com.davgeoand.api.model.temtem.Battle;
import com.davgeoand.api.model.temtem.Technique;
import com.davgeoand.api.model.temtem.Temtem;
import com.davgeoand.api.service.TemtemService;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import static io.javalin.apibuilder.ApiBuilder.*;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TemtemController {
    public final static TemtemService temtemService = new TemtemService();

    public static @NotNull EndpointGroup getTemtemEndpoints() {
        return () -> {
            path("temtems", () -> {
                get(TemtemController::allTemtems);
                post(TemtemController::createTemtem);
                path("{id}", () -> get(TemtemController::temtem));
            });
            path("techniques", () -> {
                get(TemtemController::allTechniques);
                post(TemtemController::createTechnique);
                path("{id}", () -> get(TemtemController::technique));
            });
            path("battles", () -> {
                get(TemtemController::allBattles);
                post(TemtemController::createBattle);
                path("{id}", () -> get(TemtemController::battle));
            });
        };
    }

    private static void battle(@NotNull Context context) {
        String id = context.pathParam("id");
        context.json(temtemService.battle(id));
        context.status(HttpStatus.OK);
    }

    private static void createBattle(@NotNull Context context) {
        Battle battle = context.bodyAsClass(Battle.class);
        context.json("Created: " + temtemService.createBattle(battle).getId());
        context.status(HttpStatus.CREATED);
    }

    private static void allBattles(@NotNull Context context) {
        context.json(temtemService.allBattles());
        context.status(HttpStatus.OK);
    }

    private static void technique(@NotNull Context context) {
        String id = context.pathParam("id");
        context.json(temtemService.technique(id));
        context.status(HttpStatus.OK);
    }

    private static void createTechnique(@NotNull Context context) {
        Technique technique = context.bodyAsClass(Technique.class);
        context.json("Created: " + temtemService.createTechnique(technique).getId());
        context.status(HttpStatus.CREATED);
    }

    private static void allTechniques(@NotNull Context context) {
        context.json(temtemService.allTechniques());
        context.status(HttpStatus.OK);
    }

    private static void temtem(@NotNull Context context) {
        String id = context.pathParam("id");
        context.json(temtemService.temtem(id));
        context.status(HttpStatus.OK);
    }

    private static void createTemtem(@NotNull Context context) {
        Temtem temtem = context.bodyAsClass(Temtem.class);
        context.json("Created: " + temtemService.createTemtem(temtem).getId());
        context.status(HttpStatus.CREATED);
    }

    private static void allTemtems(@NotNull Context context) {
        context.json(temtemService.allTemtems());
        context.status(HttpStatus.OK);
    }
}
