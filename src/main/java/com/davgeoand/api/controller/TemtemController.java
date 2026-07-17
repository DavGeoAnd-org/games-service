package com.davgeoand.api.controller;

import com.davgeoand.api.model.response.RecordIdResponse;
import com.davgeoand.api.service.TemtemService;
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
public class TemtemController {
    public final static TemtemService temtemService = new TemtemService();

    public static @NotNull EndpointGroup getTemtemEndpoints() {
        return () -> {
            path("temtems", () -> {
                get(TemtemController::allTemtems);
                path("{id}", () -> {
                    get(TemtemController::temtem);
                    put("teamStatus", TemtemController::updateTeamStatus);
                });
            });
            path("techniques", () -> {
                get(TemtemController::allTechniques);
                path("{id}", () -> {
                    get(TemtemController::technique);
                    put("courseStatus", TemtemController::updateCourseStatus);
                });
            });
            path("battles", () -> {
                get(TemtemController::allBattles);
                path("{id}", () -> {
                    get(TemtemController::battle);
                    get("teamSetup", TemtemController::teamSetup);
                });
            });
        };
    }

    private static void technique(@NotNull Context context) {
        log.debug("{} - {}", context.method(), context.path());
        String id = context.pathParam("id");
        log.debug("id - {}", id);
        context.json(temtemService.technique(id))
                .status(HttpStatus.OK);
    }

    private static void teamSetup(@NotNull Context context) {
        log.debug("{} - {}", context.method(), context.path());
        String id = context.pathParam("id");
        log.debug("id - {}", id);
        context.json(temtemService.teamSetup(id))
                .status(HttpStatus.OK);
    }

    private static void battle(@NotNull Context context) {
        log.debug("{} - {}", context.method(), context.path());
        String id = context.pathParam("id");
        log.debug("id - {}", id);
        boolean detailQueryParam = Boolean.parseBoolean(StringUtils.defaultIfBlank(context.queryParam("detail"), "false"));
        log.debug("detailQueryParam - {}", detailQueryParam);
        context.json((detailQueryParam) ? temtemService.battleDetail(id) : temtemService.battle(id))
                .status(HttpStatus.OK);
    }

    private static void updateCourseStatus(@NotNull Context context) {
        log.debug("{} - {}", context.method(), context.path());
        String id = context.pathParam("id");
        log.debug("id - {}", id);
        boolean courseStatus = context.bodyAsClass(boolean.class);
        log.debug("courseStatus - {}", courseStatus);
        context.json(new RecordIdResponse("Updated course status to " + courseStatus, temtemService.updateCourseStatus(id, courseStatus).getId()))
                .status(HttpStatus.OK);
    }

    private static void updateTeamStatus(@NotNull Context context) {
        log.debug("{} - {}", context.method(), context.path());
        String id = context.pathParam("id");
        log.debug("id - {}", id);
        boolean teamStatus = context.bodyAsClass(boolean.class);
        log.debug("teamStatus - {}", teamStatus);
        context.json(new RecordIdResponse("Updated team status to " + teamStatus, temtemService.updateTeamStatus(id, teamStatus).getId()))
                .status(HttpStatus.OK);
    }

    private static void temtem(@NotNull Context context) {
        log.debug("{} - {}", context.method(), context.path());
        String id = context.pathParam("id");
        log.debug("id - {}", id);
        boolean detailQueryParam = Boolean.parseBoolean(StringUtils.defaultIfBlank(context.queryParam("detail"), "false"));
        log.debug("detailQueryParam - {}", detailQueryParam);
        context.json((detailQueryParam) ? temtemService.temtemDetail(id) : temtemService.temtem(id))
                .status(HttpStatus.OK);
    }

    private static void allBattles(@NotNull Context context) {
        log.debug("{} - {}", context.method(), context.path());
        boolean idOnlyQueryParam = Boolean.parseBoolean(StringUtils.defaultIfBlank(context.queryParam("idOnly"), "false"));
        log.debug("idOnlyQueryParam - {}", idOnlyQueryParam);
        context.json((idOnlyQueryParam) ? temtemService.allBattles().stream().map(temtem -> temtem.getId().getId().getString()).toList() : temtemService.allBattles())
                .status(HttpStatus.OK);
    }

    private static void allTechniques(@NotNull Context context) {
        log.debug("{} - {}", context.method(), context.path());
        boolean idOnlyQueryParam = Boolean.parseBoolean(StringUtils.defaultIfBlank(context.queryParam("idOnly"), "false"));
        log.debug("idOnlyQueryParam - {}", idOnlyQueryParam);
        context.json((idOnlyQueryParam) ? temtemService.allTechniques().stream().map(temtem -> temtem.getId().getId().getString()).toList() : temtemService.allTechniques())
                .status(HttpStatus.OK);
    }

    private static void allTemtems(@NotNull Context context) {
        log.debug("{} - {}", context.method(), context.path());
        boolean idOnlyQueryParam = Boolean.parseBoolean(StringUtils.defaultIfBlank(context.queryParam("idOnly"), "false"));
        log.debug("idOnlyQueryParam - {}", idOnlyQueryParam);
        context.json((idOnlyQueryParam) ? temtemService.allTemtems().stream().map(temtem -> temtem.getId().getId().getString()).toList() : temtemService.allTemtems())
                .status(HttpStatus.OK);
    }
}
