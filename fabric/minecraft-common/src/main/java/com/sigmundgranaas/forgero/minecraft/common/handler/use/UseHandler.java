package com.sigmundgranaas.forgero.minecraft.common.handler.use;

import com.sigmundgranaas.forgero.core.property.v2.feature.ClassKey;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public interface UseHandler extends BaseHandler {
	ClassKey<UseHandler> KEY = new ClassKey<>("minecraft:use_handler", UseHandler.class);

	TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand);
}
