package org.saverio.golditemexpansion.client.mixin;

import com.google.common.collect.Ordering;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.saverio.golditemexpansion.client.mixin.accessor.HandledScreenAccessor;
import org.saverio.golditemexpansion.client.mixin.accessor.ScreenAccessor;
import org.saverio.golditemexpansion.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

import static org.saverio.golditemexpansion.effect.GodNegativeStatusEffect.GOD_NEGATIVE_EFFECTS;
import static org.saverio.golditemexpansion.effect.GodPositiveStatusEffect.GOD_POSITIVE_EFFECTS;

@Mixin(AbstractInventoryScreen.class)
public abstract class AbstractInventoryScreenMixin {

    @Unique
    private static final Map<StatusEffect, Long> godEffectExpireTick = new HashMap<>();

    @Unique
    private static final int GOD_EFFECT_HIDE_TICKS = 5;

    @Inject(method = "drawStatusEffects", at = @At("HEAD"), cancellable = true)
    private void onDrawStatusEffects(DrawContext context, int mouseX, int mouseY, CallbackInfo ci) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null || player.getWorld() == null) return;

        long currentTick = player.getWorld().getTime();
        List<StatusEffectInstance> effects = new ArrayList<>(player.getStatusEffects());

        // 永久屏蔽 GOD_STATUS_EFFECT
        effects.removeIf(e -> e.getEffectType() == ModEffects.GOD_STATUS_EFFECT);

        boolean hasGodPositive = effects.stream()
                .anyMatch(e -> e.getEffectType() == ModEffects.GOD_POSITIVE_EFFECT);
        boolean hasGodNegative = effects.stream()
                .anyMatch(e -> e.getEffectType() == ModEffects.GOD_NEGATIVE_EFFECT);

        boolean hasPositiveChildren = effects.stream()
                .anyMatch(e -> GOD_POSITIVE_EFFECTS.containsKey(e.getEffectType()));
        boolean hasNegativeChildren = effects.stream()
                .anyMatch(e -> GOD_NEGATIVE_EFFECTS.containsKey(e.getEffectType()));

        if (hasGodPositive) {
            godEffectExpireTick.put(ModEffects.GOD_POSITIVE_EFFECT, currentTick);
            effects.removeIf(e ->
                    e.getEffectType() != ModEffects.GOD_POSITIVE_EFFECT &&
                            GOD_POSITIVE_EFFECTS.containsKey(e.getEffectType()));
        } else if (hasPositiveChildren) {
            long expire = godEffectExpireTick.getOrDefault(ModEffects.GOD_POSITIVE_EFFECT, -100L);
            if (currentTick - expire <= GOD_EFFECT_HIDE_TICKS) {
                effects.removeIf(e -> GOD_POSITIVE_EFFECTS.containsKey(e.getEffectType()));
            } else {
                godEffectExpireTick.remove(ModEffects.GOD_POSITIVE_EFFECT);
            }
        }

        if (hasGodNegative) {
            godEffectExpireTick.put(ModEffects.GOD_NEGATIVE_EFFECT, currentTick);
            effects.removeIf(e ->
                    e.getEffectType() != ModEffects.GOD_NEGATIVE_EFFECT &&
                            GOD_NEGATIVE_EFFECTS.containsKey(e.getEffectType()));
        } else if (hasNegativeChildren) {
            long expire = godEffectExpireTick.getOrDefault(ModEffects.GOD_NEGATIVE_EFFECT, -100L);
            if (currentTick - expire <= GOD_EFFECT_HIDE_TICKS) {
                effects.removeIf(e -> GOD_NEGATIVE_EFFECTS.containsKey(e.getEffectType()));
            } else {
                godEffectExpireTick.remove(ModEffects.GOD_NEGATIVE_EFFECT);
            }
        }

        // 无需绘制任何状态效果
        if (effects.isEmpty()) {
            ci.cancel();
            return;
        }

        // 以下为状态效果绘制逻辑（与原版一致）
        AbstractInventoryScreen<?> screen = (AbstractInventoryScreen<?>) (Object) this;
        AbstractInventoryScreenInvoker invoker = (AbstractInventoryScreenInvoker) screen;
        HandledScreenAccessor handled = (HandledScreenAccessor) screen;
        ScreenAccessor screenAccess = (ScreenAccessor) screen;

        int i = handled.getX() + handled.getBackgroundWidth() + 2;
        int j = screenAccess.getWidth() - i;
        if (j < 32) {
            ci.cancel();
            return;
        }

        boolean wide = j >= 120;
        int k = 33;
        if (effects.size() > 5) {
            k = 132 / (effects.size() - 1);
        }

        Iterable<StatusEffectInstance> iterable = Ordering.natural().sortedCopy(effects);
        invoker.callDrawStatusEffectBackgrounds(context, i, k, iterable, wide);
        invoker.callDrawStatusEffectSprites(context, i, k, iterable, wide);

        if (wide) {
            invoker.callDrawStatusEffectDescriptions(context, i, k, iterable);
        } else if (mouseX >= i && mouseX <= i + 33) {
            int l = handled.getY();
            StatusEffectInstance hovered = null;
            for (StatusEffectInstance instance : iterable) {
                if (mouseY >= l && mouseY <= l + k) {
                    hovered = instance;
                }
                l += k;
            }
            if (hovered != null) {
                List<Text> tooltip = List.of(
                        invoker.callGetStatusEffectDescription(hovered),
                        StatusEffectUtil.getDurationText(hovered, 1.0F));
                context.drawTooltip(screenAccess.getTextRenderer(), tooltip, Optional.empty(), mouseX, mouseY);
            }
        }

        ci.cancel();
    }
}