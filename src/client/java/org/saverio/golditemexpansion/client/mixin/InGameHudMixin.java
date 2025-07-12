package org.saverio.golditemexpansion.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.entity.effect.StatusEffect;
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
    private static final int MAX_LOGS = 50;
    @Unique
    private static int logCount = 0;

    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"))
    private void beforeRenderStatusEffectOverlay(DrawContext context, CallbackInfo ci) {
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
                String contentInfo = sprite.getContents() == null ? "null"
                        : sprite.getContents().getWidth() + "x" + sprite.getContents().getHeight();

                System.out.println("[GoldItemExpansion][DEBUG] Effect: " + id +
                        ", Sprite Atlas: " + sprite.getAtlasId() +
                        ", Sprite Contents Hash: " + (sprite.getContents() == null ? "null" : sprite.getContents().hashCode()) +
                        ", Size: " + contentInfo +
                        ", UV: minU=" + sprite.getMinU() +
                        ", maxU=" + sprite.getMaxU() +
                        ", minV=" + sprite.getMinV() +
                        ", maxV=" + sprite.getMaxV());

                logCount++;
            }
        });
    }
}