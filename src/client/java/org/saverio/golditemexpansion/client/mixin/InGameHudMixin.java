package org.saverio.golditemexpansion.client.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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
            if (logCount % LOG_INTERVAL == 0) {
                StatusEffect effect = effectInstance.getEffectType();
                Identifier id = Registries.STATUS_EFFECT.getId(effect);

                if (id != null && id.getNamespace().equals("golditemexpansion")) {
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

    @Inject(
            method = "renderStatusEffectOverlay",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z"
            )
    )
    private void onAddRenderRunnable(DrawContext context, CallbackInfo ci,
                                     @Local StatusEffectSpriteManager manager,
                                     @Local StatusEffectInstance effectInstance,
                                     @Local Sprite sprite) {
        StatusEffect effect = effectInstance.getEffectType();
        Identifier id = Registries.STATUS_EFFECT.getId(effect);

        if (id != null && logCount % LOG_INTERVAL == 0) {
            System.out.println("[GoldItemExpansion][Inject@add] 插入渲染逻辑前检测:");
            System.out.println(" - effect: " + id);
            System.out.println(" - Sprite atlas: " + sprite.getAtlasId());
            System.out.println(" - Sprite contents ID: " + sprite.getContents().getId());
        }
    }
}