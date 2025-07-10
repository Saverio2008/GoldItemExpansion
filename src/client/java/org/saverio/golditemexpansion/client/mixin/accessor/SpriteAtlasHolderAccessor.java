package org.saverio.golditemexpansion.client.mixin.accessor;

import net.minecraft.client.texture.SpriteAtlasHolder;
import net.minecraft.client.texture.SpriteAtlasTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SpriteAtlasHolder.class)
public interface SpriteAtlasHolderAccessor {
    @Accessor("atlas")
    SpriteAtlasTexture golditemexpansion$getAtlas();
}
