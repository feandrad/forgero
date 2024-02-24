package com.sigmundgranaas.forgero.armor.item;

import com.sigmundgranaas.forgero.core.state.StateProvider;

import com.sigmundgranaas.forgero.minecraft.common.service.StateService;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;

public class DynamicArmorItem extends ArmorItem {
	private final StateProvider DEFAULT;

	public DynamicArmorItem(ArmorMaterial material, Type type, Settings settings, StateProvider defaultState, StateService service) {
		super(material, type, settings);
		this.DEFAULT = defaultState;
	}
}
