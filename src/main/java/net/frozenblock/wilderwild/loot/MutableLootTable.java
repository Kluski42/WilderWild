package net.frozenblock.wilderwild.loot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import net.frozenblock.wilderwild.loot.impl.LootTableBuilderInterface;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class MutableLootTable {
	private ArrayList<MutableLootPool> pools = new ArrayList<>();
	private ArrayList<LootItemFunction> functions = new ArrayList<>();
	private LootContextParamSet paramSet = LootTable.DEFAULT_PARAM_SET;
	private ResourceLocation randomSequence;

	public MutableLootTable(LootTable table) {
		pools = createLootPools(table.pools);
		functions.addAll(table.functions);
		paramSet = table.paramSet;
		randomSequence = table.randomSequence.orElse(null);
	}

	public LootTable build() {
		LootTable.Builder builder = LootTable.lootTable();
		builder.setParamSet(paramSet);
		builder.setRandomSequence(randomSequence);
		pools.forEach(mPool -> ((LootTableBuilderInterface) builder).wilderWild$withPool(mPool.build()));
		functions.forEach(function -> ((LootTableBuilderInterface) builder).wilderWild$apply(function));
		return builder.build();
	}


	public MutableLootTable addToTable(Predicate<MutableLootPool> condition) {
		pools.forEach(pool -> {
			if (condition.test(pool)) {
				pool.add(WWBlocks.BAOBAB_LOG.asItem(), 3, SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)));
				pool.add(WWBlocks.CYPRESS_LOG.asItem(), 3, SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)));
				pool.add(WWBlocks.MAPLE_LOG.asItem(), 3, SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)));
				pool.add(WWBlocks.PALM_LOG.asItem(), 3, SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)));
			}
		});
		return this;
	}

	private static ArrayList<MutableLootPool> createLootPools(List<LootPool> lootPoolList) {
		ArrayList<MutableLootPool> lootPools = new ArrayList<>();
		lootPoolList.forEach(pool -> lootPools.add(new MutableLootPool(pool)));
		return lootPools;
	}
}
