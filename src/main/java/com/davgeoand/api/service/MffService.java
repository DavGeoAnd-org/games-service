package com.davgeoand.api.service;

import com.davgeoand.api.data.MffDB;
import com.davgeoand.api.exception.MffException;
import com.davgeoand.api.model.mff.Character;
import com.davgeoand.api.model.mff.Shadowland;
import com.surrealdb.RecordId;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IteratorUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@NoArgsConstructor
public class MffService {
    private final MffDB mffDB = new MffDB();

    public List<Character> allCharacters() {
//        List<Character> characters = new ArrayList<>();
//        mffDB.allCharacters().forEachRemaining(characters::add);
//        return characters;
        return IteratorUtils.toList(mffDB.allCharacters());
    }

    public RecordId updateCharacter(Character character) {
        log.debug("character - {}", character);
        return mffDB.updateCharacter(character(character.getId().getId().getString()).getId(), character).getId();
    }

    private Character character(String characterId) {
        log.debug("characterId - {}", characterId);
        return mffDB.character(characterId).orElseThrow(() -> new MffException.MissingCharacterException(characterId));
    }

    public RecordId addCharacter(Character character) {
        log.debug("character - {}", character);
        return mffDB.addCharacter(character).getId();
    }

    public List<Shadowland> allShadowlands() {
        return IteratorUtils.toList(mffDB.allShadowlands());
    }

    public void addShadowland() {
        mffDB.addShadowland(new Shadowland());
    }

    public List<Character> allCharactersForShadowland() {
        List<Character> characters = new ArrayList<>(allCharactersSorted());
        characters.removeIf(character -> !character.isSixStar());
        return characters;
    }

    public RecordId updateShadowland(Shadowland shadowland) {
        log.debug("shadowland - {}", shadowland);
        return mffDB.updateShadowland(shadowland(shadowland.getId().getId().getLong()).getId(), shadowland).getId();
    }

    private Shadowland shadowland(long shadowlandId) {
        log.debug("shadowlandId - {}", shadowlandId);
        return mffDB.shadowland(shadowlandId).orElseThrow(() -> new MffException.MissingShadowlandException(shadowlandId));
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
}
