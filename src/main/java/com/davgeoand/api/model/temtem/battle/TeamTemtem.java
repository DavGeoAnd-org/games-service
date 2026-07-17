package com.davgeoand.api.model.temtem.battle;

import com.davgeoand.api.model.temtem.Technique;
import com.davgeoand.api.model.temtem.Temtem;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TeamTemtem extends Temtem {
    List<Technique> techniques;
}
