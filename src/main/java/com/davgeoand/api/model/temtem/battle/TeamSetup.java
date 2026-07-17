package com.davgeoand.api.model.temtem.battle;

import com.davgeoand.api.model.temtem.Temtem;
import lombok.*;

import java.util.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TeamSetup {
    int teamTemtemLevel;
    Map<String, BattleTemtem> battleTemtems = new TreeMap<>();
    Set<TeamTemtemWithScore> teamTemtems = new HashSet<>();

    public void battleTemtemListToMap(List<Temtem> battleTemtemsList) {
        new TreeSet<>(battleTemtemsList).forEach(temtem -> battleTemtems.put(temtem.getName(), new BattleTemtem(temtem, new TreeSet<>())));
    }

    public void addTeamTemtemToBattleTemtem(String battleTemtem, String teamTemtem) {
        battleTemtems.get(battleTemtem).addTeamTemtem(teamTemtem);
    }

    public void addTeamTemtem(TeamTemtem temtem, int score) {
        teamTemtems.add(new TeamTemtemWithScore(temtem, score));
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    protected static class BattleTemtem {
        Temtem temtem;
        Set<String> teamTemtems;

        public void addTeamTemtem(String teamTemtem) {
            teamTemtems.add(teamTemtem);
        }
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    protected static class TeamTemtemWithScore {
        TeamTemtem temtem;
        Integer score;
    }


}
