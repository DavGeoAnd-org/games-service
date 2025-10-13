package com.davgeoand.api.service;

import com.davgeoand.api.data.TemtemDB;
import com.davgeoand.api.exception.TemtemException;
import com.davgeoand.api.model.temtem.Battle;
import com.davgeoand.api.model.temtem.Technique;
import com.davgeoand.api.model.temtem.Temtem;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor
public class TemtemService {
    private final TemtemDB temtemDB = new TemtemDB();

    public List<Temtem> allTemtems() {
        List<Temtem> temtemList = new ArrayList<>();
        temtemDB.allTemtems().forEachRemaining(temtemList::add);
        return temtemList;
    }

    public Temtem createTemtem(Temtem temtem) {
        log.debug("temtem - {}", temtem);
        Temtem createdTemtem = temtemDB.createItem(temtem);
        log.debug("createdTemtem - {}", createdTemtem);
        return createdTemtem;
    }

    public Temtem temtem(String id) {
        log.debug("id - {}", id);
        return temtemDB.temtem(id).orElseThrow(() -> new TemtemException.MissingTemtemException(id));
    }

    public List<Technique> allTechniques() {
        List<Technique> techniqueList = new ArrayList<>();
        temtemDB.allTechniques().forEachRemaining(techniqueList::add);
        return techniqueList;
    }

    public Technique createTechnique(Technique technique) {
        log.debug("technique - {}", technique);
        Technique createdTechnique = temtemDB.createTechnique(technique);
        log.debug("createdTechnique - {}", createdTechnique);
        return createdTechnique;
    }

    public Technique technique(String id) {
        log.debug("id - {}", id);
        return temtemDB.technique(id).orElseThrow(() -> new TemtemException.MissingTechniqueException(id));
    }

    public List<Battle> allBattles() {
        List<Battle> battleList = new ArrayList<>();
        temtemDB.allBattles().forEachRemaining(battleList::add);
        return battleList;
    }

    public Battle createBattle(Battle battle) {
        log.debug("battle - {}", battle);
        Battle createdBattle = temtemDB.createBattle(battle);
        log.debug("createdBattle - {}", createdBattle);
        return createdBattle;
    }

    public Battle battle(String id) {
        log.debug("id - {}", id);
        return temtemDB.battle(id).orElseThrow(() -> new TemtemException.MissingBattleException(id));
    }
}
