package org.saverio.golditemexpansion.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasHolder;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.saverio.golditemexpansion.client.mixin.accessor.SpriteAtlasHolderAccessor;
import org.saverio.golditemexpansion.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(SpriteAtlasHolder.class)
public abstract class SpriteAtlasHolderMixin {

    @SuppressWarnings("resource")
    @Inject(method = "reload", at = @At("TAIL"))
    private void onReload(ResourceReloader.Synchronizer synchronizer,
                          ResourceManager manager,
                          Profiler prepareProfiler,
                          Profiler applyProfiler,
                          Executor prepareExecutor,
                          Executor applyExecutor,
                          CallbackInfoReturnable<CompletableFuture<Void>> cir) {

        SpriteAtlasHolder holder = (SpriteAtlasHolder) (Object) this;

        SpriteAtlasTexture atlas = ((SpriteAtlasHolderAccessor) holder).golditemexpansion$getAtlas();
        System.out.println("[GoldItemExpansion] 当前reload的图集ID: " + atlas.getId());
        if (!atlas.getId().equals(new Identifier("minecraft", "textures/atlas/mob_effects.png"))) {
            return;
        }

        cir.getReturnValue().thenRunAsync(() -> {
            Sprite positive = atlas.getSprite(new Identifier("golditemexpansion", "god_positive_status_effect"));
            Sprite negative = atlas.getSprite(new Identifier("golditemexpansion", "god_negative_status_effect"));

            // 测试是否加载成功
            if (positive.getContents() == null || negative.getContents() == null) {
                System.err.println("[GoldItemExpansion] ❌ 自定义状态图标未加载成功！");
                return;
            }

            // 触发预加载
            MinecraftClient.getInstance().getStatusEffectSpriteManager().getSprite(ModEffects.GOD_POSITIVE_EFFECT);
            MinecraftClient.getInstance().getStatusEffectSpriteManager().getSprite(ModEffects.GOD_NEGATIVE_EFFECT);

            System.out.println("[GoldItemExpansion] ✅ 自定义状态图标成功 stitch 且可用！");
        }, MinecraftClient.getInstance());
    }
}