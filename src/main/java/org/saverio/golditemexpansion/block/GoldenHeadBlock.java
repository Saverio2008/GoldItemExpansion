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
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

public class GoldenHeadBlock extends Block {
    private static final int TICKS_BEFORE_REMOVE = 40;
    private static final int EFFECT_RADIUS = 10;

    public static final EnumProperty<MountType> MOUNT = EnumProperty.of("mount", MountType.class);
    public static final IntProperty ROTATION = IntProperty.of("rotation", 0, 15);

    private static final VoxelShape SHAPE = Block.createCuboidShape(4, 0, 4, 12, 8, 12);

    // 壁挂四方向映射为 4 个 rotation 值，配合 JSON 渲染
    private static final Map<Direction, Integer> WALL_ROTATION = Map.of(
            Direction.SOUTH, 0,
            Direction.WEST, 4,
            Direction.NORTH, 8,
            Direction.EAST, 12
    );

    public GoldenHeadBlock(Settings settings) {
        super(settings);
        setDefaultState(getStateManager().getDefaultState()
                .with(MOUNT, MountType.FLOOR)
                .with(ROTATION, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(MOUNT, ROTATION);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction clicked = ctx.getSide();
        MountType mount;
        int rotation;

        if (clicked == Direction.UP) {
            mount = MountType.FLOOR;
            rotation = getRotationFromYaw(ctx.getPlayerYaw());
        } else if (clicked == Direction.DOWN) {
            mount = MountType.CEILING;
            rotation = getRotationFromYaw(ctx.getPlayerYaw());
        } else {
            mount = MountType.WALL;
            rotation = WALL_ROTATION.getOrDefault(clicked, 0);
        }

        return getDefaultState().with(MOUNT, mount).with(ROTATION, rotation);
    }

    private int getRotationFromYaw(float yaw) {
        return MathHelper.floor((yaw * 16.0F / 360.0F) + 0.5F) & 15;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
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