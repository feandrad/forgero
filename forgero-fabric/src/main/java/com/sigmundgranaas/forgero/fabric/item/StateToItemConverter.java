package com.sigmundgranaas.forgero.fabric.item;

import com.sigmundgranaas.forgero.core.property.AttributeType;
import com.sigmundgranaas.forgero.core.state.State;
import com.sigmundgranaas.forgero.core.state.StateProvider;
import com.sigmundgranaas.forgero.core.type.Type;
import com.sigmundgranaas.forgero.core.util.match.Context;
import com.sigmundgranaas.forgero.minecraft.common.item.DefaultStateItem;
import com.sigmundgranaas.forgero.minecraft.common.item.GemItem;
import com.sigmundgranaas.forgero.minecraft.common.item.tool.*;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class StateToItemConverter {
    private final StateProvider provider;

    public StateToItemConverter(StateProvider provider) {
        this.provider = provider;
    }

    public static StateToItemConverter of(StateProvider provider) {
        return new StateToItemConverter(provider);
    }

    public Item convert() {
        var context = Context.of();
        var state = provider.get();
        int attack_damage = (int) state.stream().applyAttribute(AttributeType.ATTACK_DAMAGE);
        float attack_speed = state.stream().applyAttribute(AttributeType.ATTACK_SPEED);
        if (state.type().test(Type.of("SWORD"), context)) {
            return new DynamicSwordItem(ToolMaterials.WOOD, (int) state.stream().applyAttribute(AttributeType.ATTACK_DAMAGE), state.stream().applyAttribute(AttributeType.ATTACK_SPEED), getItemSettings(state), provider);
        } else if (state.type().test(Type.of("PICKAXE"), context)) {
            return new DynamicPickaxeItem(ToolMaterials.WOOD, (int) state.stream().applyAttribute(AttributeType.ATTACK_DAMAGE), state.stream().applyAttribute(AttributeType.ATTACK_SPEED), getItemSettings(state), provider);
        } else if (state.type().test(Type.of("AXE"), context)) {
            return new DynamicAxeItem(ToolMaterials.WOOD, attack_damage, attack_speed, getItemSettings(state), () -> state);
        } else if (state.type().test(Type.of("HOE"), context)) {
            return new DynamicHoeItem(ToolMaterials.WOOD, (int) state.stream().applyAttribute(AttributeType.ATTACK_DAMAGE), state.stream().applyAttribute(AttributeType.ATTACK_SPEED), getItemSettings(state), provider);
        } else if (state.type().test(Type.of("SHOVEL"), context)) {
            return new DynamicShovelItem(ToolMaterials.WOOD, (int) state.stream().applyAttribute(AttributeType.ATTACK_DAMAGE), state.stream().applyAttribute(AttributeType.ATTACK_SPEED), getItemSettings(state), provider);
        } else if (state.type().test(Type.GEM)) {
            return new GemItem(getItemSettings(state), state);
        }
        return defaultStateItem();
    }

    public Identifier id() {
        return new Identifier(provider.get().nameSpace(), provider.get().name());
    }

    private Item defaultStateItem() {
        var item = new DefaultStateItem(new Item.Settings(), provider);
        ItemGroupEvents.modifyEntriesEvent(getItemGroup(provider.get())).register(entries -> entries.add(item));
        return item;
    }

    public ItemGroup getItemGroup(State state) {
        if (state.test(Type.TOOL)) {
            return net.minecraft.item.ItemGroups.TOOLS;
        } else if (state.test(Type.WEAPON)) {
            return net.minecraft.item.ItemGroups.COMBAT;
        } else if (state.test(Type.PART)) {
            return ItemGroups.FORGERO_TOOL_PARTS;
        } else if (state.test(Type.SCHEMATIC)) {
            return ItemGroups.FORGERO_SCHEMATICS;
        } else if (state.test(Type.TRINKET)) {
            return ItemGroups.FORGERO_GEMS;
        }
        return net.minecraft.item.ItemGroups.INGREDIENTS;
    }

    private Item.Settings getItemSettings(State state) {
        var settings = new Item.Settings();

        if (state.name().contains("schematic")) {
            settings.recipeRemainder(Registries.ITEM.get(new Identifier(state.identifier())));
        }

        if (state.name().contains("netherite")) {
            settings.fireproof();
        }
        return settings;
    }
}