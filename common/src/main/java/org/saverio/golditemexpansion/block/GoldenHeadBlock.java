package org.saverio.golditemexpansion.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public final class GoldenHeadBlock extends Block {
    private static final int TICKS_BEFORE_REMOVE = 10;

    public static final EnumProperty<AttachFace> FACE = BlockStateProperties.ATTACH_FACE;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape SHAPE_FLOOR = Block.box(4, 0, 4, 12, 8, 12);
    private static final VoxelShape SHAPE_CEILING = Block.box(4, 8, 4, 12, 16, 12);
    private static final VoxelShape SHAPE_WALL_SOUTH = Block.box(4, 4, 0, 12, 12, 8);
    private static final VoxelShape SHAPE_WALL_NORTH = Block.box(4, 4, 8, 12, 12, 16);
    private static final VoxelShape SHAPE_WALL_WEST = Block.box(8, 4, 4, 16, 12, 12);
    private static final VoxelShape SHAPE_WALL_EAST = Block.box(0, 4, 4, 8, 12, 12);

    public GoldenHeadBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any()
                .setValue(FACE, AttachFace.FLOOR)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACE, FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction clicked = ctx.getClickedFace();
        AttachFace face;
        Direction facing;

        if (clicked == Direction.UP) {
            face = AttachFace.FLOOR;
            facing = ctx.getHorizontalDirection().getOpposite();
        } else if (clicked == Direction.DOWN) {
            face = AttachFace.CEILING;
            facing = ctx.getHorizontalDirection().getOpposite();
        } else {
            face = AttachFace.WALL;
            facing = clicked;
        }

        return defaultBlockState().setValue(FACE, face).setValue(FACING, facing);
    }

    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, net.minecraft.world.phys.shapes.CollisionContext context) {
        AttachFace face = state.getValue(FACE);
        Direction facing = state.getValue(FACING);

        return switch (face) {
            case FLOOR -> SHAPE_FLOOR;
            case CEILING -> SHAPE_CEILING;
            case WALL -> switch (facing) {
                case SOUTH -> SHAPE_WALL_SOUTH;
                case NORTH -> SHAPE_WALL_NORTH;
                case WEST -> SHAPE_WALL_WEST;
                case EAST -> SHAPE_WALL_EAST;
                default -> SHAPE_FLOOR;
            };
        };
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, net.minecraft.world.item.ItemStack stack) {
        if (!level.isClientSide && placer instanceof Player player) {
            applyEffectsToPlayer(player);
            level.scheduleTick(pos, this, TICKS_BEFORE_REMOVE);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
    }

    private void applyEffectsToPlayer(Player player) {
        int duration = 6000;
        applyOrExtendEffect(player, new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, duration, 6));
        applyOrExtendEffect(player, new MobEffectInstance(MobEffects.FIRE_RESISTANCE, duration, 0));
        applyOrExtendEffect(player, new MobEffectInstance(MobEffects.ABSORPTION, duration, 9));
        applyOrExtendEffect(player, new MobEffectInstance(MobEffects.HEALTH_BOOST, duration, 9));
    }

    private void applyOrExtendEffect(Player player, MobEffectInstance newEffect) {
        MobEffectInstance current = player.getEffect(newEffect.getEffect());
        if (current != null) {
            int extended = current.getDuration() + newEffect.getDuration();
            int amp = Math.max(current.getAmplifier(), newEffect.getAmplifier());
            player.addEffect(new MobEffectInstance(newEffect.getEffect(), extended, amp));
        } else {
            player.addEffect(newEffect);
        }
    }
}
