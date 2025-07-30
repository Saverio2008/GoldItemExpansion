package org.saverio.golditemexpansion.util;

import net.minecraft.world.entity.LivingEntity;

import java.util.Map;
import java.util.WeakHashMap;

public class EffectRemovalStatusTracker {
    private static final Map<LivingEntity, Boolean> removalStatusMap = new WeakHashMap<>();

    public static void setRemoving(LivingEntity entity, boolean removing) {
        if (removing) {
            removalStatusMap.put(entity, true);
        } else {
            removalStatusMap.remove(entity);
        }
    }

    public static boolean isRemoving(LivingEntity entity) {
        return removalStatusMap.containsKey(entity);
    }
}
