package org.saverio.golditemexpansion.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class GolditemexpansionClient implements ClientModInitializer {

    private static final Identifier RELOAD_LISTENER_ID = new Identifier("golditemexpansion", "mob_effects_injector");

    @Override
    public void onInitializeClient() {
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new IdentifiableResourceReloadListener() {
            @Override
            public @NotNull Identifier getFabricId() {
                return RELOAD_LISTENER_ID;
            }
            @Override
            public CompletableFuture<Void> reload(
                    Synchronizer synchronizer,
                    @NotNull ResourceManager manager,
                    Profiler prepareProfiler,
                    Profiler applyProfiler,
                    Executor prepareExecutor,
                    Executor applyExecutor) {
                System.out.println("[Golditemexpansion] Resource reload triggered.");
                CompletableFuture<Void> prepare = CompletableFuture.runAsync(() ->
                        System.out.println("[Golditemexpansion] Preparing resource injection..."), prepareExecutor);
                return prepare.thenCompose(synchronizer::whenPrepared).thenRunAsync(() ->
                        System.out.println("[Golditemexpansion] Resource injection applied."), applyExecutor);
            }
        });
    }
}