package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class HollowedLogBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape X_SHAPE = Shapes.or(Block.box(0, 0, 0, 16, 16, 3), Block.box(0, 13, 0, 16, 16, 16), Block.box(0, 0, 13, 16, 16, 16), Block.box(0, 0, 0, 16, 3, 16));
    protected static final VoxelShape Y_SHAPE = Shapes.or(Block.box(0, 0, 0, 16, 16, 3), Block.box(0, 0, 0, 3, 16, 16), Block.box(0, 0, 13, 16, 16, 16), Block.box(13, 0, 0, 16, 16, 16));
    protected static final VoxelShape Z_SHAPE = Shapes.or(Block.box(13, 0, 0, 16, 16, 16), Block.box(0, 0, 0, 3, 16, 16), Block.box(0, 13, 0, 16, 16, 16), Block.box(0, 0, 0, 16, 3, 16));
    protected static final VoxelShape RAYCAST_SHAPE = Shapes.block();

    public HollowedLogBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(AXIS, Direction.Axis.Y));
    }

    @Override
    public VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return switch (state.getValue(AXIS)) {
            default -> X_SHAPE;
            case Y -> Y_SHAPE;
            case Z -> Z_SHAPE;
        };
    }

	@Override
    public VoxelShape getInteractionShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return RAYCAST_SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(AXIS, ctx.getClickedFace().getAxis()).setValue(WATERLOGGED, ctx.getLevel().getFluidState(ctx.getClickedPos()).is(Fluids.WATER));
    }

    @Override
    public BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        level.scheduleTick(currentPos, this, 1);
        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    public RenderShape getRenderShape(@NotNull BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return !(Boolean) state.getValue(WATERLOGGED);
    }

	@Override
    public boolean useShapeForLightOcclusion(@NotNull BlockState state) {
        return true;
    }

	public static void hollowEffects(Level level, Direction face, BlockState state, BlockPos pos, boolean isStem) {
		if (level instanceof ServerLevel serverLevel) {
			double stepX = face.getStepX();
			double stepY = face.getStepY();
			double stepZ = face.getStepZ();
			if (stepX < 0) {
				stepX *= -1;
			}
			if (stepY < 0) {
				stepY *= -1;
			}
			if (stepZ < 0) {
				stepZ *= -1;
			}
			serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, level.random.nextInt(10, 24), 0.25 + (stepX * 0.25), 0.25 + (stepY * 0.25), 0.25 + (stepZ * 0.25), 0.05D);
			level.playSound(null, pos, isStem ? RegisterSounds.STEM_HOLLOWED : RegisterSounds.LOG_HOLLOWED, SoundSource.BLOCKS, 0.7F, 0.95F + (level.random.nextFloat() * 0.2F));
		}
	}

}
