package com.crosskai.livingcampfires;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;

public class PortableCampfireItem extends Item {
    private static final int DURABILITY = 600;
    private static final int PARTICLE_INTERVAL = 80;

    private final CampfireVariant variant;

    public PortableCampfireItem(CampfireVariant variant, Settings settings) {
        super(settings.maxDamage(DURABILITY).maxCount(1));
        this.variant = variant;
    }

    /**
     * Called by the server tick handler only when this item is actually in a player's hand.
     * Keeping this logic in the server tick avoids relying on inventoryTick's stack identity.
     */
    public void tickWhileHeld(ItemStack stack, PlayerEntity player, Hand hand, ServerWorld world) {
        if (stack.isEmpty()) {
            return;
        }

        EffectApplier.apply(variant, player, world);

        // Creative players get the effects, but their items do not lose durability.
        if (!player.isCreative()) {
            EquipmentSlot equipmentSlot = hand == Hand.MAIN_HAND
                    ? EquipmentSlot.MAINHAND
                    : EquipmentSlot.OFFHAND;
            stack.damage(1, player, equipmentSlot);
        }

        if (world.getTime() % PARTICLE_INTERVAL == 0L) {
            world.spawnParticles(
                    ParticleTypes.SMOKE,
                    player.getX(),
                    player.getY() + 1.0,
                    player.getZ(),
                    2, 0.2, 0.1, 0.2, 0.01
            );

            if (world.getRandom().nextInt(4) == 0) {
                world.playSound(
                        null,
                        player.getBlockPos(),
                        SoundEvents.BLOCK_CAMPFIRE_CRACKLE,
                        SoundCategory.PLAYERS,
                        0.2F,
                        0.8F + world.getRandom().nextFloat() * 0.4F
                );
            }
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return variant == CampfireVariant.HERBAL_MYSTIC;
    }
}
