package com.sigmundgranaas.forgero;

import com.sigmundgranaas.forgero.item.ForgeroItemRegister;
import com.sigmundgranaas.forgero.item.ItemInitializer;
import com.sigmundgranaas.forgero.item.forgerotool.material.ForgeroMaterial;
import com.sigmundgranaas.forgero.item.forgerotool.recipe.ForgeroBaseToolRecipeSerializer;
import com.sigmundgranaas.forgero.item.forgerotool.recipe.ForgeroToolWithBindingRecipeSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.registry.Registry;


public class Forgero implements ModInitializer {
    public static String MOD_NAME = "Forgero";
    public static String MOD_NAMESPACE = "forgero";

    @Override
    public void onInitialize() {
        ItemInitializer items = new ItemInitializer(ForgeroMaterial.getMaterialList());
        registerItems(items);
        registerRecipes();
    }

    private void registerItems(ItemInitializer items) {
        ForgeroItemRegister.RegisterForgeroItem(items.getToolPartsHandles());
        ForgeroItemRegister.RegisterForgeroItem(items.getToolPartsBindings());
        ForgeroItemRegister.RegisterForgeroItem(items.getToolPartsHeads());
        ForgeroItemRegister.RegisterForgeroItem(items.getTools());
    }

    private void registerRecipes() {
        Registry.register(Registry.RECIPE_SERIALIZER, ForgeroBaseToolRecipeSerializer.ID, ForgeroBaseToolRecipeSerializer.INSTANCE);
        Registry.register(Registry.RECIPE_SERIALIZER, ForgeroToolWithBindingRecipeSerializer.ID, ForgeroToolWithBindingRecipeSerializer.INSTANCE);
    }
}
