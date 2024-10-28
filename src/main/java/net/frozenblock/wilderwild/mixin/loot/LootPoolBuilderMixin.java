package net.frozenblock.wilderwild.mixin.loot;

import com.google.common.collect.ImmutableList;
import net.frozenblock.wilderwild.datagen.loot.impl.LootPoolBuilderInterface;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LootPool.Builder.class)
public class LootPoolBuilderMixin implements LootPoolBuilderInterface {
	@Shadow
	@Final
	private ImmutableList.Builder<LootPoolEntryContainer> entries;

	@Shadow
	@Final
	private ImmutableList.Builder<LootItemCondition> conditions;

	@Shadow
	@Final
	private ImmutableList.Builder<LootItemFunction> functions;

//	@Override
//	public LootPool.Builder wilderWild$unBuild(LootPool lootPool) {
//		LootPool.Builder builder = LootPool.lootPool();
//		lootPool.entries.forEach(entry -> ((LootPoolBuilderInterface) builder).wilderWild$add(entry));
//		lootPool.conditions.forEach(condition -> ((LootPoolBuilderInterface) builder).wilderWild$when(condition));
//		lootPool.functions.forEach(function -> ((LootPoolBuilderInterface) builder).wilderWild$apply(function));
//		builder.setRolls(lootPool.rolls).setBonusRolls(lootPool.bonusRolls);
//		return builder;
//	}

	@Override
	public LootPool.Builder wilderWild$add(LootPoolEntryContainer entry) {
		entries.add(entry);
		return getSelf();
	}

	@Override
	public LootPool.Builder wilderWild$when(LootItemCondition condition) {
		conditions.add(condition);
		return getSelf();
	}

	@Override
	public LootPool.Builder wilderWild$apply(LootItemFunction function) {
		functions.add(function);
		return getSelf();
	}

	@Unique
	private LootPool.Builder getSelf() {
		return (LootPool.Builder) (Object) this;
	}
}
