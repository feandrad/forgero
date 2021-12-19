package com.sigmundgranaas.forgero.tool.toolpart;

import com.sigmundgranaas.forgero.identifier.ForgeroToolPartIdentifier;
import com.sigmundgranaas.forgero.material.MaterialCollection;
import com.sigmundgranaas.forgero.material.material.PrimaryMaterial;

public class ForgeroToolPartFactoryImpl implements ForgeroToolPartFactory {
    private static ForgeroToolPartFactory INSTANCE;

    public static ForgeroToolPartFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ForgeroToolPartFactoryImpl();
        }
        return INSTANCE;
    }

    @Override
    public ForgeroToolPart createToolPart(ForgeroToolPartIdentifier identifier) {
        PrimaryMaterial material = (PrimaryMaterial) MaterialCollection.INSTANCE.getMaterial(identifier.getMaterial());
        return switch (identifier.getToolPartType()) {
            case PICKAXEHEAD -> new PickaxeHead(material);
            case SWORDHEAD -> null;
            case SHOVELHEAD -> null;
            case AXEHEAD -> null;
            case HANDLE -> new Handle(material);
            case BINDING -> null;
        };
    }
}