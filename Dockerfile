FROM davidgandalcio/otel-java-agent:25.0.3_9-2.29.0
LABEL authors="davidgandalcio"

COPY ./target/games-service.jar games-service.jar
COPY ./target/lib lib

EXPOSE 10000

ENTRYPOINT ["java", "-jar", "games-service.jar"]