package com.davgeoand.api.model.temtem.battle;

import com.davgeoand.api.model.temtem.Temtem;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class BattleDetail extends Battle {
    List<TemtemWithLevel> levelTemtems;

    public int avgLevel() {
        int sumLevel = 0;
        for (TemtemWithLevel temtem : levelTemtems) {
            sumLevel += temtem.getLevel();
        }
        return Math.ceilDiv(sumLevel, levelTemtems.size());
    }

    public List<Temtem> temtems() {
        return levelTemtems.stream().map(TemtemWithLevel::getTemtem).toList();
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    protected static class TemtemWithLevel {
        Temtem temtem;
        int level;
    }
}
