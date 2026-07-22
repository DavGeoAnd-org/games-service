package com.davgeoand.api.model.mff;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Tier {
    String value = "T1";
    int level = 0;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Tier tier = (Tier) o;
        return level == tier.level && Objects.equals(value, tier.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, level);
    }

    public String simpleToString() {
        return value + ":" + level;
    }
}
