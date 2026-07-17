package com.davgeoand.api.model.mff;

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
public class Character {
    @JsonSerialize(using = RecordIdSerializer.class)
    @JsonDeserialize(using = RecordIdDeserializer.class)
    RecordId id;
    String name, type, gender, side, ctp = "", ctpStatus = "";
    boolean sixStar, update = false;
    int shadowland, infinite, silence, burn, paralyze;
    Tier tier = new Tier();
    Dispatch dispatch = new Dispatch();
}
