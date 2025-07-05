package org.saverio.golditemexpansion.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.List;

public class GoldenHeadBlock extends WallMountedBlock {
    public static final EnumProperty<WallMountLocation> FACE = WallMountedBlock.FACE;
    public static final DirectionProperty FACING = WallMountedBlock.FACING;
    private static final int TICKS_BEFORE_REMOVE = 40;
    public GoldenHeadBlock(Settings settings) {
        super(settings);
        setDefaultState(this.getStateManager().getDefaultState()
                .with(FACE, WallMountLocation.WALL)
                .with(FACING, Direction.NORTH));
    }
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getPlacementDirections()[0];
        WallMountLocation face = direction == Direction.UP ? WallMountLocation.CEILING
                : direction == Direction.DOWN ? WallMountLocation.FLOOR
                : WallMountLocation.WALL;
        Direction facing = face == WallMountLocation.WALL
                ? ctx.getHorizontalPlayerFacing().getOpposite()
                : ctx.getHorizontalPlayerFacing();
        return this.getDefaultState().with(FACE, face).with(FACING, facing);
    }
    @Override
    protected void appendProperties(StateManager.Builder<net.minecraft.block.Block, BlockState> builder) {
        builder.add(FACE, FACING);
    }
    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClient) {
            ServerWorld serverWorld = (ServerWorld) world;
            applyEffectsToNearbyPlayers(serverWorld, pos);
            serverWorld.scheduleBlockTick(pos, this, TICKS_BEFORE_REMOVE);
        }
    }
    @SuppressWarnings("deprecation")
    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, Blocks.AIR.getDefaultState());
    }
    private void applyEffectsToNearbyPlayers(ServerWorld world, BlockPos pos) {
        Box area = new Box(
                pos.getX() - 10, pos.getY() - 1, pos.getZ() - 10,
                pos.getX() + 10, pos.getY() + 1, pos.getZ() + 10
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
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        WallMountLocation face = state.get(FACE);
        Direction facing = state.get(FACING);

        switch (face) {
            case FLOOR:
                return Block.createCuboidShape(4, 0, 4, 12, 8, 12);
            case CEILING:
                return Block.createCuboidShape(4, 8, 4, 12, 16, 12);
            case WALL:
                switch (facing) {
                    case NORTH:
                        return Block.createCuboidShape(4, 4, 8, 12, 12, 16);
                    case SOUTH:
                        return Block.createCuboidShape(4, 4, 0, 12, 12, 8);
                    case WEST:
                        return Block.createCuboidShape(8, 4, 4, 16, 12, 12);
                    case EAST:
                        return Block.createCuboidShape(0, 4, 4, 8, 12, 12);
                }
        }
        return VoxelShapes.fullCube();
    }
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.getOutlineShape(state, world, pos, context);
    }
}