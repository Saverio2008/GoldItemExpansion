package org.saverio.golditemexpansion.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
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

    private static final Identifier RELOAD_ID = new Identifier("golditemexpansion", "mob_effects_reload");

    // 标记资源是否加载完成（只是辅助）
    private volatile boolean resourcesReloaded = false;
    // 标记图标是否已刷新
    private boolean iconsRefreshed = false;

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
                    Executor applyExecutor) {

                System.out.println("[Golditemexpansion] 🔄 资源重载开始");

                CompletableFuture<Void> prepareFuture = CompletableFuture.runAsync(() ->
                        System.out.println("[Golditemexpansion] 🔄 异步预加载..."), prepareExecutor);

                return prepareFuture.thenRunAsync(() -> {
                    System.out.println("[Golditemexpansion] 🔄 资源重载完成，标记已完成");
                    resourcesReloaded = true;
                }, applyExecutor);
            }
        });

        // 进入世界时注册图标（可选）
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            // 进入世界后，图标也可刷新一次（防止多人切换）
            client.execute(() -> {
                refreshIcons(client);
                System.out.println("[Golditemexpansion] ✅ 进入世界时自定义药水图标刷新完成");
            });
        });

        // 监听客户端Tick，等待资源加载完且处于主菜单时刷新图标，只刷新一次
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (resourcesReloaded && !iconsRefreshed) {
                // 判断当前界面是不是主菜单或无界面（主界面是null或MainMenuScreen）
                if (client.currentScreen == null /* || client.currentScreen instanceof MainMenuScreen */) {
                    iconsRefreshed = true;
                    client.execute(() -> {
                        refreshIcons(client);
                        System.out.println("[Golditemexpansion] ✅ 游戏主界面自定义药水图标刷新完成");
                    });
                }
            }
        });
    }

    private void refreshIcons(MinecraftClient client) {
        StatusEffectSpriteManager spriteManager = client.getStatusEffectSpriteManager();
        if (spriteManager != null) {
            spriteManager.getSprite(ModEffects.GOD_POSITIVE_EFFECT);
            spriteManager.getSprite(ModEffects.GOD_NEGATIVE_EFFECT);
        }
    }
}