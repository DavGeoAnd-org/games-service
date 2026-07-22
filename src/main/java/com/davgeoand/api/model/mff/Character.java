package com.davgeoand.api.model.mff;

import com.davgeoand.api.model.serializer.RecordIdDeserializer;
import com.davgeoand.api.model.serializer.RecordIdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.surrealdb.RecordId;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Character {
    @JsonSerialize(using = RecordIdSerializer.class)
    @JsonDeserialize(using = RecordIdDeserializer.class)
    RecordId id;
    String name = "", type = "", side = "", gender = "", species = "", instinct = "", uniform = "", ctp = "", ctpStatus = "";
    boolean sixStar = false, update = false;
    int shadowland = 0, infinite = 0, silence = 0, burn = 0, paralyze = 0, shock = 0, snare = 0, fracture = 0;
    List<String> abilities = new ArrayList<>(), skillElements = new ArrayList<>();
    Tier tier = new Tier();
    Dispatch dispatch = new Dispatch();

    public Map<String, Object> updateMap(Character other) {
        Map<String, Object> updateMap = new HashMap<>();

        if (!name.equals(other.name)) updateMap.putAll(Map.of("name.old", name, "name.new", other.name));
        if (!type.equals(other.type)) updateMap.putAll(Map.of("type.old", type, "type.new", other.type));
        if (!side.equals(other.side)) updateMap.putAll(Map.of("side.old", side, "side.new", other.side));
        if (!gender.equals(other.gender)) updateMap.putAll(Map.of("gender.old", gender, "gender.new", other.gender));
        if (!species.equals(other.species))
            updateMap.putAll(Map.of("species.old", species, "species.new", other.species));
        if (!instinct.equals(other.instinct))
            updateMap.putAll(Map.of("instinct.old", instinct, "instinct.new", other.instinct));
        if (!uniform.equals(other.uniform))
            updateMap.putAll(Map.of("uniform.old", uniform, "uniform.new", other.uniform));
        if (!ctp.equals(other.ctp)) updateMap.putAll(Map.of("ctp.old", ctp, "ctp.new", other.ctp));
        if (!ctpStatus.equals(other.ctpStatus))
            updateMap.putAll(Map.of("ctpStatus.old", ctpStatus, "ctpStatus.new", other.ctpStatus));
        if (sixStar != other.sixStar) updateMap.putAll(Map.of("sixStar.old", sixStar, "sixStar.new", other.sixStar));
        if (shadowland != other.shadowland)
            updateMap.putAll(Map.of("shadowland.old", shadowland, "shadowland.new", other.shadowland));
        if (infinite != other.infinite)
            updateMap.putAll(Map.of("infinite.old", infinite, "infinite.new", other.infinite));
        if (silence != other.silence) updateMap.putAll(Map.of("silence.old", silence, "silence.new", other.silence));
        if (burn != other.burn) updateMap.putAll(Map.of("burn.old", burn, "burn.new", other.burn));
        if (paralyze != other.paralyze)
            updateMap.putAll(Map.of("paralyze.old", paralyze, "paralyze.new", other.paralyze));
        if (shock != other.shock) updateMap.putAll(Map.of("shock.old", shock, "shock.new", other.shock));
        if (snare != other.snare) updateMap.putAll(Map.of("snare.old", snare, "snare.new", other.snare));
        if (fracture != other.fracture)
            updateMap.putAll(Map.of("fracture.old", fracture, "fracture.new", other.fracture));
        if (!abilities.equals(other.abilities))
            updateMap.putAll(Map.of("abilities.old", abilities, "abilities.new", other.abilities));
        if (!skillElements.equals(other.skillElements))
            updateMap.putAll(Map.of("skillElements.old", skillElements, "skillElements.new", other.skillElements));
        if (!tier.equals(other.tier))
            updateMap.putAll(Map.of("tier.old", tier.simpleToString(), "tier.new", other.tier.simpleToString()));
        if (!dispatch.equals(other.dispatch))
            updateMap.putAll(Map.of("dispatch.old", dispatch.simpleToString(), "dispatch.new", other.dispatch.simpleToString()));

        return updateMap;
    }
}
