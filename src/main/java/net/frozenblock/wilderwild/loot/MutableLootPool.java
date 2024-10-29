package net.frozenblock.wilderwild.loot;

import java.util.ArrayList;
import net.frozenblock.wilderwild.loot.impl.LootPoolBuilderInterface;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class MutableLootPool {
	public ArrayList<LootPoolEntryContainer> entries = new ArrayList<>();
	public ArrayList<LootItemCondition> conditions = new ArrayList<>();
	public ArrayList<LootItemFunction> functions = new ArrayList<>();
	public NumberProvider rolls = ConstantValue.exactly(1.0F);
	public NumberProvider bonusRolls = ConstantValue.exactly(0.0F);

	public MutableLootPool() {
	}

	public MutableLootPool(LootPool lootPool) {
		entries.addAll(lootPool.entries);
		conditions.addAll(lootPool.conditions);
		functions.addAll(lootPool.functions);
		rolls = lootPool.rolls;
		bonusRolls = lootPool.bonusRolls;
	}

	public LootPool build() {
		LootPool.Builder builder = LootPool.lootPool();
		entries.forEach(entry -> ((LootPoolBuilderInterface) builder).wilderWild$add(entry));
		conditions.forEach(condition -> ((LootPoolBuilderInterface) builder).wilderWild$when(condition));
		functions.forEach(function -> ((LootPoolBuilderInterface) builder).wilderWild$apply(function));
		builder.setRolls(rolls);
		builder.setBonusRolls(bonusRolls);
		return builder.build();
	}

	/**
	 * Adds one item to the loot pool
	 *
	 * @param item    item to add
	 * @param weight  how likely the item is to get drawn from the pool
	 * @param builder idk lol
	 * @return this
	 */
	public MutableLootPool add(ItemLike item, int weight, LootItemFunction.Builder builder) {
		entries.add(LootItem.lootTableItem(item).setWeight(weight).apply(builder).build());
		return this;
	}

	/**
	 * Adds one or more items to the loot pool with the same weight
	 *
	 * @param items   items to add
	 * @param weight  how likely the items are to get drawn from the pool
	 * @param builder idk lol
	 * @return this
	 */
	public MutableLootPool addAll(int weight, LootItemFunction.Builder builder, ItemLike... items) {
		for (ItemLike item : items) {
			entries.add(LootItem.lootTableItem(item).setWeight(weight).apply(builder).build());
		}
		return this;
	}

	/**
	 * Returns if the loot pool contains the given item
	 *
	 * @param item item to check for
	 * @return if item was found
	 */
	public boolean hasItem(Item item) {
		for (LootPoolEntryContainer entryContainer : entries) {
			if (entryContainer instanceof LootItem lootItem) {
				if (item.equals(lootItem.item.value())) return true;
			}
		}
		return false;
	}

	/**
	 * Returns if the loot pool contains any of the given items
	 *
	 * @param items items to check for
	 * @return if any of the items were found
	 */
	public boolean hasAnyItems(Item... items) {
		for (LootPoolEntryContainer entryContainer : entries) {
			if (entryContainer instanceof LootItem lootItem) {
				for (Item item : items) {
					if (item.equals(lootItem.item.value())) return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns if the loot table contains all the given items
	 *
	 * @param items items to check for
	 * @return if all of the items were found
	 */
	public boolean hasAllItems(Item... items) {
		for (Item item : items) {
			boolean found = false;
			for (LootPoolEntryContainer entryContainer : entries) {
				if (entryContainer instanceof LootItem lootItem) {
					if (item.equals(lootItem.item.value())) {
						found = true;
						break;
					}
				}
			}
			if (!found) return false;
		}
		return true;
	}
}
