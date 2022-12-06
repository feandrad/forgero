package com.sigmundgranaas.forgero.resources;


import com.sigmundgranaas.forgero.ForgeroStateRegistry;
import com.sigmundgranaas.forgero.resources.dynamic.DynamicResourceGenerator;
import com.sigmundgranaas.forgero.resources.external.Patchouli;
import com.sigmundgranaas.forgero.state.State;
import com.sigmundgranaas.forgero.type.MutableTypeNode;
import lombok.Synchronized;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.tags.JTag;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;


public class ARRPGenerator {

    public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create("forgero:dynamic");
    private static final List<DynamicResourceGenerator> generators = new ArrayList<>();

    @Synchronized
    public static void register(DynamicResourceGenerator generator) {
        generators.add(generator);
    }

    public void generate() {
        generateResources();
        generators.stream().filter(DynamicResourceGenerator::enabled).forEach(generator -> generator.generate(RESOURCE_PACK));
    }


    public void generateResources() {
        generateTags();
        generateTagsFromStateTree();
        RRPCallback.BEFORE_VANILLA.register(a -> a.add(RESOURCE_PACK));

        if (FabricLoader.getInstance().isModLoaded("patchouli")) {
            new Patchouli().registerResources(RESOURCE_PACK);
        }
    }

    public void generateTags() {

    }

    public void generateTagsFromStateTree() {
        ForgeroStateRegistry.TREE.nodes().forEach(this::createTagFromType);
    }

    private void createTagFromType(MutableTypeNode node) {
        JTag typeTag = new JTag();
        var states = node.getResources(State.class);
        if (states.size() > 0) {
            states.stream()
                    .filter(state -> ForgeroStateRegistry.STATE_TO_CONTAINER.containsKey(state.identifier()))
                    .map(state -> new Identifier(ForgeroStateRegistry.STATE_TO_CONTAINER.get(state.identifier())))
                    .forEach(typeTag::add);
            RESOURCE_PACK.addTag(new Identifier("forgero", "items/" + node.name().toLowerCase()), typeTag);
        }
    }
}