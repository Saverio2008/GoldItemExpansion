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
            String path = id.getPath();
            if ("god_positive_status_effect".equals(path) || "god_negative_status_effect".equals(path)) {
                Identifier spriteId = new Identifier("golditemexpansion", "gui/status_effect/" + path);
                Sprite sprite = ((SpriteAtlasHolderAccessor) this).golditemexpansion$invokeGetSprite(spriteId);
                System.out.println("[Mixin] Injecting sprite for " + path + ": " + sprite);
                cir.setReturnValue(sprite);
                cir.cancel();
            }
        }
    }
}