package com.sigmundgranaas.forgero.armor.item;

import com.sigmundgranaas.forgero.core.state.StateProvider;
import com.sigmundgranaas.forgero.minecraft.common.item.ForgeroMaterial;
import com.sigmundgranaas.forgero.minecraft.common.service.StateService;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;

public class ForgeroArmorMaterial extends ForgeroMaterial implements ArmorMaterial {

	public ForgeroArmorMaterial(StateProvider provider, Ingredient ingredient, StateService service) {
		super(provider, ingredient, service);
	}

	@Override
	public int getDurability(ArmorItem.Type type) {
		return 0;
	}

	@Override
	public int getProtection(ArmorItem.Type type) {
		return 0;
	}

	@Override
	public SoundEvent getEquipSound() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public float getToughness() {
		return 0;
	}

	@Override
	public float getKnockbackResistance() {
		return 0;
	}
}
