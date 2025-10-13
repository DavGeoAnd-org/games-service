package com.davgeoand.api.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    // Service
    public static final String SERVICE_NAME = ServiceProperties.getProperty("service.name").orElse("games-service");
    public static final String SERVICE_PORT = StringUtils.defaultIfBlank(System.getenv("SERVICE_PORT"), "8080");
    public static final String SERVICE_CONTEXT_PATH = StringUtils.defaultIfBlank(System.getenv("SERVICE_CONTEXT_PATH"), "/games");

    // SurrealDB
    public static final String SURREALDB_CONNECT = StringUtils.defaultIfBlank(System.getenv("SURREALDB_CONNECT"), "http://localhost:8000");
    public static final String SURREALDB_NAMESPACE = StringUtils.defaultIfBlank(System.getenv("SURREALDB_NAMESPACE"), "games");
    public static final String SURREALDB_USERNAME = StringUtils.defaultIfBlank(System.getenv("SURREALDB_USERNAME"), "root");
    public static final String SURREALDB_PASSWORD = StringUtils.defaultIfBlank(System.getenv("SURREALDB_PASSWORD"), "root");
}