package com.davgeoand.api.model.temtem;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TemtemDetail extends Temtem {
    public List<LevelTechnique> levelTechniques;
    public List<Technique> courseTechniques;

    @Getter
    @Setter
    @ToString(callSuper = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LevelTechnique {
        int level;
        Technique technique;
    }
}
