/*
 * Copyright 2023-2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.world.biome;

import com.mojang.datafixers.util.Pair;
import net.frozenblock.lib.worldgen.biome.api.FrozenBiome;
import net.frozenblock.lib.worldgen.biome.api.parameters.Continentalness;
import net.frozenblock.lib.worldgen.biome.api.parameters.Erosion;
import net.frozenblock.lib.worldgen.biome.api.parameters.Humidity;
import net.frozenblock.lib.worldgen.biome.api.parameters.Temperature;
import net.frozenblock.lib.worldgen.biome.api.parameters.Weirdness;
import net.frozenblock.wilderwild.config.WorldgenConfig;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.world.additions.feature.WilderMiscPlaced;
import net.frozenblock.wilderwild.world.additions.feature.WilderPlacedFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.function.Consumer;

public final class MagmaCaves extends FrozenBiome {
	public static final Climate.Parameter TEMPERATURE = Climate.Parameter.span(Temperature.WARM, Temperature.HOT);
	public static final Climate.Parameter HUMIDITY = Climate.Parameter.span(Humidity.ARID, Humidity.NEUTRAL);
	public static final Climate.Parameter CONTINENTALNESS = Climate.Parameter.span(Continentalness.COAST, Continentalness.FAR_INLAND);
	public static final Climate.Parameter EROSION = Climate.Parameter.span(Erosion.EROSION_2, Erosion.EROSION_4);
	public static final Climate.Parameter WEIRDNESS = Climate.Parameter.span(Weirdness.weirdnesses[3], Weirdness.weirdnesses[8]);
	public static final float OFFSET = 0.000F;
	public static final float TEMP = 1.0F;
	public static final float DOWNFALL = 0.4F;
	public static final int WATER_COLOR = 9817343;
	public static final int WATER_FOG_COLOR = 6069471;
	public static final int FOG_COLOR = 0;
	public static final int SKY_COLOR = OverworldBiomes.calculateSkyColor(0F);
	public static final MagmaCaves INSTANCE = new MagmaCaves();

	@Override
	public String modID() {
		return WilderSharedConstants.MOD_ID;
	}

	@Override
	public String biomeID() {
		return "magma_caves";
	}

	@Override
	public float temperature() {
		return TEMP;
	}

	@Override
	public float downfall() {
		return DOWNFALL;
	}

	@Override
	public boolean hasPrecipitation() {
		return true;
	}

	@Override
	public int skyColor() {
		return SKY_COLOR;
	}

	@Override
	public int fogColor() {
		return FOG_COLOR;
	}

	@Override
	public int waterColor() {
		return WATER_COLOR;
	}

	@Override
	public int waterFogColor() {
		return WATER_FOG_COLOR;
	}

	@Override
	public @Nullable Integer foliageColorOverride() {
		return null;
	}

	@Override
	public @Nullable Integer grassColorOverride() {
		return null;
	}

	@Override
	public @Nullable AmbientParticleSettings ambientParticleSettings() {
		return new AmbientParticleSettings(ParticleTypes.LARGE_SMOKE, 0.0015F);
	}

	@Override
	public @NotNull Holder<SoundEvent> ambientLoopSound() {
		return RegisterSounds.AMBIENT_JELLYFISH_CAVES_LOOP;
	}

	@Override
	public @NotNull AmbientMoodSettings ambientMoodSettings() {
		return AmbientMoodSettings.LEGACY_CAVE_SETTINGS;
	}

	@Override
	public @NotNull AmbientAdditionsSettings ambientAdditionsSound() {
		return new AmbientAdditionsSettings(RegisterSounds.AMBIENT_JELLYFISH_CAVES_ADDITIONS, 0.005D);
	}

	@Override
	public @NotNull Music backgroundMusic() {
		return Musics.createGameMusic(RegisterSounds.MUSIC_OVERWORLD_JELLYFISH_CAVES);
	}

	@Override
	public void addFeatures(@NotNull BiomeGenerationSettings.Builder features) {
		BiomeDefaultFeatures.addFossilDecoration(features);
		BiomeDefaultFeatures.addDefaultCrystalFormations(features);
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, CavePlacements.MONSTER_ROOM_DEEP);
		features.addFeature(GenerationStep.Decoration.LAKES, WilderMiscPlaced.MAGMA_LAVA_POOL.getKey());
		features.addFeature(GenerationStep.Decoration.LAKES, WilderMiscPlaced.LAVA_LAKE_EXTRA.getKey());
		features.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, WilderMiscPlaced.LAVA_SPRING_EXTRA.getKey());
		BiomeDefaultFeatures.addDefaultUndergroundVariety(features);
		BiomeDefaultFeatures.addSurfaceFreezing(features);
		BiomeDefaultFeatures.addPlainGrass(features);
		BiomeDefaultFeatures.addDefaultOres(features, false);
		BiomeDefaultFeatures.addDefaultSoftDisks(features);
		BiomeDefaultFeatures.addPlainVegetation(features);
		BiomeDefaultFeatures.addDefaultMushrooms(features);
		BiomeDefaultFeatures.addDefaultExtraVegetation(features);
		BiomeDefaultFeatures.addDefaultCarversAndLakes(features);
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, WilderMiscPlaced.FOSSIL_LAVA.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderPlacedFeatures.UPSIDE_DOWN_MAGMA.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.MAGMA_DISK.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.OBSIDIAN_DISK.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.BASALT_PILE.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.BASALT_SPIKE.getKey());
		features.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.DOWNWARDS_BASALT_COLUMN.getKey());
		features.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderMiscPlaced.MAGMA_PATH.getKey());
		features.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderMiscPlaced.FIRE_PATCH_MAGMA.getKey());
	}

	@Override
	public void addSpawns(MobSpawnSettings.Builder spawns) {
		BiomeDefaultFeatures.commonSpawns(spawns);
	}

	@Override
	public void injectToOverworld(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> parameters) {
		if (WorldgenConfig.get().biomeGeneration.generateMagmaCaves) {
			this.addBottomBiome(
				parameters,
				TEMPERATURE,
				HUMIDITY,
				CONTINENTALNESS,
				EROSION,
				WEIRDNESS,
				OFFSET
			);
		}
	}

}
