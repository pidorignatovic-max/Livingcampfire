package com.crosskai.livingcampfires;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.server.world.ServerWorld;

public class LivingCampfires implements ModInitializer {
    public static final String MOD_ID = "livingcampfires";

    @Override
    public void onInitialize() {
        for (CampfireVariant variant : CampfireVariant.values()) registerVariant(variant);
        ServerTickEvents.END_WORLD_TICK.register(LivingCampfires::tickWorld);
        System.out.println("[Living Campfires] Loaded 7 campfire blocks and 7 portable items.");
    }

    private static void registerVariant(CampfireVariant variant) {
        String path = variant.name().toLowerCase();
        Identifier blockId = Identifier.of(MOD_ID, path + "_campfire");
        Identifier itemId = Identifier.of(MOD_ID, "portable_" + path + "_campfire");
        LivingCampfireBlock block = Registry.register(Registries.BLOCK, blockId,
            new LivingCampfireBlock(variant, true, 2,
                AbstractBlock.Settings.copy(Blocks.CAMPFIRE).registryKey(RegistryKey.of(RegistryKeys.BLOCK, blockId))));
        Registry.register(Registries.ITEM, blockId,
            new BlockItem(block, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, blockId))));
        Registry.register(Registries.ITEM, itemId,
            new PortableCampfireItem(variant, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, itemId))));
    }

    private static void tickWorld(ServerWorld world) {
        if (world.getTime() % 40L != 0L) return;
        for (PlayerEntity player : world.getPlayers()) {
            BlockPos center = player.getBlockPos();
            int radius = 5;
            for (BlockPos pos : BlockPos.iterate(center.add(-radius, -3, -radius), center.add(radius, 3, radius))) {
                BlockState state = world.getBlockState(pos);
                if (!(state.getBlock() instanceof LivingCampfireBlock campfire) || !state.get(LivingCampfireBlock.LIT)) continue;
                if (player.squaredDistanceTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) > 25.0) continue;
                EffectApplier.apply(campfire.getVariant(), player, world);
                if (campfire.getVariant() == CampfireVariant.SIGNAL) {
                    world.spawnParticles(net.minecraft.particle.ParticleTypes.CAMPFIRE_COSY_SMOKE,
                        pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5, 4, 0.25, 0.2, 0.25, 0.01);
                    player.addStatusEffect(new net.minecraft.entity.effect.StatusEffectInstance(
                        net.minecraft.entity.effect.StatusEffects.GLOWING, 100, 0, true, false, true));
                }
            }
        }
    }
}
