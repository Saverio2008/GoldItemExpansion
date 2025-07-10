package org.saverio.golditemexpansion.effect;

import net.minecraft.entity.effect.StatusEffectCategory;

public class GodNegativeStatusEffect extends GodStatusEffect {
    public GodNegativeStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0xAA0000); // 负面效果颜色
    }
}