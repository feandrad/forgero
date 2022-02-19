package com.sigmundgranaas.forgero.core.texture.palette;

import com.sigmundgranaas.forgero.core.identifier.texture.toolpart.PaletteIdentifier;
import com.sigmundgranaas.forgero.core.material.material.PaletteResourceIdentifier;

import java.util.*;

public class PaletteResourceRegistry {
    public static PaletteResourceRegistry INSTANCE;
    private final Map<String, PaletteResourceIdentifier> paletteIdentifierMap;
    private final Set<String> premadePalettes;

    private PaletteResourceRegistry() {
        this.premadePalettes = new HashSet<>();
        paletteIdentifierMap = new HashMap<>();
        addPremadePalette("iron");
    }

    public static PaletteResourceRegistry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PaletteResourceRegistry();
        }
        return INSTANCE;
    }

    public void addPalette(PaletteResourceIdentifier palette) {
        this.paletteIdentifierMap.put(palette.getIdentifier(), palette);
    }

    public Optional<PaletteResourceIdentifier> getPalette(String identifier) {
        return Optional.ofNullable(paletteIdentifierMap.get(identifier));
    }

    public void addPremadePalette(String id) {
        premadePalettes.add(id);
    }

    public boolean premadePalette(PaletteIdentifier id) {
        return premadePalettes.contains(id.getIdentifier());
    }
}