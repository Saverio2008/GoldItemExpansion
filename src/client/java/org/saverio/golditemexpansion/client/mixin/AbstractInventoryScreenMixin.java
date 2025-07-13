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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.saverio.golditemexpansion.effect.GodNegativeStatusEffect.GOD_NEGATIVE_EFFECTS;
import static org.saverio.golditemexpansion.effect.GodPositiveStatusEffect.GOD_POSITIVE_EFFECTS;

@Mixin(AbstractInventoryScreen.class)
public abstract class AbstractInventoryScreenMixin {

    @SuppressWarnings("ReassignedVariable")
    @Inject(method = "drawStatusEffects", at = @At("HEAD"), cancellable = true)
    private void onDrawStatusEffects(DrawContext context, int mouseX, int mouseY, CallbackInfo ci) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;

        List<StatusEffectInstance> effects = new ArrayList<>(player.getStatusEffects());

        effects.removeIf(e -> e.getEffectType() == ModEffects.GOD_STATUS_EFFECT);

        boolean hasPositive = effects.stream()
                .anyMatch(e -> e.getEffectType() == ModEffects.GOD_POSITIVE_EFFECT);
        if (hasPositive) {
            effects.removeIf(e ->
                    e.getEffectType() != ModEffects.GOD_POSITIVE_EFFECT &&
                            GOD_POSITIVE_EFFECTS.containsKey(e.getEffectType()));
        }

        boolean hasNegative = effects.stream()
                .anyMatch(e -> e.getEffectType() == ModEffects.GOD_NEGATIVE_EFFECT);
        if (hasNegative) {
            effects.removeIf(e ->
                    e.getEffectType() != ModEffects.GOD_NEGATIVE_EFFECT &&
                            GOD_NEGATIVE_EFFECTS.containsKey(e.getEffectType()));
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
}