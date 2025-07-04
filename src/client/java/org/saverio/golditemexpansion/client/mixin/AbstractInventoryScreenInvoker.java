package org.saverio.golditemexpansion.client.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractInventoryScreen.class)
public interface AbstractInventoryScreenInvoker {

    @Invoker("drawStatusEffectBackgrounds")
    void callDrawStatusEffectBackgrounds(DrawContext context, int x, int height, Iterable<StatusEffectInstance> statusEffects, boolean wide);

    @Invoker("drawStatusEffectSprites")
    void callDrawStatusEffectSprites(DrawContext context, int x, int height, Iterable<StatusEffectInstance> statusEffects, boolean wide);

    @Invoker("drawStatusEffectDescriptions")
    void callDrawStatusEffectDescriptions(DrawContext context, int x, int height, Iterable<StatusEffectInstance> statusEffects);

    @Invoker("getStatusEffectDescription")
    net.minecraft.text.Text callGetStatusEffectDescription(StatusEffectInstance statusEffectInstance);
}