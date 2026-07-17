package com.davgeoand.api.model.temtem;

import com.davgeoand.api.model.serializer.RecordIdDeserializer;
import com.davgeoand.api.model.serializer.RecordIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.surrealdb.RecordId;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Temtem implements Comparable<Temtem> {
    @JsonSerialize(using = RecordIdSerializer.class)
    @JsonDeserialize(using = RecordIdDeserializer.class)
    protected RecordId id;
    protected String name;
    protected boolean teamStatus;
    protected List<String> types, weakTypes, superWeakTypes, strongTypes, superStrongTypes;
    protected int number, hitPoints, stamina, speed, attack, defense, specialAttack, specialDefense;

    @Override
    public int compareTo(@NotNull Temtem o) {
        return name.compareTo(o.getName());
    }
}
