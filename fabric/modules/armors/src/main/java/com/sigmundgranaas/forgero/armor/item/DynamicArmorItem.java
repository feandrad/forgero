package com.sigmundgranaas.forgero.armor.item;

import java.util.List;

import com.sigmundgranaas.forgero.core.state.State;
import com.sigmundgranaas.forgero.core.state.StateProvider;
import com.sigmundgranaas.forgero.minecraft.common.item.ToolStateItem;
import com.sigmundgranaas.forgero.minecraft.common.service.StateService;
import com.sigmundgranaas.forgero.minecraft.common.tooltip.StateWriter;
import com.sigmundgranaas.forgero.minecraft.common.tooltip.Writer;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class DynamicArmorItem extends ArmorItem implements ToolStateItem {
	private final StateProvider DEFAULT;

	public DynamicArmorItem(ArmorMaterial material, Type type, Settings settings, StateProvider defaultState, StateService service) {
		super(material, type, settings);
		this.DEFAULT = defaultState;
	}

	@Override
	public int getItemBarStep(ItemStack stack) {
		return ToolStateItem.super.getItemBarStep(stack);
	}

	public int getItemBarColor(ItemStack stack) {
		return getDurabilityColor(stack);
	}

	@Override
	public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
		StateWriter.of(dynamicState(itemStack)).write(tooltip, tooltipContext);
		super.appendTooltip(itemStack, world, tooltip, tooltipContext);
	}

	@Override
	public Text getName() {
		return Writer.nameToTranslatableText(this);
	}

	@Override
	public Text getName(ItemStack stack) {
		return getName();
	}

	@Override
	public State defaultState() {
		return DEFAULT.get();
	}
}
