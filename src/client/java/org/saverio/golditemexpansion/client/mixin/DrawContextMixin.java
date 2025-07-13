package org.saverio.golditemexpansion.client.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrawContext.class)
public class DrawContextMixin {

    @Unique
    private static final Identifier TARGET_ATLAS = new Identifier("minecraft", "textures/atlas/mob_effects.png");

    @Unique
    private static int callCount = 0;

    @Inject(method = "drawTexturedQuad*", at = @At("HEAD"))
    private void onDrawTexturedQuad(
            Identifier atlas,
            int x0, int x1,
            int y0, int y1,
            int z,
            float minU, float maxU,
            float minV, float maxV,
            CallbackInfo ci
    ) {
        if (!atlas.equals(TARGET_ATLAS)) return;

        callCount++;
        if (callCount % 100 == 0) {
            System.out.printf(
                    "[GoldItemExpansion] drawTexturedQuad #%d for %s at [%d,%d]-[%d,%d] UV: [%.3f,%.3f]-[%.3f,%.3f]\n",
                    callCount, atlas, x0, y0, x1, y1, minU, maxU, minV, maxV
            );
        }
    }
}