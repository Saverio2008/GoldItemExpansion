package org.saverio.golditemexpansion.client.mixin.accessor;

import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.SpriteContents;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(SpriteAtlasTexture.class)
public interface SpriteAtlasTextureAccessor {
    @Accessor("sprites")
    Map<Identifier, Sprite> getGolditemexpansion$Sprites();

    @Accessor("spritesToLoad")
    List<SpriteContents> getGolditemexpansion$SpritesToLoad();
}
