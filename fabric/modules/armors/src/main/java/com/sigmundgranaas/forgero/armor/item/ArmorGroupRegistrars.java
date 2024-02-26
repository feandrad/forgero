package com.sigmundgranaas.forgero.armor.item;

import static com.sigmundgranaas.forgero.armor.ForgeroArmorInitializer.FORGERO_ARMORS;
import static com.sigmundgranaas.forgero.minecraft.common.item.RegistryUtils.typeConverter;

import com.sigmundgranaas.forgero.core.registry.GenericRegistry;
import com.sigmundgranaas.forgero.core.registry.RankableConverter;
import com.sigmundgranaas.forgero.core.registry.Registerable;
import com.sigmundgranaas.forgero.core.state.StateProvider;
import com.sigmundgranaas.forgero.core.type.Type;

import net.minecraft.item.ItemGroup;

public class ArmorGroupRegistrars implements Registerable<RankableConverter<StateProvider, ItemGroup>> {
	@Override
	public void register(GenericRegistry<RankableConverter<StateProvider, ItemGroup>> registry) {
		registry.register("forgero:helmet", typeConverter(Type.HELMET, FORGERO_ARMORS, 3));
		registry.register("forgero:chestplate", typeConverter(Type.CHESTPLATE, FORGERO_ARMORS, 3));
		registry.register("forgero:leggings", typeConverter(Type.LEGGINGS, FORGERO_ARMORS, 3));
		registry.register("forgero:boots", typeConverter(Type.BOOTS, FORGERO_ARMORS, 3));
	}
}
