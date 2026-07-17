package com.davgeoand.api.service;

import com.davgeoand.api.data.TemtemDB;
import com.davgeoand.api.exception.TemtemException.MissingBattleException;
import com.davgeoand.api.exception.TemtemException.MissingTechniqueException;
import com.davgeoand.api.exception.TemtemException.MissingTemtemException;
import com.davgeoand.api.model.temtem.Technique;
import com.davgeoand.api.model.temtem.Temtem;
import com.davgeoand.api.model.temtem.TemtemDetail;
import com.davgeoand.api.model.temtem.battle.Battle;
import com.davgeoand.api.model.temtem.battle.BattleDetail;
import com.davgeoand.api.model.temtem.battle.TeamSetup;
import com.davgeoand.api.model.temtem.battle.TeamTemtem;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@NoArgsConstructor
public class TemtemService {
    private final TemtemDB temtemDB = new TemtemDB();

    public List<Temtem> allTemtems() {
        List<Temtem> temtemList = new ArrayList<>();
        temtemDB.allTemtems().forEachRemaining(temtemList::add);
        log.debug("temtemList - {}", temtemList);
        return temtemList;
    }

    public List<Technique> allTechniques() {
        List<Technique> techniqueList = new ArrayList<>();
        temtemDB.allTechniques().forEachRemaining(techniqueList::add);
        log.debug("techniqueList - {}", techniqueList);
        return techniqueList;
    }

    public List<Battle> allBattles() {
        List<Battle> battleList = new ArrayList<>();
        temtemDB.allBattles().forEachRemaining(battleList::add);
        log.debug("battleList - {}", battleList);
        return battleList;
    }

    public Temtem temtem(String id) {
        log.debug("id - {}", id);
        return temtemDB.temtem(id).orElseThrow(() -> new MissingTemtemException(id));
    }

    public TemtemDetail temtemDetail(String id) {
        log.debug("id - {}", id);
        Temtem temtem = temtem(id);
        return temtemDB.temtemDetail(temtem.getId());
    }

    public Temtem updateTeamStatus(String id, boolean teamStatus) {
        log.debug("id - {}", id);
        log.debug("teamStatus - {}", teamStatus);
        Temtem temtem = temtem(id);
        temtem.setTeamStatus(teamStatus);
        return temtemDB.updateTemtem(temtem);
    }

    public Technique updateCourseStatus(String id, boolean courseStatus) {
        log.debug("id - {}", id);
        log.debug("courseStatus - {}", courseStatus);
        Technique technique = technique(id);
        technique.setCourseStatus(courseStatus);
        return temtemDB.updateTechnique(technique);
    }

    public Technique technique(String id) {
        log.debug("id - {}", id);
        return temtemDB.technique(id).orElseThrow(() -> new MissingTechniqueException(id));
    }

    public BattleDetail battleDetail(String id) {
        log.debug("id - {}", id);
        Battle battle = battle(id);
        return temtemDB.battleDetail(battle.getId());
    }

    public Battle battle(String id) {
        log.debug("id - {}", id);
        return temtemDB.battle(id).orElseThrow(() -> new MissingBattleException(id));
    }

    public TeamSetup teamSetup(String id) {
        log.debug("id - {}", id);
        // Create initial TeamSetup
        TeamSetup teamSetup = new TeamSetup();

        // Get BattleDetail
        BattleDetail battleDetail = battleDetail(id);
        log.debug("battleDetail - {}", battleDetail);
        int teamLevel = battleDetail.avgLevel();
        log.debug("teamLevel - {}", teamLevel);
        List<Temtem> battleTemtems = battleDetail.temtems();
        log.debug("battleTemtems - {}", battleTemtems);

        // Put values in TeamSetup from BattleDetail
        teamSetup.setTeamTemtemLevel(teamLevel);
        teamSetup.battleTemtemListToMap(battleTemtems);
        log.debug("teamSetup - {}", teamSetup);

        // Get TeamTemtems
        List<TeamTemtem> teamTemtems = new ArrayList<>();
        temtemDB.teamTemtems(teamLevel).forEachRemaining((teamTemtems::add));
        log.debug("teamTemtems - {}", teamTemtems);

        // Add Team Temtems to Battle Temtems and give each a score
        for (TeamTemtem teamTemtem : teamTemtems.stream().sorted().toList()) {
            int teamTemtemScore = 0;
            Set<String> techniqueTypes = teamTemtem.getTechniques().stream().map(Technique::getType).collect(Collectors.toSet());
            for (Temtem battleTemtem : battleTemtems) {
                if (Collections.disjoint(battleTemtem.getTypes(), Stream.concat(teamTemtem.getWeakTypes().stream(), teamTemtem.getSuperWeakTypes().stream()).collect(Collectors.toSet()))) {
                    if (!Collections.disjoint(Stream.concat(battleTemtem.getWeakTypes().stream(), battleTemtem.getSuperWeakTypes().stream()).collect(Collectors.toSet()), techniqueTypes)) {
                        teamSetup.addTeamTemtemToBattleTemtem(battleTemtem.getName(), teamTemtem.getName());
                        teamTemtemScore += 1;
                    }
                }
            }
            if(teamTemtemScore>0) teamSetup.addTeamTemtem(teamTemtem, teamTemtemScore);
        }
        log.debug("teamSetup - {}", teamSetup);

        return teamSetup;
    }
}
