/*
 * Copyright 2023-2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.mixin.entity.experience;

import net.frozenblock.wilderwild.item.AncientHorn;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ExperienceOrb;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ExperienceOrb.class)
public class ExperienceOrbMixin {
	@ModifyVariable(method = "repairPlayerItems", at = @At("HEAD"), argsOnly = true)
	private int wilderWild$repairAncientHorn(int amount, ServerPlayer player) {
		if (player.isHolding(RegisterItems.ANCIENT_HORN)) {
			return AncientHorn.decreaseCooldown(player, amount);
		}
		return amount;
	}
}
