package org.saverio.golditemexpansion.potion;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;

import static org.saverio.golditemexpansion.effect.ModEffectInstances.GOD_STATUS_EFFECT;

public final class ModPotionInstances {
    public static final Potion GOD_POTION = new Potion(new MobEffectInstance(GOD_STATUS_EFFECT, 3600, 0, false, true, false));
    public static final Potion UNDEAD_GOD_POTION = new Potion(new MobEffectInstance(GOD_STATUS_EFFECT, 3600, 1, false, true, false));
    public static final Potion ARTHROPOD_GOD_POTION = new Potion(new MobEffectInstance(GOD_STATUS_EFFECT, 3600, 2, false, true, false));
    public static final Potion HEALING_V = new Potion(new MobEffectInstance(MobEffects.HEAL, 1, 4));
    public static final Potion GODLY_HEALING = new Potion(new MobEffectInstance(MobEffects.HEAL, 1, 14));
}
