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
import org.saverio.golditemexpansion.effect.ModEffects;
import org.saverio.golditemexpansion.util.GodEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Mixin(AbstractInventoryScreen.class)
public abstract class AbstractInventoryScreenMixin {

    @Inject(method = "drawStatusEffects", at = @At("HEAD"), cancellable = true)
    private void onDrawStatusEffects(DrawContext context, int mouseX, int mouseY, CallbackInfo ci) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;

        Collection<StatusEffectInstance> filtered = player.getStatusEffects().stream()
                .filter(effectInstance -> {
                    StatusEffect effect = effectInstance.getEffectType();
                    StatusEffectInstance godEffect = player.getStatusEffect(ModEffects.GOD_STATUS_EFFECT);
                    if (godEffect != null) {
                        int amplifier = godEffect.getAmplifier();
                        switch (amplifier) {
                            case 0 -> {
                                return !GodEffects.GOD_POSITIVE_EFFECTS.containsKey(effect); // 屏蔽正面效果
                            }
                            case 1, 2 -> {
                                return !GodEffects.GOD_NEGATIVE_EFFECTS.containsKey(effect); // 屏蔽负面效果
                            }
                        }
                    }
                    return true;
                })
                .toList();

        if (filtered.isEmpty()) {
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
        if (filtered.size() > 5) {
            k = 132 / (filtered.size() - 1);
        }

        Iterable<StatusEffectInstance> iterable = Ordering.natural().sortedCopy(filtered);

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
                ScreenAccessor screenAccessor = (ScreenAccessor) screen;
                List<Text> tooltip = List.of(
                        invoker.callGetStatusEffectDescription(hovered),
                        StatusEffectUtil.getDurationText(hovered, 1.0F));
                context.drawTooltip(screenAccessor.getTextRenderer(), tooltip, Optional.empty(), mouseX, mouseY);
            }
        }

        ci.cancel();
    }
}