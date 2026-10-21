package com.crosskai.livingcampfires.mixin;

import com.crosskai.livingcampfires.LivingCampfireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityType.class)
public abstract class CampfireBlockEntityTypeMixin {
    @Inject(method = "supports", at = @At("HEAD"), cancellable = true)
    private void livingcampfires$allowCustomCampfires(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this == BlockEntityType.CAMPFIRE && state.getBlock() instanceof LivingCampfireBlock) cir.setReturnValue(true);
    }
}
