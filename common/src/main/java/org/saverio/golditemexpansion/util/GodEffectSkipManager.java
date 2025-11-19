package org.saverio.golditemexpansion.util;

import net.minecraft.world.entity.LivingEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class GodEffectSkipManager {
    private static final Map<LivingEntity, Boolean> skipFlags = new ConcurrentHashMap<>();
    private GodEffectSkipManager() {}
    public static void setSkip(LivingEntity entity, boolean skip) {
        if (skip) {
            skipFlags.put(entity, true);
        } else {
            skipFlags.remove(entity);
        }
    }
    public static boolean shouldSkip(LivingEntity entity) {
        return skipFlags.containsKey(entity);
    }
}
