package org.saverio.golditemexpansion.client.mixin.accessor;

import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteContents;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Sprite.class)
public interface SpriteAccessor {
    @Accessor("atlasId")
    Identifier golditemexpansion$getAtlasId();

    @Accessor("contents")
    SpriteContents golditemexpansion$getContents();
}