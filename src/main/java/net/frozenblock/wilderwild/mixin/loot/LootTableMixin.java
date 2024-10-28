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

package net.frozenblock.wilderwild.mixin.loot;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.datagen.loot.impl.LootPoolInterface;
import net.frozenblock.wilderwild.datagen.loot.impl.LootTableBuilderInterface;
import net.frozenblock.wilderwild.datagen.loot.impl.LootTableInterface;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.ByteTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LootTable.class)
public class LootTableMixin implements LootTableInterface {

	@Shadow
	@Final
	private List<LootPool> pools;

	@Shadow
	@Final
	private LootContextParamSet paramSet;

	@Shadow
	@Final
	private Optional<ResourceLocation> randomSequence;

	@Shadow
	@Final
	private List<LootItemFunction> functions;

	@WrapOperation(
		method = "fill",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/Container;setItem(ILnet/minecraft/world/item/ItemStack;)V",
			ordinal = 1
		)
	)
	public void wilderWild$setStoneItem(Container instance, int i, ItemStack itemStack, Operation<Void> original) {
		if (instance instanceof StoneChestBlockEntity) {
			CustomData.update(DataComponents.CUSTOM_DATA, itemStack, compoundTag -> compoundTag.put("wilderwild_is_ancient", ByteTag.valueOf(true)));
		}
		original.call(instance, i, itemStack);
	}

	@Override
	public LootTable.Builder wilderWild$unbuild() {
		LootTable.Builder builder = LootTable.lootTable();
		pools.forEach(pool -> ((LootTableBuilderInterface) builder).wilderWild$withPool(pool));
		builder.setParamSet(this.paramSet);
		((LootTableBuilderInterface) builder).wilderWild$setRandomSequence(this.randomSequence);
		functions.forEach(function -> ((LootTableBuilderInterface) builder).wilderWild$apply(function));
		return builder;
	}

	@Override
	public LootTable.Builder wilderWild$unbuildWithoutPools() {
		LootTable.Builder builder = LootTable.lootTable();
		builder.setParamSet(this.paramSet);
		((LootTableBuilderInterface) builder).wilderWild$setRandomSequence(this.randomSequence);
		functions.forEach(function -> ((LootTableBuilderInterface) builder).wilderWild$apply(function));
		return builder;
	}

	@Override
	public ArrayList<LootPool.Builder> wilderWild$getLootPools() {
		ArrayList<LootPool.Builder> builders = new ArrayList<>();
		for (LootPool lootPool : pools) {
			builders.add(((LootPoolInterface) lootPool).wilderWild$unbuild());
		}
		return builders;
	}


}
