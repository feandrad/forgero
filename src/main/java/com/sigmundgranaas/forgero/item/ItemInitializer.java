package com.sigmundgranaas.forgero.item;

import com.sigmundgranaas.forgero.item.forgerotool.Modifier.ForgeroModifierItem;
import com.sigmundgranaas.forgero.item.forgerotool.tool.ForgeroPickaxe;
import com.sigmundgranaas.forgero.item.forgerotool.tool.ForgeroShovel;
import com.sigmundgranaas.forgero.item.forgerotool.toolpart.ForgeroToolPartItem;
import com.sigmundgranaas.forgero.item.forgerotool.toolpart.ForgeroToolPartTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;

import java.util.ArrayList;
import java.util.List;

public class ItemInitializer {
    public static List<ForgeroToolPartItem> toolPartsHeads;
    public static List<ForgeroToolPartItem> toolPartsHandles;
    public static List<Item> tools;
    public static List<ForgeroToolPartItem> toolPartsBindings;
    public static List<Item> modifiers;
    public final List<ToolMaterial> materials;

    public ItemInitializer(List<ToolMaterial> materials) {
        this.materials = materials;
        toolPartsHandles = initializeHandles();
        toolPartsHeads = initializeHeads();
        tools = initializeTools(toolPartsHandles, toolPartsHeads);
        toolPartsBindings = initializeBindings();
        modifiers = initializeModifiers();
    }

    private List<Item> initializeModifiers() {
        List<Item> items = new ArrayList<>();
        for (ToolMaterial material : materials) {
            items.add(new ForgeroModifierItem(new Item.Settings().group(ForgeroItemGroups.FORGERO_MODIFIERS)));
        }
        return items;
    }

    private List<ForgeroToolPartItem> initializeHandles() {
        List<ForgeroToolPartItem> items = new ArrayList<>();
        for (ToolMaterial material : materials) {
            ForgeroToolPartItem toolPartItem = new ForgeroToolPartItem(new Item.Settings().group(ForgeroItemGroups.FORGERO_TOOL_PARTS), material, ForgeroToolPartTypes.HANDLE);
            items.add(toolPartItem);
        }

        return items;
    }

    private List<ForgeroToolPartItem> initializeHeads() {
        List<ForgeroToolPartItem> items = new ArrayList<>();
        for (ToolMaterial material : materials) {
            ForgeroToolPartItem toolPartItem1 = new ForgeroToolPartItem(new Item.Settings().group(ForgeroItemGroups.FORGERO_TOOL_PARTS), material, ForgeroToolPartTypes.PICKAXE_HEAD);
            items.add(toolPartItem1);
            ForgeroToolPartItem toolPartItem2 = new ForgeroToolPartItem(new Item.Settings().group(ForgeroItemGroups.FORGERO_TOOL_PARTS), material, ForgeroToolPartTypes.SHOVEL_HEAD);
            items.add(toolPartItem2);
        }
        return items;
    }

    private List<ForgeroToolPartItem> initializeBindings() {
        List<ForgeroToolPartItem> items = new ArrayList<>();
        for (ToolMaterial material : materials) {
            ForgeroToolPartItem toolPartItem = new ForgeroToolPartItem(new Item.Settings().group(ForgeroItemGroups.FORGERO_TOOL_PARTS), material, ForgeroToolPartTypes.BINDING);
            items.add(toolPartItem);
        }
        return items;
    }

    private List<Item> initializeTools(List<ForgeroToolPartItem> toolHandles, List<ForgeroToolPartItem> toolHeads) {
        List<Item> items = new ArrayList<>();
        for (ForgeroToolPartItem head : toolHeads) {
            for (ForgeroToolPartItem handle : toolHandles) {
                Item tool;
                switch (head.getToolPartType()) {
                    case SHOVEL_HEAD -> tool = new ForgeroShovel(head.getMaterial(), 7, 5, new Item.Settings().group(ForgeroItemGroups.FORGERO_TOOLS), head, handle);
                    case PICKAXE_HEAD -> tool = new ForgeroPickaxe(head.getMaterial(), 7, 5, new Item.Settings().group(ForgeroItemGroups.FORGERO_TOOLS), head, handle);
                    default -> throw new IllegalStateException("Unexpected value: " + head.getToolPartType());
                }
                items.add(tool);
            }
        }
        return items;
    }

    public List<ForgeroToolPartItem> getToolPartsHeads() {
        return toolPartsHeads;
    }

    public List<ForgeroToolPartItem> getToolPartsHandles() {
        return toolPartsHandles;
    }

    public List<Item> getTools() {
        return tools;
    }

    public List<ForgeroToolPartItem> getToolPartsBindings() {
        return toolPartsBindings;
    }

    public List<Item> getModifiers() {
        return modifiers;
    }


}
