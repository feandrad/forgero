package com.sigmundgranaas.forgero.armor.item;

import com.sigmundgranaas.forgero.core.registry.GenericRegistry;
import com.sigmundgranaas.forgero.core.registry.RankableConverter;
import com.sigmundgranaas.forgero.core.registry.Registerable;
import com.sigmundgranaas.forgero.core.state.StateProvider;

import net.minecraft.item.ItemGroup;

public class ArmorGroupRegistrars implements Registerable<RankableConverter<StateProvider, ItemGroup>> {
	@Override
	public void register(GenericRegistry<RankableConverter<StateProvider, ItemGroup>> registry) {
//		registry.register("forgero:bow", typeConverter(Type.BOW, FORGERO_BOWS, 3));
	}
}
