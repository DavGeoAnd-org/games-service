package com.davgeoand.api.model.mff;

import com.davgeoand.api.model.serializer.RecordIdDeserializer;
import com.davgeoand.api.model.serializer.RecordIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.surrealdb.RecordId;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Shadowland {
    @JsonSerialize(using = RecordIdSerializer.class)
    @JsonDeserialize(using = RecordIdDeserializer.class)
    RecordId id = new RecordId("shadowlands", Instant.now().toEpochMilli());
    boolean current = true;
    List<ShadowlandLevel> levels = new ArrayList<>();

    public void addShadowlandLevel(ShadowlandLevel shadowlandLevel) {
        levels.add(shadowlandLevel);
    }
}
