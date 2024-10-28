package net.frozenblock.wilderwild.mixin.loot;

import net.frozenblock.wilderwild.datagen.loot.impl.LootPoolBuilderInterface;
import net.frozenblock.wilderwild.datagen.loot.impl.LootPoolInterface;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import java.util.List;

@Mixin(LootPool.class)
public class LootPoolMixin implements LootPoolInterface {
	@Shadow
	@Final
	public List<LootPoolEntryContainer> entries;

	@Shadow
	@Final
	public List<LootItemCondition> conditions;

	@Shadow
	@Final
	public List<LootItemFunction> functions;

	@Shadow
	@Final
	public NumberProvider rolls;

	@Shadow
	@Final
	public NumberProvider bonusRolls;

	@Override
	public LootPool.Builder wilderWild$unbuild() {
		LootPool.Builder builder = LootPool.lootPool();
		this.entries.forEach(entry -> ((LootPoolBuilderInterface) builder).wilderWild$add(entry));
		this.conditions.forEach(condition -> ((LootPoolBuilderInterface) builder).wilderWild$when(condition));
		this.functions.forEach(function -> ((LootPoolBuilderInterface) builder).wilderWild$apply(function));
		builder.setRolls(this.rolls).setBonusRolls(this.bonusRolls);
		return builder;
	}
}
