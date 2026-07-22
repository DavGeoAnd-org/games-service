package com.davgeoand.api.service;

import com.davgeoand.api.data.MffDB;
import com.davgeoand.api.exception.MffException;
import com.davgeoand.api.model.mff.Character;
import com.davgeoand.api.model.mff.CharacterUpdate;
import com.davgeoand.api.model.mff.Shadowland;
import com.davgeoand.api.model.mff.ShadowlandLevel;
import com.davgeoand.api.monitor.event.ServiceEventHandler;
import com.surrealdb.RecordId;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IteratorUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Slf4j
@NoArgsConstructor
public class MffService {
    private final MffDB mffDB = new MffDB();

    public List<Character> allCharacters() {
        return IteratorUtils.toList(mffDB.allCharacters());
    }

    public List<Character> allCharactersApp() {
        return allCharactersSorted().reversed();
    }

    private List<Character> allCharactersSorted() {
        List<Character> characters = allCharacters();
        List<Character> damageSorted = characters.stream().sorted(Comparator.comparingInt(c -> Math.max(c.getBurn(), Math.max(c.getParalyze(), c.getSilence())))).toList();
        List<Character> infiniteSorted = damageSorted.stream().sorted(Comparator.comparingInt(Character::getInfinite)).toList();
        return characters.stream().sorted(Comparator.comparingDouble(c -> (double) (damageSorted.indexOf(c) + infiniteSorted.indexOf(c)) / 2)).toList();
    }

    public RecordId addCharacter(Character character) {
        log.debug("character: {}", character);
        fieldsToUppercase(character);
        return mffDB.addCharacter(character).getId();
    }

    @SneakyThrows
    public void updateCharacter(String id, Character character) {
        log.debug("id: {}", id);
        log.debug("character: {}", character);
        if (!id.equals(character.getId().getId().getString())) {
            throw new MffException.IdMismatchException("Id provided does not match character's id");
        }
        fieldsToUppercase(character);
        Character existingCharacter = character(id);
        log.debug("existingCharacter: {}", existingCharacter);
        mffDB.updateCharacter(character);
        Map<String, Object> updateMap = existingCharacter.updateMap(character);
        log.debug("updateMap: {}", updateMap);
        if (!updateMap.isEmpty())
            ServiceEventHandler.addEvent(new CharacterUpdate(character.getId(), updateMap));
    }

    @SneakyThrows
    private Character character(String id) {
        log.debug("id: {}", id);
        return mffDB.character(id).orElseThrow(() -> new MffException.MissingCharacterException(id));
    }

    private void fieldsToUppercase(Character character) {
        log.debug("character: {}", character);
        character.setName(character.getName().toUpperCase());
        character.setUniform(character.getUniform().toUpperCase());
    }

    public List<Shadowland> allShadowlands() {
        return IteratorUtils.toList(mffDB.allShadowlands());
    }

    public List<Shadowland> allShadowlandsApp() {
        return allShadowlands().reversed().stream().limit(10).toList();
    }

    public void addShadowland() {
        mffDB.addShadowland(new Shadowland());
    }

    public List<Character> allCharactersForShadowland() {
        List<Character> characters = new ArrayList<>(allCharactersSorted());
        characters.removeIf(character -> !character.isSixStar());
        return characters;
    }

    public void addShadowlandLevel(long id, ShadowlandLevel shadowlandLevel) {
        log.debug("id: {}", id);
        log.debug("shadowlandLevel: {}", shadowlandLevel);
        Shadowland shadowland = shadowland(id);
        shadowland.addShadowlandLevel(shadowlandLevel);
        updateShadowland(shadowland);
    }

    @SneakyThrows
    public Shadowland shadowland(long id) {
        log.debug("id: {}", id);
        return mffDB.shadowland(id).orElseThrow(() -> new MffException.MissingShadowlandException(id));
    }

    public void finishShadowland(long id) {
        log.debug("id: {}", id);
        Shadowland shadowland = shadowland(id);
        shadowland.setCurrent(false);
        updateShadowland(shadowland);
    }

    private void updateShadowland(Shadowland shadowland) {
        mffDB.updateShadowland(shadowland);
    }
}
