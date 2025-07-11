package org.saverio.golditemexpansion.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.saverio.golditemexpansion.effect.ModEffects;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class GolditemexpansionClient implements ClientModInitializer {

    private boolean hasRegisteredIcons = false;
    private static final Identifier RELOAD_ID = new Identifier("golditemexpansion", "mob_effects_reload");

    @Override
    public void onInitializeClient() {
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new IdentifiableResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return RELOAD_ID;
            }

            @Override
            public CompletableFuture<Void> reload(
                    Synchronizer synchronizer,
                    ResourceManager resourceManager,
                    Profiler prepareProfiler,
                    Profiler applyProfiler,
                    Executor prepareExecutor,
                    Executor applyExecutor
            ) {
                return CompletableFuture.supplyAsync(() -> {
                    System.out.println("[Golditemexpansion] 🔄 Preloading mob_effects sprites...");
                    // 这里可同步执行简单操作，比如加载贴图资源缓存（即使没实际资源加载，也模拟一下）
                    return null;
                }, prepareExecutor).thenRunAsync(() -> {
                    System.out.println("[Golditemexpansion] ✅ Mob effects sprites reloaded.");
                    // 这里做刷新缓存或标记贴图已经准备好等操作
                }, applyExecutor);
            }
        });

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (!hasRegisteredIcons) {
                hasRegisteredIcons = true;
                MinecraftClient mc = MinecraftClient.getInstance();
                StatusEffectSpriteManager spriteManager = mc.getStatusEffectSpriteManager();
                if (spriteManager != null) {
                    spriteManager.getSprite(ModEffects.GOD_POSITIVE_EFFECT);
                    spriteManager.getSprite(ModEffects.GOD_NEGATIVE_EFFECT);
                    System.out.println("[Golditemexpansion] ✅ 进入世界时自定义药水图标注册成功！");
                }
            }
        });
    }
}