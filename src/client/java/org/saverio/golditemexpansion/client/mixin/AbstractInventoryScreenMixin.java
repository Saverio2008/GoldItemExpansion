package org.saverio.golditemexpansion.client.mixin;

import com.google.common.collect.Ordering;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
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
    private static final Map<StatusEffectInstance, Long> godEffectExpireTick = new WeakHashMap<>();

    @Unique
    private static final int GOD_EFFECT_HIDE_TICKS = 5;

    @Inject(method = "drawStatusEffects", at = @At("HEAD"), cancellable = true)
    private void onDrawStatusEffects(DrawContext context, int mouseX, int mouseY, CallbackInfo ci) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null || player.getWorld() == null) return;

        long currentTick = player.getWorld().getTime();
        List<StatusEffectInstance> effects = new ArrayList<>(player.getStatusEffects());

        effects.removeIf(e -> e.getEffectType() == ModEffects.GOD_STATUS_EFFECT);

        Optional<StatusEffectInstance> godPositive = effects.stream()
                .filter(e -> e.getEffectType() == ModEffects.GOD_POSITIVE_EFFECT)
                .findFirst();
        Optional<StatusEffectInstance> godNegative = effects.stream()
                .filter(e -> e.getEffectType() == ModEffects.GOD_NEGATIVE_EFFECT)
                .findFirst();

        if (godPositive.isPresent()) {
            effects.removeIf(e ->
                    e.getEffectType() != ModEffects.GOD_POSITIVE_EFFECT &&
                            GOD_POSITIVE_EFFECTS.containsKey(e.getEffectType()));
            godEffectExpireTick.put(godPositive.get(), currentTick);
        } else {
            effects.removeIf(e ->
                    GOD_POSITIVE_EFFECTS.containsKey(e.getEffectType()) &&
                            shouldHideAfterExpire(e, currentTick));
        }

        if (godNegative.isPresent()) {
            effects.removeIf(e ->
                    e.getEffectType() != ModEffects.GOD_NEGATIVE_EFFECT &&
                            GOD_NEGATIVE_EFFECTS.containsKey(e.getEffectType()));
            godEffectExpireTick.put(godNegative.get(), currentTick);
        } else {
            effects.removeIf(e ->
                    GOD_NEGATIVE_EFFECTS.containsKey(e.getEffectType()) &&
                            shouldHideAfterExpire(e, currentTick));
        }

        if (effects.isEmpty()) {
            ci.cancel();
            return;
        }

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

    @Unique
    private boolean shouldHideAfterExpire(StatusEffectInstance e, long currentTick) {
        Long tick = godEffectExpireTick.get(e);
        return tick != null && currentTick - tick <= GOD_EFFECT_HIDE_TICKS;
    }
}