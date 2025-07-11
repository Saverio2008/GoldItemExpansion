package org.saverio.golditemexpansion.client.mixin.accessor;

import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteContents;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Sprite.class)
public interface SpriteInvoker {
    @Invoker("<init>")
    static Sprite invokeInit(Identifier atlasId, SpriteContents contents, int atlasWidth, int atlasHeight, int x, int y) {
        throw new AssertionError();
    }
}