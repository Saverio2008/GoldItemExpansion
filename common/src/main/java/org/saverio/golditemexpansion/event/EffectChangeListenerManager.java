package org.saverio.golditemexpansion.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EffectChangeListenerManager {

    private static final List<EffectChangeListener> LISTENERS = new CopyOnWriteArrayList<>();

    public static void register(EffectChangeListener listener) {
        LISTENERS.add(listener);
    }

    public static void unregister(EffectChangeListener listener) {
        LISTENERS.remove(listener);
    }

    public static void onEffectAdded(LivingEntity entity, MobEffectInstance effect, @Nullable Entity source) {
        for (EffectChangeListener listener : LISTENERS) {
            listener.onEffectAdded(entity, effect, source);
        }
    }

    public static void onEffectRemoved(LivingEntity entity, MobEffectInstance effect) {
        for (EffectChangeListener listener : LISTENERS) {
            listener.onEffectRemoved(entity, effect);
        }
    }
}
