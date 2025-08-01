package org.saverio.golditemexpansion.util;

public class GodEffectRemoveSkipManager {
    private static final ThreadLocal<Boolean> skipFlag = ThreadLocal.withInitial(() -> Boolean.FALSE);

    public static void setSkip(boolean skip) {
        skipFlag.set(skip);
    }

    public static boolean shouldSkip() {
        return skipFlag.get();
    }

    public static void clear() {
        skipFlag.remove();
    }
}
