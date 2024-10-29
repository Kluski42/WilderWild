package net.frozenblock.wilderwild.loot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.frozenblock.wilderwild.loot.impl.LootTableBuilderInterface;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;

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

	/**
	 * Runs the consumer on each pool
	 *
	 * @return this
	 */
	public MutableLootTable modifyPools(Consumer<MutableLootPool> consumer) {
		pools.forEach(consumer);
		return this;
	}

	/**
	 * Runs the consumer on each pool that matches the condition
	 *
	 * @return this
	 */
	public MutableLootTable modifyPools(Predicate<MutableLootPool> condition, Consumer<MutableLootPool> consumer) {
		pools.forEach(pool -> {
			if (condition.test(pool)) {
				consumer.accept(pool);
			}
		});
		return this;
	}

	/**
	 * Converts a list of loot pools to an array list of mutable loot pools
	 *
	 * @param lootPoolList loot pools to copy
	 * @return array list of converted loot pools from input
	 */
	private static ArrayList<MutableLootPool> createLootPools(List<LootPool> lootPoolList) {
		ArrayList<MutableLootPool> lootPools = new ArrayList<>();
		lootPoolList.forEach(pool -> lootPools.add(new MutableLootPool(pool)));
		return lootPools;
	}

	/**
	 * Returns if a pool has an item
	 *
	 * @param item item to check form
	 * @return predicate that checks if the pool has the given item
	 */
	public static Predicate<MutableLootPool> has(Item item) {
		return lootPool -> lootPool.hasItem(item);
	}
}
