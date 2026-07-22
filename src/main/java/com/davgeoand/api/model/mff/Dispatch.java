package com.davgeoand.api.model.mff;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Dispatch {
    int sector = 1, level = 1;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Dispatch dispatch = (Dispatch) o;
        return sector == dispatch.sector && level == dispatch.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sector, level);
    }

    public String simpleToString() {
        return sector + ":" + level;
    }
}
