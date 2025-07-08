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

public class GoldenHeadBlock extends Block {
    private static final int TICKS_BEFORE_REMOVE = 40;
    private static final int EFFECT_RADIUS = 10;

    public static final EnumProperty<MountType> MOUNT = EnumProperty.of("mount", MountType.class);
    public static final IntProperty ROTATION = IntProperty.of("rotation", 0, 15);

    private static final VoxelShape SHAPE = Block.createCuboidShape(4, 0, 4, 12, 8, 12);

    public GoldenHeadBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState()
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
            rotation = getRotationFromYaw(ctx.getPlayerYaw()); // 地面用16方向
        } else if (clicked == Direction.DOWN) {
            mount = MountType.CEILING;
            rotation = getRotationFromYaw(ctx.getPlayerYaw()); // 天花板用16方向
        } else {
            mount = MountType.WALL;
            rotation = getRotationFromDirection(clicked); // 墙面用4方向映射成rotation的4个值
        }

        return getDefaultState().with(MOUNT, mount).with(ROTATION, rotation);
    }

    private int getRotationFromYaw(float yaw) {
        return MathHelper.floor((yaw * 16.0F / 360.0F) + 0.5F) & 15;
    }

    private int getRotationFromDirection(Direction direction) {
        // 墙面四个方向分别对应rotation的四个值(0,4,8,12)
        // 你可以根据模型调整这几个值对应的方向
        return switch (direction) {
            case SOUTH -> 0;  // 南墙
            case WEST  -> 4;  // 西墙
            case NORTH -> 8;  // 北墙
            case EAST  -> 12; // 东墙
            default -> {
                System.err.println("Unexpected direction for wall mount: " + direction);
                yield 0;
            }
        };
    }

    @Override
    @SuppressWarnings("deprecation")
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