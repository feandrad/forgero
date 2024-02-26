package com.sigmundgranaas.forgero.armor;

import static com.sigmundgranaas.forgero.minecraft.common.item.RegistryUtils.*;

import com.sigmundgranaas.forgero.armor.item.ArmorGroupRegistrars;
import com.sigmundgranaas.forgero.armor.item.DynamicArmorItemRegistrationHandler;
import com.sigmundgranaas.forgero.core.Forgero;
import com.sigmundgranaas.forgero.core.registry.RegistryFactory;
import com.sigmundgranaas.forgero.fabric.api.entrypoint.ForgeroPreInitializationEntryPoint;
import com.sigmundgranaas.forgero.minecraft.common.item.BuildableStateConverter;
import com.sigmundgranaas.forgero.minecraft.common.item.ItemRegistries;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;

public class ForgeroArmorInitializer implements ForgeroPreInitializationEntryPoint {
	public static final RegistryKey<ItemGroup> FORGERO_ARMOR_KEY = RegistryKey.of(
			RegistryKeys.ITEM_GROUP, new Identifier(Forgero.NAMESPACE, "armors")
	);

	public static final ItemGroup FORGERO_ARMORS = FabricItemGroup.builder()
			.icon(ForgeroArmorInitializer::armorIcon)
			.displayName(Text.translatable("itemGroup.forgero.armors"))
			.build();

	static {
		Registry.register(Registries.ITEM_GROUP, FORGERO_ARMOR_KEY, FORGERO_ARMORS);
	}
	
	private static ItemStack armorIcon() {
		return new ItemStack(Registries.ITEM.get(new Identifier("minecraft:iron_helmet")));
	}

	@Override
	public void onPreInitialization() {
		var settingRegistry = ItemRegistries.SETTING_PROCESSOR;
		var groupRegistry = ItemRegistries.GROUP_CONVERTER;

		register(groupRegistry, ArmorGroupRegistrars::new);

		var factory = new RegistryFactory<>(groupRegistry);

		var baseConverter = BuildableStateConverter.builder()
				.group(factory::convert)
				.settings(settingProcessor(settingRegistry))
				.item(defaultItem)
				.priority(0)
				.build();

		register(ItemRegistries.STATE_CONVERTER, new DynamicArmorItemRegistrationHandler(baseConverter));
	}
}
