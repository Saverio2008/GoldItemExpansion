package org.saverio.golditemexpansion.client.mixin;

import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.SkullBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.block.BlockState;
import org.saverio.golditemexpansion.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(SkullBlockEntityRenderer.class)
public class SkullBlockEntityRendererMixin {

    @Unique
    private static final Identifier GOLDEN_HEAD_TEXTURE = new Identifier("golditemexpansion", "block/golden_head");

    // 用于标记当前是否渲染金头
    @Unique
    private static final ThreadLocal<Boolean> isRenderingGoldenHead = ThreadLocal.withInitial(() -> false);

    @Inject(
            method = "render(Lnet/minecraft/block/entity/SkullBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V",
            at = @At("HEAD")
    )
    private void onRenderHead(SkullBlockEntity skull, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {
        BlockState state = skull.getCachedState();
        isRenderingGoldenHead.set(state.getBlock() == ModBlocks.GOLDEN_HEAD_BLOCK);
    }

    @Inject(
            method = "render(Lnet/minecraft/block/entity/SkullBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V",
            at = @At("RETURN")
    )
    private void onRenderReturn(SkullBlockEntity skull, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {
        isRenderingGoldenHead.remove();
    }

    @Redirect(
            method = "getRenderLayer(Lnet/minecraft/block/SkullBlock$SkullType;Lcom/mojang/authlib/GameProfile;)Lnet/minecraft/client/render/RenderLayer;",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"
            )
    )
    private static Object redirectTextureMapGet(Map<?, ?> map, Object key) {
        if (isRenderingGoldenHead.get()) {
            return GOLDEN_HEAD_TEXTURE;
        }
        return map.get(key);
    }
}