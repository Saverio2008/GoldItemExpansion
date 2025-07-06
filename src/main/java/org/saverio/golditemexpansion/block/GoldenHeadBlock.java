package org.saverio.golditemexpansion.block;

import net.minecraft.block.SkullBlock;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.List;

public class GoldenHeadBlock extends SkullBlock {
    private static final int TICKS_BEFORE_REMOVE = 40;
    private static final int EFFECT_RADIUS = 10;

    public GoldenHeadBlock(Settings settings) {
        // 用一个自定义的 SkullType，贴图绑定时会用它的注册名
        super(SkullBlock.Type.SKELETON, settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, net.minecraft.block.BlockState state, net.minecraft.entity.LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClient) {
            ServerWorld serverWorld = (ServerWorld) world;
            applyEffectsToNearbyPlayers(serverWorld, pos);
            serverWorld.scheduleBlockTick(pos, this, TICKS_BEFORE_REMOVE);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void scheduledTick(net.minecraft.block.BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, net.minecraft.block.Blocks.AIR.getDefaultState());
    }

    private void applyEffectsToNearbyPlayers(ServerWorld world, BlockPos pos) {
        Box area = new Box(
                pos.getX() - EFFECT_RADIUS, pos.getY() - EFFECT_RADIUS, pos.getZ() - EFFECT_RADIUS,
                pos.getX() + EFFECT_RADIUS, pos.getY() + EFFECT_RADIUS, pos.getZ() + EFFECT_RADIUS
        );

        List<PlayerEntity> players = world.getEntitiesByClass(PlayerEntity.class, area, player -> true);
        int durationTicks = 6000;

        for (PlayerEntity player : players) {
            applyOrExtendEffect(player, new StatusEffectInstance(StatusEffects.RESISTANCE, durationTicks, 6));
            applyOrExtendEffect(player, new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, durationTicks, 0));
            applyOrExtendEffect(player, new StatusEffectInstance(StatusEffects.ABSORPTION, durationTicks, 9));
            applyOrExtendEffect(player, new StatusEffectInstance(StatusEffects.HEALTH_BOOST, durationTicks, 9));
        }
    }

    private void applyOrExtendEffect(PlayerEntity player, StatusEffectInstance newEffect) {
        StatusEffectInstance current = player.getStatusEffect(newEffect.getEffectType());
        if (current != null) {
            int extendedDuration = current.getDuration() + newEffect.getDuration();
            int amplifier = Math.max(current.getAmplifier(), newEffect.getAmplifier());
            player.addStatusEffect(new StatusEffectInstance(
                    newEffect.getEffectType(),
                    extendedDuration,
                    amplifier,
                    newEffect.isAmbient(),
                    newEffect.shouldShowParticles(),
                    newEffect.shouldShowIcon()
            ));
        } else {
            player.addStatusEffect(newEffect);
        }
    }
}