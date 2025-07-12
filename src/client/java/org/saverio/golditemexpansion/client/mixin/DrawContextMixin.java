package org.saverio.golditemexpansion.client.mixin;

import net.minecraft.client.texture.Sprite;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.atomic.AtomicInteger;

@Mixin(DrawContext.class)
public class DrawContextMixin {

    @Unique
    private static final int MAX_LOGS = 10;
    @Unique
    private static final AtomicInteger logCount = new AtomicInteger(0);

    // 你注册贴图时使用的 ID
    @Unique
    private static final Identifier GOD_POSITIVE_SPRITE_ID = new Identifier("golditemexpansion", "god_positive_status_effect");
    @Unique
    private static final Identifier GOD_NEGATIVE_SPRITE_ID = new Identifier("golditemexpansion", "god_negative_status_effect");

    @Inject(method = "drawSprite(IIIIILnet/minecraft/client/texture/Sprite;)V", at = @At("HEAD"))
    private void onDrawSprite(int x, int y, int z, int width, int height, Sprite sprite, CallbackInfo ci) {
        if (logCount.get() >= MAX_LOGS) return;
        if (sprite == null || sprite.getContents() == null) return;

        Identifier spriteId = sprite.getContents().getId(); // ✅ 获取具体的贴图ID golditemexpansion:god_positive_status_effect

        // 只检查你的贴图
        if (!spriteId.equals(GOD_POSITIVE_SPRITE_ID) && !spriteId.equals(GOD_NEGATIVE_SPRITE_ID)) {
            return;
        }

        int currentCount = logCount.incrementAndGet();
        System.out.println("[GoldItemExpansion][DrawContext] drawSprite called for custom sprite:");
        System.out.println(" - Pos: (" + x + "," + y + "), Size: " + width + "x" + height);
        System.out.println(" - Atlas: " + sprite.getAtlasId());
        System.out.println(" - ID: " + spriteId);
        System.out.println(" - UV: minU=" + sprite.getMinU() + ", maxU=" + sprite.getMaxU() +
                ", minV=" + sprite.getMinV() + ", maxV=" + sprite.getMaxV());
        System.out.println(" - Contents size: " + sprite.getContents().getWidth() + "x" + sprite.getContents().getHeight());
        System.out.println(" - Log count: " + currentCount);
    }
}