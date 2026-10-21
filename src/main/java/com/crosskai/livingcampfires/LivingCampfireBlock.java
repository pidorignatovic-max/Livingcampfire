package com.crosskai.livingcampfires;

import net.minecraft.block.CampfireBlock;

public class LivingCampfireBlock extends CampfireBlock {
    private final CampfireVariant variant;
    public LivingCampfireBlock(CampfireVariant variant, boolean emitsParticles, int fireDamage, Settings settings) {
        super(emitsParticles, fireDamage, settings);
        this.variant = variant;
    }
    public CampfireVariant getVariant() { return variant; }
}
