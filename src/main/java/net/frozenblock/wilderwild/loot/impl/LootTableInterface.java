package net.frozenblock.wilderwild.loot.impl;

import java.util.ArrayList;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;

public interface LootTableInterface {
	LootTable.Builder wilderWild$unbuild();

	LootTable.Builder wilderWild$unbuildWithoutPools();

	ArrayList<LootPool.Builder> wilderWild$getLootPools();
}
