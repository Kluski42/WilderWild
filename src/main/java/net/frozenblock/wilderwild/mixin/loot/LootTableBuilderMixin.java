package net.frozenblock.wilderwild.mixin.loot;

import com.google.common.collect.ImmutableList;
import net.frozenblock.wilderwild.datagen.loot.impl.LootTableBuilderInterface;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import java.util.List;
import java.util.Optional;

@Mixin(LootTable.Builder.class)
public class LootTableBuilderMixin implements LootTableBuilderInterface {
	@Shadow
	@Final
	private ImmutableList.Builder<LootPool> pools;

	@Shadow
	@Final
	private ImmutableList.Builder<LootItemFunction> functions;

	@Shadow
	private Optional<ResourceLocation> randomSequence;

	@Override
	public LootTable.Builder wilderWild$withPool(LootPool pool) {
		this.pools.add(pool);
		return getSelf();
	}

	@Override
	public LootTable.Builder wilderWild$setRandomSequence(Optional<ResourceLocation> randomSequence) {
		this.randomSequence = randomSequence;
		return getSelf();
	}

	@Override
	public LootTable.Builder wilderWild$apply(LootItemFunction function) {
		this.functions.add(function);
		return getSelf();
	}

	@Unique
	private LootTable.Builder getSelf() {
		return (LootTable.Builder) (Object) this;
	}
}
