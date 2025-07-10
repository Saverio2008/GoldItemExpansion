package org.saverio.golditemexpansion.client.mixin.accessor;

import net.minecraft.client.texture.Sprite;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import net.minecraft.client.texture.SpriteAtlasHolder;

@Mixin(SpriteAtlasHolder.class)
public interface SpriteAtlasHolderAccessor {
    @Invoker("getSprite")
    Sprite golditemexpansion$invokeGetSprite(Identifier id);
}
