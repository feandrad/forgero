package com.sigmundgranaas.forgero.recipe.customrecipe;

import java.util.Locale;

public enum RecipeTypes {

    MISC_SHAPELESS,
    GEM_UPGRADE_RECIPE,
    STATE_CRAFTING_RECIPE,
    STATE_UPGRADE_RECIPE,
    SCHEMATIC_PART_CRAFTING,
    TOOLPART_SCHEMATIC_RECIPE;

    String getName() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}