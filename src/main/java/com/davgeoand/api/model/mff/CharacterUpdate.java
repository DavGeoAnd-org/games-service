package com.davgeoand.api.model.mff;

import com.davgeoand.api.monitor.event.type.Event;
import com.influxdb.v3.client.Point;
import com.surrealdb.RecordId;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CharacterUpdate extends Event {
    private RecordId id;
    private Map<String, Object> changesMap = new HashMap<>();

    @Override
    public Point toPoint() {
        return Point.measurement("mff.character.update")
                .setTag("id", id.getId().getString())
                .setFields(changesMap)
                .setTimestamp(time);
    }
}
