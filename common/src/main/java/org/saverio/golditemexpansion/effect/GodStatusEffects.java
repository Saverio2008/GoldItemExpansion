package org.saverio.golditemexpansion.effect;

import net.minecraft.world.effect.MobEffectCategory;
import org.saverio.golditemexpansion.util.GodStatusEffect;
import net.minecraft.tags.EntityTypeTags;

public final class GodStatusEffects {
    public static final class Player extends GodStatusEffect {
        public Player() {
            super(
                    MobEffectCategory.NEUTRAL,
                    0xFFFF69B4,
                    3600,
                    entity -> entity instanceof net.minecraft.world.entity.player.Player
            );
        }
    }
    public static final class Undead extends GodStatusEffect {
        public Undead() {
            super(
                    MobEffectCategory.NEUTRAL,
                    0xFFFF69B4,
                    3600,
                    entity -> entity.getType().is(EntityTypeTags.UNDEAD)
            );
        }
    }
    public static final class Arthropod extends GodStatusEffect {
        public Arthropod() {
            super(
                    MobEffectCategory.NEUTRAL,
                    0xFFFF69B4,
                    3600,
                    entity -> entity.getType().is(EntityTypeTags.ARTHROPOD)
            );
        }
    }
}