package com.sigmundgranaas.forgero.core.material.implementation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SimpleMaterialLoaderTest {

    @Test
    void loadMaterials() {
        Assertions.assertTrue(new SimpleMaterialLoader("/config/forgero/materials/simple/").getMaterials().size() > 0);
    }

    @Test
    void loadMaterial() {
    }

    @Test
    void getMaterials() {
    }
}