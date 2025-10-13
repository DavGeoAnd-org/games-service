package com.davgeoand.api.model.temtem;

import com.davgeoand.api.model.serializer.RecordIdDeserializer;
import com.davgeoand.api.model.serializer.RecordIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.surrealdb.RecordId;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Technique {
    @JsonSerialize(using = RecordIdSerializer.class)
    @JsonDeserialize(using = RecordIdDeserializer.class)
    public RecordId id;
    public String name, effect, courseId, courseStatus, type, synergy, category, targeting;
    public int damage, staminaCost, hold, priority;
}
