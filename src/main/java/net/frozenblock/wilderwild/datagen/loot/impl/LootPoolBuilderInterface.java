package net.frozenblock.wilderwild.datagen.loot.impl;

import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public interface LootPoolBuilderInterface {

	LootPool.Builder wilderWild$add(LootPoolEntryContainer entry);

	LootPool.Builder wilderWild$when(LootItemCondition condition);

	LootPool.Builder wilderWild$apply(LootItemFunction function);
}
