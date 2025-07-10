package org.saverio.golditemexpansion.client.mixin;

import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
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

    @SuppressWarnings("resource")
    @Inject(method = "getSprite", at = @At("HEAD"), cancellable = true)
    private void injectCustomSprite(StatusEffect effect, CallbackInfoReturnable<Sprite> cir) {
        Identifier id = Registries.STATUS_EFFECT.getId(effect);
        if (id != null && id.getNamespace().equals("golditemexpansion")) {
            String path = id.getPath();
            if (path.equals("god_positive_status_effect") || path.equals("god_negative_status_effect")) {
                SpriteAtlasTexture atlas = ((SpriteAtlasHolderAccessor) this).golditemexpansion$getAtlas();
                Identifier spriteId = new Identifier("golditemexpansion", "gui/status_effect/" + path);
                Sprite sprite = atlas.getSprite(spriteId);
                if (sprite != null) {
                    cir.setReturnValue(sprite);
                }
            }
        }
    }
}