package net.frozenblock.wilderwild.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.sound.api.instances.RestrictedMovingSound;
import net.frozenblock.lib.sound.api.instances.RestrictedMovingSoundLoop;
import net.frozenblock.lib.sound.api.predicate.SoundPredicate;
import net.frozenblock.lib.sound.impl.EntityLoopingSoundInterface;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.monster.EnderMan;

@Environment(EnvType.CLIENT)
public class ClientMethods {

    public static void playClientEnderManSound(EnderMan enderMan) {
        Minecraft client = Minecraft.getInstance();
        if (client.level != null && enderMan.isAlive()) {
            client.getSoundManager().play(new RestrictedMovingSound<>(enderMan, SoundEvents.ENDERMAN_STARE, SoundSource.HOSTILE, 2.5F, 1F, SoundPredicate.notSilentAndAlive()));
        }
    }

	public static void playClientEnderManAngerLoop(EnderMan enderMan) {
		((EntityLoopingSoundInterface) enderMan).addSound(BuiltInRegistries.SOUND_EVENT.getKey(RegisterSounds.ENTITY_ENDERMAN_ANGER_LOOP), SoundSource.HOSTILE, 1.0F, 0.9F, WilderSharedConstants.id("enderman_anger"));
		Minecraft.getInstance().getSoundManager().play(new RestrictedMovingSoundLoop<>(enderMan, RegisterSounds.ENTITY_ENDERMAN_ANGER_LOOP, SoundSource.HOSTILE, 1.0F, 0.9F, SoundPredicate.getPredicate(WilderSharedConstants.id("enderman_anger"))));
		((WilderEnderman) enderMan).setCanPlayLoopingSound(false);
	}
}
