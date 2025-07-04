package org.saverio.golditemexpansion.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class GoldenHeadBlock extends Block {

    public GoldenHeadBlock(Settings settings) {
        super(settings);
    }

    // 放置时调用，或者用 scheduled tick 定时给玩家加效果
    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClient) {
            applyEffectsToNearbyPlayers((ServerWorld) world, pos);
        }
    }

    private void applyEffectsToNearbyPlayers(ServerWorld world, BlockPos pos) {
        // 范围半径，比如 10 格方块范围
        int radius = 10;
        Box area = new Box(pos).expand(radius);

        List<PlayerEntity> players = world.getEntitiesByClass(PlayerEntity.class, area, player -> true);

        int durationTicks = 6000;

        for (PlayerEntity player : players) {
            // 抗性VII (level 6，因为level从0开始计)
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, durationTicks, 6));
            // 抗火 (level 0)
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, durationTicks, 0));
            // 伤害吸收X (level 9)
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, durationTicks, 9));
            // 生命提升X (level 9)
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, durationTicks, 9));
        }
    }
}
