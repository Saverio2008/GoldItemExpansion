package org.saverio.golditemexpansion.client;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.saverio.golditemexpansion.client.mixin.accessor.SpriteAtlasHolderAccessor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ReloadListener implements IdentifiableResourceReloadListener {
    private static final Identifier ID = new Identifier("yourmodid", "effect_sprite_reload");

    @Override
    public Identifier getFabricId() {
        return ID;
    }

    @Override
    public CompletableFuture<Void> reload(
            ResourceReloader.Synchronizer synchronizer,
            ResourceManager manager,
            Profiler prepareProfiler,
            Profiler applyProfiler,
            Executor prepareExecutor,
            Executor applyExecutor
    ) {
        // 在 applyExecutor 线程异步执行清缓存操作
        return CompletableFuture.runAsync(() -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client != null && client.getStatusEffectSpriteManager() != null) {
                StatusEffectSpriteManager spriteManager = client.getStatusEffectSpriteManager();
                try (SpriteAtlasTexture atlas = ((SpriteAtlasHolderAccessor) spriteManager).golditemexpansion$getAtlas()) {
                    if (atlas != null) {
                        System.out.println("[Golditemexpansion] 🧹 清除图集缓存！");
                        atlas.clear();
                    }
                }
            }
        }, applyExecutor);
    }
}
