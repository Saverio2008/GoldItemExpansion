package org.saverio.golditemexpansion.block;

import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.List;

public class GoldenHeadBlock extends Block {
    private static final int TICKS_BEFORE_REMOVE = 40;
    private static final int EFFECT_RADIUS = 10;

    public static final EnumProperty<MountType> MOUNT = EnumProperty.of("mount", MountType.class);
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;

    private static final VoxelShape SHAPE_FLOOR = Block.createCuboidShape(4, 0, 4, 12, 8, 12);
    private static final VoxelShape SHAPE_CEILING   = Block.createCuboidShape(4, 8, 4, 12, 16, 12);
    private static final VoxelShape SHAPE_WALL_SOUTH = Block.createCuboidShape(4, 4, 0, 12, 12, 8);
    private static final VoxelShape SHAPE_WALL_NORTH = Block.createCuboidShape(4, 4, 8, 12, 12, 16);
    private static final VoxelShape SHAPE_WALL_WEST  = Block.createCuboidShape(8, 4, 4, 16, 12, 12);
    private static final VoxelShape SHAPE_WALL_EAST  = Block.createCuboidShape(0, 4, 4, 8, 12, 12);

    public GoldenHeadBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState()
                .with(MOUNT, MountType.FLOOR)
                .with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(MOUNT, FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction clicked = ctx.getSide();
        MountType mount;
        Direction facing;

        if (clicked == Direction.UP) {
            mount = MountType.FLOOR;
            facing = ctx.getHorizontalPlayerFacing().getOpposite(); // 玩家朝向反方向作为面朝
        } else if (clicked == Direction.DOWN) {
            mount = MountType.CEILING;
            facing = ctx.getHorizontalPlayerFacing().getOpposite();
        } else {
            mount = MountType.WALL;
            facing = clicked;
        }

        return getDefaultState().with(MOUNT, mount).with(FACING, facing);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        MountType mount = state.get(MOUNT);
        Direction facing = state.get(FACING);

        return switch (mount) {
            case FLOOR -> SHAPE_FLOOR;
            case CEILING -> SHAPE_CEILING;
            case WALL ->
                    switch (facing) {
                        case SOUTH -> SHAPE_WALL_SOUTH;
                        case NORTH -> SHAPE_WALL_NORTH;
                        case WEST  -> SHAPE_WALL_WEST;
                        case EAST  -> SHAPE_WALL_EAST;
                        default -> SHAPE_FLOOR;
                    };
        };
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, net.minecraft.item.ItemStack itemStack) {
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
        Box area = new Box(pos).expand(EFFECT_RADIUS);
        List<PlayerEntity> players = world.getEntitiesByClass(PlayerEntity.class, area, p -> true);
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
            int extended = current.getDuration() + newEffect.getDuration();
            int amp = Math.max(current.getAmplifier(), newEffect.getAmplifier());
            player.addStatusEffect(new StatusEffectInstance(newEffect.getEffectType(), extended, amp));
        } else {
            player.addStatusEffect(newEffect);
        }
    }
}