package org.saverio.golditemexpansion.util;

import net.minecraft.entity.effect.StatusEffectInstance;
import org.saverio.golditemexpansion.mixin.HiddenEffectInstance;

public class EffectUtils {

    /**
     * 设置一个状态效果图标是否应该在 GUI 中隐藏。
     *
     * @param instance 要修改的状态效果
     * @param hide     是否隐藏图标
     */
    public static void setHideIcon(StatusEffectInstance instance, boolean hide) {
        if (instance instanceof HiddenEffectInstance hidden) {
            hidden.goldItemExpansion$setHideIcon(hide);
        }
    }

    /**
     * 检查状态效果是否被标记为隐藏。
     *
     * @param instance 状态效果实例
     * @return 是否隐藏
     */
    public static boolean shouldHideIcon(StatusEffectInstance instance) {
        return instance instanceof HiddenEffectInstance hidden && hidden.goldItemExpansion$shouldHideIcon();
    }
}