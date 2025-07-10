package org.saverio.golditemexpansion.util;

import net.minecraft.entity.LivingEntity;

public class LivingEntityUtils {
    public static boolean isGodNegativeApplied(LivingEntity entity) {
        if (entity instanceof LivingEntityAccessor accessor) {
            return accessor.goldItemExpansion$isGodNegativeApplied();
        }
        return false; // 或者默认值
    }

    public static void setGodNegativeApplied(LivingEntity entity, boolean applied) {
        if (entity instanceof LivingEntityAccessor accessor) {
            accessor.goldItemExpansion$setGodNegativeApplied(applied);
        }
    }

    public static boolean isGodPositiveApplied(LivingEntity entity) {
        if (entity instanceof LivingEntityAccessor accessor) {
            return accessor.goldItemExpansion$isGodPositiveApplied();
        }
        return false;
    }

    public static void setGodPositiveApplied(LivingEntity entity, boolean applied) {
        if (entity instanceof LivingEntityAccessor accessor) {
            accessor.goldItemExpansion$setGodPositiveApplied(applied);
        }
    }

    public static boolean isGodStatusApplied(LivingEntity entity) {
        if (entity instanceof LivingEntityAccessor accessor) {
            return accessor.goldItemExpansion$isGodStatusApplied();
        }
        return false;
    }

    public static void setGodStatusApplied(LivingEntity entity, boolean applied) {
        if (entity instanceof LivingEntityAccessor accessor) {
            accessor.goldItemExpansion$setGodStatusApplied(applied);
        }
    }
}