package org.saverio.golditemexpansion.client.mixin;

import net.minecraft.block.SkullBlock;
import net.minecraft.client.render.block.entity.SkullBlockEntityRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;

@Mixin(SkullBlockEntityRenderer.class)
public class SkullBlockEntityRendererMixin {

    @Unique
    private static final Identifier GOLDEN_HEAD_TEXTURE = new Identifier("golditemexpansion", "block/golden_head");

    @Redirect(
            method = "getRenderLayer(Lnet/minecraft/block/SkullBlock$SkullType;Lcom/mojang/authlib/GameProfile;)Lnet/minecraft/client/render/RenderLayer;",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"
            )
    )
    private static Object redirectTextureMapGet(Map<?, ?> map, Object key) {
        if (key instanceof SkullBlock.Type skullType) {
            if (skullType == SkullBlock.Type.SKELETON) {
                return GOLDEN_HEAD_TEXTURE;
            }
        }
        return map.get(key);
    }
}