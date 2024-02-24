package com.sigmundgranaas.forgero.armor.item;

import static com.sigmundgranaas.forgero.minecraft.common.item.RegistryUtils.typeMatcher;

import java.util.Optional;

import com.sigmundgranaas.forgero.armor.attribute.KnockbackResistance;
import com.sigmundgranaas.forgero.armor.attribute.Toughness;
import com.sigmundgranaas.forgero.core.property.v2.attribute.attributes.Durability;
import com.sigmundgranaas.forgero.core.property.v2.attribute.attributes.Protection;
import com.sigmundgranaas.forgero.core.registry.GenericRegistry;
import com.sigmundgranaas.forgero.core.registry.RankableConverter;
import com.sigmundgranaas.forgero.core.registry.Registerable;
import com.sigmundgranaas.forgero.core.state.MaterialBased;
import com.sigmundgranaas.forgero.core.state.State;
import com.sigmundgranaas.forgero.core.state.StateProvider;
import com.sigmundgranaas.forgero.core.state.composite.ConstructedTool;
import com.sigmundgranaas.forgero.minecraft.common.item.BuildableStateConverter;
import com.sigmundgranaas.forgero.minecraft.common.item.ItemData;
import com.sigmundgranaas.forgero.minecraft.common.service.StateService;

import net.minecraft.data.client.BlockStateVariantMap;

import org.apache.commons.lang3.function.TriFunction;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.recipe.Ingredient;

public class DynamicArmorItemRegistrationHandler implements Registerable<RankableConverter<StateProvider, ItemData>> {

	private final BuildableStateConverter defaultStateConverter;

	public DynamicArmorItemRegistrationHandler(BuildableStateConverter defaultStateConverter) {
		this.defaultStateConverter = defaultStateConverter;
	}

	@Override
	public void register(GenericRegistry<RankableConverter<StateProvider, ItemData>> registry) {
		var base = defaultStateConverter.toBuilder()
				.priority(1)
				.build();

		registry.register("forgero:helmet", base.toBuilder()
				.priority(2)
				.matcher(typeMatcher("HELMET"))
				.armor(armorItemPreparer(this::armor))
				.build());

		registry.register("forgero:chestplate", base.toBuilder()
				.priority(2)
				.matcher(typeMatcher("CHESTPLATE"))
				.armor(armorItemPreparer(this::armor))
				.build());

		registry.register("forgero:leggings", base.toBuilder()
				.priority(2)
				.matcher(typeMatcher("LEGGINGS"))
				.armor(armorItemPreparer(this::armor))
				.build());

		registry.register("forgero:boots", base.toBuilder()
				.priority(2)
				.matcher(typeMatcher("BOOTS"))
				.armor(armorItemPreparer(this::armor))
				.build());
	}

	private Item armor(ArmorItem.Type armorType, StateProvider provider, Item.Settings settings, DynamicArmorItemSettings params) {
		return new DynamicArmorItem(
				new ForgeroArmorMaterial(provider, params.ingredient(), StateService.INSTANCE),
				armorType, settings, provider, StateService.INSTANCE
		);
	}

	public static TriFunction<ArmorItem.Type,StateProvider, Item.Settings, Item> armorItemPreparer(BlockStateVariantMap.QuadFunction<ArmorItem.Type, StateProvider, Item.Settings, DynamicArmorItemSettings, Item> converter) {
		return (armorType, state, settings) -> {
			DynamicArmorItemSettings params = createDynamicSettings(armorType, state);
			return converter.apply(armorType, state, settings, params);
		};
	}


	public static DynamicArmorItemSettings createDynamicSettings(ArmorItem.Type armorType, StateProvider provider) {
		var state = provider.get();

		int durability = (int) state.stream().applyAttribute(Durability.KEY);
		int protection = (int) state.stream().applyAttribute(Protection.KEY);
		float toughness = state.stream().applyAttribute(Toughness.KEY);
		float knockbackResist = state.stream().applyAttribute(KnockbackResistance.KEY);

		Ingredient ingredient = createIngredientFromState(provider);

		return new DynamicArmorItemSettings(
				armorType,
				durability,
				protection,
				toughness,
				knockbackResist,
				ingredient
		);
	}

	public static Ingredient createIngredientFromState(StateProvider provider) {
		var state = provider.get();

		Optional<State> ingredientState = Optional.empty();

		if (state instanceof ConstructedTool tool) {
			if (tool.getHead() instanceof MaterialBased based) {
				ingredientState = Optional.of(based.baseMaterial());
			}
		}

		StateService service = StateService.INSTANCE;
		return ingredientState
				.flatMap(service::convert)
				.map(Ingredient::ofStacks)
				.orElse(ToolMaterials.WOOD.getRepairIngredient());
	}

	public record DynamicArmorItemSettings(
			ArmorItem.Type armorType,
			int durability, int protection, float toughness, float knockbackResist,
			Ingredient ingredient) {
	}
}
