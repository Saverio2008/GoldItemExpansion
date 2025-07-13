package org.saverio.golditemexpansion.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.saverio.golditemexpansion.client.mixin.accessor.SpriteAtlasHolderAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Unique
    private static final int LOG_INTERVAL = 100;
    @Unique
    private static int logCount = 0;

    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"))
    private void beforeRenderStatusEffectOverlay(DrawContext context, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        StatusEffectSpriteManager manager = client.getStatusEffectSpriteManager();

        client.player.getStatusEffects().forEach(effectInstance -> {
            // 每 LOG_INTERVAL 次打印一次，避免日志刷屏
            if (logCount % LOG_INTERVAL == 0) {
                StatusEffect effect = effectInstance.getEffectType();
                Identifier id = Registries.STATUS_EFFECT.getId(effect);
                if (id != null && id.getNamespace().equals("golditemexpansion") &&
                        (id.getPath().equals("god_positive_status_effect") || id.getPath().equals("god_negative_status_effect"))) {

                    Sprite sprite = manager.getSprite(effect);
                    if (sprite == null || sprite.getContents() == null) return;

                    System.out.printf("[GoldItemExpansion][DEBUG #%d] Effect: %s, Atlas: %s, Size: %dx%d, UV: minU=%.3f maxU=%.3f minV=%.3f maxV=%.3f\n",
                            logCount,
                            id,
                            sprite.getAtlasId(),
                            sprite.getContents().getWidth(),
                            sprite.getContents().getHeight(),
                            sprite.getMinU(),
                            sprite.getMaxU(),
                            sprite.getMinV(),
                            sprite.getMaxV());
                }
            }
            logCount++;
        });
    }

    @SuppressWarnings("resource")
    @Redirect(
            method = "renderStatusEffectOverlay",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/texture/StatusEffectSpriteManager;getSprite(Lnet/minecraft/entity/effect/StatusEffect;)Lnet/minecraft/client/texture/Sprite;"
            )
    )
    private Sprite redirectGetSprite(StatusEffectSpriteManager manager, StatusEffect effect) {
        Identifier id = Registries.STATUS_EFFECT.getId(effect);
        if (id != null) {
            SpriteAtlasTexture atlas = ((SpriteAtlasHolderAccessor) manager).golditemexpansion$getAtlas();
            Sprite custom = atlas.getSprite(id);
            if (custom != null && custom.getContents() != null && !custom.getContents().getId().getPath().equals("missingno")) {
                return custom;
            }
        }
        return manager.getSprite(effect);
    }
}