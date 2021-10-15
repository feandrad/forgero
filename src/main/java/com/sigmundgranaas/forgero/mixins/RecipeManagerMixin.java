package com.sigmundgranaas.forgero.mixins;

import com.google.gson.JsonElement;
import com.sigmundgranaas.forgero.item.forgerotool.material.ForgeroMaterial;
import com.sigmundgranaas.forgero.item.forgerotool.recipe.ForgeroRecipeCreator;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {

    @Inject(method = "apply", at = @At("HEAD"))
    public void interceptApply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo info) {
        ForgeroRecipeCreator creator = new ForgeroRecipeCreator(map, ForgeroMaterial.getMaterialList());
        creator.createAndRegisterHandles();
        creator.createAndRegisterHeads();
        creator.createAndRegisterBindings();
        creator.createAndRegisterTools();
        creator.createAndRegisterToolsWithBinding();
    }
}