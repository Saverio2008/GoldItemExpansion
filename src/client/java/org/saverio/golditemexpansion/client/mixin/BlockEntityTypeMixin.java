package org.saverio.golditemexpansion.client.mixin;

import com.mojang.datafixers.types.Type;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import org.saverio.golditemexpansion.block.ModBlocks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Set;

import static net.minecraft.block.entity.BlockEntityType.*;

@Mixin(BlockEntityType.class)
public abstract class BlockEntityTypeMixin {

    @Mutable
    @Shadow
    @Final
    private Set<Block> blocks;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(BlockEntityFactory<?> factory, Set<Block> blocks, Type<?> type, CallbackInfo ci) {
        if ((Object) this == BlockEntityType.SKULL) {
            Set<Block> modifiable = new HashSet<>(this.blocks);
            modifiable.add(ModBlocks.GOLDEN_HEAD_BLOCK);
            this.blocks = modifiable;
        }
    }
}