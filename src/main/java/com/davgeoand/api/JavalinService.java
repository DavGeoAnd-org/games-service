package com.davgeoand.api;

import com.davgeoand.api.controller.AdminController;
import com.davgeoand.api.controller.TemtemController;
import com.davgeoand.api.exception.TemtemException;
import com.davgeoand.api.helper.Constants;
import com.surrealdb.SurrealException;
import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;

import static io.javalin.apibuilder.ApiBuilder.path;

@Slf4j
public class JavalinService {
    private final Javalin javalin;
    private final String SERVICE_NAME = Constants.SERVICE_NAME;

    public JavalinService() {
        log.info("Initializing {}", SERVICE_NAME);
        javalin = Javalin.create(javalinConfig -> {
            javalinConfig.router.apiBuilder(routes());
            javalinConfig.router.contextPath = Constants.SERVICE_CONTEXT_PATH;
        });
        addExceptionHandlers();
        log.info("Initialized {}", SERVICE_NAME);
    }

    private void addExceptionHandlers() {
        log.info("Adding exception handlers");
        javalin.exception(TemtemException.MissingException.class, (e, context) -> {
            context.result(e.getMessage());
            context.status(HttpStatus.NOT_FOUND);
        });
        javalin.exception(SurrealException.class, (e, context) -> {
            context.result(e.getMessage());
            context.status(HttpStatus.INTERNAL_SERVER_ERROR);
        });
        log.info("Added exception handlers");
    }

    private EndpointGroup routes() {
        return () -> {
            path("admin", AdminController.getAdminEndpoints());
            path("temtem", TemtemController.getTemtemEndpoints());
        };
    }

    public void start() {
        log.info("Starting {}", SERVICE_NAME);
        javalin.start(Integer.parseInt(Constants.SERVICE_PORT));
        log.info("Started {}", SERVICE_NAME);
    }
}