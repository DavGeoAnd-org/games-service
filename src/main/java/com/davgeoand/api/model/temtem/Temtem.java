package com.davgeoand.api.model.temtem;

import com.davgeoand.api.model.serializer.RecordIdDeserializer;
import com.davgeoand.api.model.serializer.RecordIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.surrealdb.RecordId;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Temtem {
    @JsonSerialize(using = RecordIdSerializer.class)
    @JsonDeserialize(using = RecordIdDeserializer.class)
    protected RecordId id;
    protected String name, teamStatus;
    protected List<String> types, weakTypes, superWeakTypes, strongTypes, superStrongTypes;
    protected int number, hitPoints, stamina, speed, attack, defense, specialAttack, specialDefense;
}
