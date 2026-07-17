package com.davgeoand.api.model.response;

import com.davgeoand.api.model.serializer.RecordIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.surrealdb.RecordId;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RecordIdResponse {
    String message;
    @JsonSerialize(using = RecordIdSerializer.class)
    RecordId id;
}