package org.saverio.golditemexpansion.client.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.WallSkullBlock;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.SkullBlockEntityModel;
import net.minecraft.client.render.block.entity.SkullBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationPropertyHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.saverio.golditemexpansion.block.GoldenHeadBlock;

import java.util.Map;

@Mixin(SkullBlockEntityRenderer.class)
public class SkullBlockEntityRendererMixin {

    @Unique
    private static final Identifier GOLDEN_HEAD_TEXTURE = new Identifier("golditemexpansion", "block/golden_head");

    @Shadow @Final
    private Map<SkullBlock.SkullType, SkullBlockEntityModel> MODELS;

    @Inject(
            method = "render(Lnet/minecraft/block/entity/SkullBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void renderGoldenHead(SkullBlockEntity skullEntity, float tickDelta, MatrixStack matrices,
                                  VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {

        BlockState state = skullEntity.getCachedState();
        if (state.getBlock() instanceof GoldenHeadBlock) {
            SkullBlock.SkullType skullType = ((SkullBlock) state.getBlock()).getSkullType();
            SkullBlockEntityModel model = MODELS.get(skullType);

            VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(GOLDEN_HEAD_TEXTURE));

            Direction direction = state.getBlock() instanceof WallSkullBlock ? state.get(WallSkullBlock.FACING) : null;
            int rotation = direction != null ? RotationPropertyHelper.fromDirection(direction.getOpposite()) : state.get(SkullBlock.ROTATION);
            float yaw = RotationPropertyHelper.toDegrees(rotation);
            float animationProgress = skullEntity.getPoweredTicks(tickDelta);

            matrices.push();
            if (direction == null) {
                matrices.translate(0.5F, 0.0F, 0.5F);
            } else {
                matrices.translate(0.5F - direction.getOffsetX() * 0.25F, 0.25F, 0.5F - direction.getOffsetZ() * 0.25F);
            }
            matrices.scale(-1.0F, -1.0F, 1.0F);

            model.setHeadRotation(animationProgress, yaw, 0.0F);
            model.render(matrices, consumer, light, overlay, 1f, 1f, 1f, 1f);

            matrices.pop();
            ci.cancel();
        }
    }
}