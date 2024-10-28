package net.frozenblock.wilderwild.loot;

import java.util.ArrayList;
import net.frozenblock.wilderwild.loot.impl.LootPoolBuilderInterface;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class MutableLootPool {
	private ArrayList<LootPoolEntryContainer> entries = new ArrayList<>();
	private ArrayList<LootItemCondition> conditions = new ArrayList<>();
	private ArrayList<LootItemFunction> functions = new ArrayList<>();
	private NumberProvider rolls = ConstantValue.exactly(1.0F);
	private NumberProvider bonusRolls = ConstantValue.exactly(0.0F);

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

	public MutableLootPool add(Item item, int weight, LootItemFunction.Builder builder) {
		entries.add(LootItem.lootTableItem(item).setWeight(weight).apply(builder).build());
		return this;
	}
}
