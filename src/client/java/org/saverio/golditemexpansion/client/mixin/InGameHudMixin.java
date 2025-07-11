package org.saverio.golditemexpansion.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.saverio.golditemexpansion.client.mixin.accessor.SpriteAccessor;
import org.saverio.golditemexpansion.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.Objects;

import static com.mojang.text2speech.Narrator.LOGGER;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Unique
    private boolean hasLogged = false;

    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"))
    private void beforeRenderStatusEffectOverlay(DrawContext context, CallbackInfo ci) {
        if (hasLogged) return;

        MinecraftClient client = MinecraftClient.getInstance();
        StatusEffectSpriteManager spriteManager = client.getStatusEffectSpriteManager();

        Collection<StatusEffectInstance> effects = Objects.requireNonNull(client.player).getStatusEffects();

        for (StatusEffectInstance effectInstance : effects) {
            StatusEffect effect = effectInstance.getEffectType();

            if (effect == ModEffects.GOD_POSITIVE_EFFECT || effect == ModEffects.GOD_NEGATIVE_EFFECT) {
                Sprite sprite = spriteManager.getSprite(effect);
                SpriteAccessor accessor = (SpriteAccessor) sprite;

                LOGGER.warn("[InGameHudMixin] Rendering effect: {} | Atlas ID: {} | SpriteContents: {}",
                        effect.getName().getString(), accessor.golditemexpansion$getAtlasId(), accessor.golditemexpansion$getContents());

                hasLogged = true;
                break;
            }
        }
    }
}