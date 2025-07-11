package org.saverio.golditemexpansion.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
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
    private volatile boolean needsSpriteRefresh = false;
    private static final Identifier RELOAD_ID = new Identifier("golditemexpansion", "mob_effects_reload");

    @Override
    public void onInitializeClient() {

        // 监听客户端Tick，在主线程安全刷新图标
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (needsSpriteRefresh) {
                needsSpriteRefresh = false;
                StatusEffectSpriteManager spriteManager = client.getStatusEffectSpriteManager();
                if (spriteManager != null) {
                    spriteManager.getSprite(ModEffects.GOD_POSITIVE_EFFECT);
                    spriteManager.getSprite(ModEffects.GOD_NEGATIVE_EFFECT);
                    System.out.println("[Golditemexpansion] ✅ Tick事件刷新自定义药水图标完成");
                }
            }
        });

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
                    Executor applyExecutor) {

                System.out.println("[Golditemexpansion] 🔄 资源重载开始");

                CompletableFuture<Void> prepareFuture = CompletableFuture.runAsync(() ->
                        System.out.println("[Golditemexpansion] 🔄 异步预加载..."), prepareExecutor);

                return prepareFuture.thenRunAsync(() -> {
                    System.out.println("[Golditemexpansion] 🔄 资源重载应用阶段，标记需要刷新图标");
                    needsSpriteRefresh = true;
                }, applyExecutor);
            }
        });

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (!hasRegisteredIcons) {
                hasRegisteredIcons = true;
                client.execute(() -> {
                    StatusEffectSpriteManager spriteManager = client.getStatusEffectSpriteManager();
                    if (spriteManager != null) {
                        spriteManager.getSprite(ModEffects.GOD_POSITIVE_EFFECT);
                        spriteManager.getSprite(ModEffects.GOD_NEGATIVE_EFFECT);
                        System.out.println("[Golditemexpansion] ✅ 进入世界时自定义药水图标注册成功！");
                    }
                });
            }
        });
    }
}