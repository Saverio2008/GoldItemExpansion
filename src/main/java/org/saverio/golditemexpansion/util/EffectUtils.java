package org.saverio.golditemexpansion.util;

import net.minecraft.entity.effect.StatusEffectInstance;

public class EffectUtils {

    public static void setHideIcon(StatusEffectInstance instance, boolean hide) {
        if (instance instanceof HiddenEffectInstance hidden) {
            hidden.goldItemExpansion$setHideIcon(hide);
        }
    }

    private static boolean printedStack = false;

    public static boolean shouldHideIcon(StatusEffectInstance instance) {
        boolean hidden = instance instanceof HiddenEffectInstance h && h.goldItemExpansion$shouldHideIcon();
        if (hidden) {
            System.out.println("[GoldItemExpansion] Hiding icon for: " + instance.getEffectType().getTranslationKey());
            if (!printedStack) {
                printedStack = true;
                System.out.println("[GoldItemExpansion] First stack trace for hidden icon:");
                Thread.dumpStack();
            }
        }
        return hidden;
    }
}