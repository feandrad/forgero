package com.sigmundgranaas.forgero.item.nbt.v2;

import com.sigmundgranaas.forgero.state.Composite;
import com.sigmundgranaas.forgero.state.State;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

import static com.sigmundgranaas.forgero.item.nbt.v2.NbtConstants.*;

public class CompositeEncoder implements CompoundEncoder<State> {
    private final IngredientEncoder ingredientEncoder;
    private final UpgradeEncoder upgradeEncoder;
    private final IdentifiableEncoder identifiableEncoder;

    public CompositeEncoder() {
        this.ingredientEncoder = new IngredientEncoder();
        this.upgradeEncoder = new UpgradeEncoder();
        this.identifiableEncoder = new IdentifiableEncoder();
    }

    @Override
    public NbtCompound encode(State element) {
        var compound = identifiableEncoder.encode(element);
        compound.putString(STATE_TYPE_IDENTIFIER, COMPOSITE_IDENTIFIER);
        compound.putString(TYPE_IDENTIFIER, element.type().typeName());
        if (element instanceof Composite composite) {
            var ingredients = new NbtList();
            composite.ingredients().stream().map(ingredientEncoder::encode).forEach(ingredients::add);
            compound.put(INGREDIENTS_IDENTIFIER, ingredients);

            var upgrades = new NbtList();
            composite.upgrades().stream().map(upgradeEncoder::encode).forEach(upgrades::add);
            compound.put(UPGRADES_IDENTIFIER, upgrades);
        }
        return compound;
    }
}
