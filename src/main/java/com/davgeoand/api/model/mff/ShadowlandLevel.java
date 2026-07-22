package com.davgeoand.api.model.mff;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShadowlandLevel {
    int level = 1;
    List<String> characters = new ArrayList<>();
}
