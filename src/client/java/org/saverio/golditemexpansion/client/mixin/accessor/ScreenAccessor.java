package org.saverio.golditemexpansion.client.mixin.accessor;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Screen.class)
public interface ScreenAccessor {
    @Accessor("width")
    int getWidth();

    @Accessor("textRenderer")
    TextRenderer getTextRenderer();
}