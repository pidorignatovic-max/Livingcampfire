package com.crosskai.livingcampfires;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

public final class EffectApplier {
    private EffectApplier() {}
    public static void apply(CampfireVariant variant, PlayerEntity player, World world) {
        switch (variant) {
            case WARM -> add(player, StatusEffects.REGENERATION, 100, 0);
            case HERBAL_SPEED -> add(player, StatusEffects.SPEED, 100, 0);
            case HERBAL_DEFENSE -> add(player, StatusEffects.RESISTANCE, 100, 0);
            case HERBAL_UTILITY -> add(player, StatusEffects.JUMP_BOOST, 100, 0);
            case HERBAL_MYSTIC -> {
                add(player, StatusEffects.LUCK, 100, 0);
                add(player, StatusEffects.NIGHT_VISION, 100, 0);
            }
            case FROST -> add(player, StatusEffects.SLOWNESS, 60, 0);
            case SIGNAL -> add(player, StatusEffects.GLOWING, 100, 0);
        }
    }
    private static void add(PlayerEntity player, RegistryEntry<StatusEffect> effect, int duration, int amplifier) {
        player.addStatusEffect(new StatusEffectInstance(effect, duration, amplifier, true, false, true));
    }
}
