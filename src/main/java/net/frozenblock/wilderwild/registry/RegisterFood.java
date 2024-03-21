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

package net.frozenblock.wilderwild.registry;

import net.minecraft.world.food.FoodProperties;

public final class RegisterFood {
	public static final FoodProperties BAOBAB_NUT = new FoodProperties.Builder().saturationModifier(0.2F).nutrition(1).build();
	public static final FoodProperties CRAB_CLAW = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1F).build();
	public static final FoodProperties COOKED_CRAB_CLAW = new FoodProperties.Builder().nutrition(4).saturationModifier(0.4F).build();
	public static final FoodProperties PRICKLY_PEAR = new FoodProperties.Builder().nutrition(2).saturationModifier(0.3F).build();
	public static final FoodProperties SPLIT_COCONUT = new FoodProperties.Builder().nutrition(2).saturationModifier(0.4F).build();

	private RegisterFood() {
		throw new UnsupportedOperationException("RegisterFood contains only static declarations.");
	}

}
