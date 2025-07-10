package org.saverio.golditemexpansion.client.mixin;

import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.saverio.golditemexpansion.client.mixin.accessor.SpriteAtlasHolderAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffectSpriteManager.class)
public abstract class StatusEffectSpriteManagerMixin {
    @Inject(method = "getSprite", at = @At("HEAD"), cancellable = true)
    private void injectCustomSprite(StatusEffect effect, CallbackInfoReturnable<Sprite> cir) {
        Identifier id = Registries.STATUS_EFFECT.getId(effect);
        if (id != null && id.getNamespace().equals("golditemexpansion")) {
            Identifier spriteId = switch (id.getPath()) {
                case "god_positive_status_effect" -> new Identifier("golditemexpansion", "gui/status_effect/god_positive_status_effect");
                case "god_negative_status_effect" -> new Identifier("golditemexpansion", "gui/status_effect/god_negative_status_effect");
                default -> new Identifier("golditemexpansion", "gui/status_effect/god_default");
            };
            Sprite sprite = ((SpriteAtlasHolderAccessor) this).golditemexpansion$invokeGetSprite(spriteId);
            if (sprite != null && !sprite.getContents().getId().getPath().contains("missing")) {
                cir.setReturnValue(sprite);
            }
        }
    }
}