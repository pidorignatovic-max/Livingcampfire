package com.crosskai.livingcampfires;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class PortableCampfireItem extends Item {
    private static final int DURABILITY = 600, EFFECT_INTERVAL = 40, PARTICLE_INTERVAL = 80;
    private final CampfireVariant variant;
    public PortableCampfireItem(CampfireVariant variant, Settings settings) {
        super(settings.maxDamage(DURABILITY).maxCount(1));
        this.variant = variant;
    }
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient || !(entity instanceof PlayerEntity player) || player.isCreative()) return;
        Hand hand;
        if (player.getStackInHand(Hand.MAIN_HAND) == stack) hand = Hand.MAIN_HAND;
        else if (player.getStackInHand(Hand.OFF_HAND) == stack) hand = Hand.OFF_HAND;
        else return;
        if (world.getTime() % EFFECT_INTERVAL != 0) return;
        EffectApplier.apply(variant, player, world);
        EquipmentSlot equipmentSlot = hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
        stack.damage(1, player, equipmentSlot);
        if (world.getTime() % PARTICLE_INTERVAL == 0 && world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.SMOKE, player.getX(), player.getY() + 1.0, player.getZ(), 2, 0.2, 0.1, 0.2, 0.01);
            if (world.getRandom().nextInt(4) == 0) world.playSound(null, player.getBlockPos(), SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.PLAYERS, 0.2F, 0.8F + world.getRandom().nextFloat() * 0.4F);
        }
    }
    @Override
    public boolean hasGlint(ItemStack stack) { return variant == CampfireVariant.HERBAL_MYSTIC; }
}
