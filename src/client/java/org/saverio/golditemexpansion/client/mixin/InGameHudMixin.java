package org.saverio.golditemexpansion.client.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.saverio.golditemexpansion.client.mixin.accessor.SpriteAtlasHolderAccessor;
import org.saverio.golditemexpansion.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Unique
    private static final ThreadLocal<StatusEffect> currentEffect = new ThreadLocal<>();
    @Unique
    private static final int MAX_LOGS = 50;
    @Unique
    private static int logCount = 0;

    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"))
    private void beforeRenderStatusEffectOverlay(DrawContext context, CallbackInfo ci) {
        currentEffect.remove();  // 清理，防止残留
        logCount = 0;
    }

    // 在调用 getSprite 前注入，捕获当前的StatusEffectInstance对应的StatusEffect
    @Inject(method = "renderStatusEffectOverlay",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/texture/StatusEffectSpriteManager;getSprite(Lnet/minecraft/entity/effect/StatusEffect;)Lnet/minecraft/client/texture/Sprite;")
    )
    private void captureCurrentEffect(DrawContext context, CallbackInfo ci,
                                      @Local StatusEffectInstance statusEffectInstance) {
        currentEffect.set(statusEffectInstance.getEffectType());
    }

    @SuppressWarnings("resource")
    @Redirect(method = "renderStatusEffectOverlay",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/texture/StatusEffectSpriteManager;getSprite(Lnet/minecraft/entity/effect/StatusEffect;)Lnet/minecraft/client/texture/Sprite;"))
    private Sprite redirectGetSprite(StatusEffectSpriteManager manager, StatusEffect effect) {
        StatusEffect actualEffect = currentEffect.get();
        if (actualEffect != null) {
            Identifier id = Registries.STATUS_EFFECT.getId(actualEffect);
            if (id != null && (actualEffect == ModEffects.GOD_POSITIVE_EFFECT || actualEffect == ModEffects.GOD_NEGATIVE_EFFECT)) {
                SpriteAtlasTexture atlas = ((SpriteAtlasHolderAccessor) manager).golditemexpansion$getAtlas();
                Sprite custom = atlas.getSprite(id);
                if (custom != null && custom.getContents() != null && !custom.getContents().getId().getPath().equals("missingno")) {
                    System.out.println("[GoldItemExpansion] 替换自定义状态效果图标为自定义Sprite: " + id);
                    return custom;
                }
            }
        }
        // fallback 使用默认贴图
        return manager.getSprite(effect);
    }

    @Inject(method = "renderStatusEffectOverlay", at = @At(value = "RETURN"))
    private void afterRenderStatusEffectOverlay(DrawContext context, CallbackInfo ci) {
        currentEffect.remove();  // 清理避免内存泄漏
    }

    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"))
    private void debugRenderStatusEffectOverlay(DrawContext context, CallbackInfo ci) {
        if (logCount >= MAX_LOGS) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        StatusEffectSpriteManager manager = client.getStatusEffectSpriteManager();

        client.player.getStatusEffects().forEach(effectInstance -> {
            if (logCount >= MAX_LOGS) return;

            StatusEffect effect = effectInstance.getEffectType();
            Identifier id = Registries.STATUS_EFFECT.getId(effect);

            if (id != null && id.getNamespace().equals("golditemexpansion") &&
                    (id.getPath().equals("god_positive_status_effect") || id.getPath().equals("god_negative_status_effect"))) {

                Sprite sprite = manager.getSprite(effect);
                if (sprite == null) {
                    System.out.println("[GoldItemExpansion][WARN] Sprite is null for effect " + id);
                    return;
                }

                if (sprite.getContents() == null) {
                    System.out.println("[GoldItemExpansion][WARN] Sprite contents is null for effect " + id);
                    return;
                }

                int width = sprite.getContents().getWidth();
                int height = sprite.getContents().getHeight();

                float minU = sprite.getMinU();
                float maxU = sprite.getMaxU();
                float minV = sprite.getMinV();
                float maxV = sprite.getMaxV();

                boolean uvValid = (minU >= 0 && maxU <= 1 && minV >= 0 && maxV <= 1 && minU < maxU && minV < maxV);
                boolean sizeValid = (width == 16 || width == 32) && (height == 16 || height == 32);

                System.out.println("[GoldItemExpansion][DEBUG] Effect: " + id +
                        ", Sprite Atlas: " + sprite.getAtlasId() +
                        ", Sprite Contents Hash: " + sprite.getContents().hashCode() +
                        ", Size: " + width + "x" + height +
                        ", UV: minU=" + minU +
                        ", maxU=" + maxU +
                        ", minV=" + minV +
                        ", maxV=" + maxV);

                if (!uvValid) {
                    System.out.println("[GoldItemExpansion][WARN] UV coordinates invalid for " + id +
                            ": minU=" + minU + ", maxU=" + maxU + ", minV=" + minV + ", maxV=" + maxV);
                }

                if (!sizeValid) {
                    System.out.println("[GoldItemExpansion][WARN] Sprite size unusual for " + id +
                            ": width=" + width + ", height=" + height);
                }

                logCount++;
            }
        });
    }
}