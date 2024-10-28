package net.frozenblock.wilderwild.datagen.loot.impl;

import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;

public interface LootTableBuilderInterface {
	LootTable.Builder wilderWild$withPool(LootPool pool);

	LootTable.Builder wilderWild$setRandomSequence(Optional<ResourceLocation> randomSequence);

	LootTable.Builder wilderWild$apply(LootItemFunction function);

}
