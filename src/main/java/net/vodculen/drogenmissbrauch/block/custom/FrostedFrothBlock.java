package net.vodculen.drogenmissbrauch.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.vodculen.drogenmissbrauch.util.Ticks;
import net.vodculen.drogenmissbrauch.util.WithdrawableEntity;

public class FrostedFrothBlock extends Block {
	public static final IntProperty BITES;
	public static final int DEFAULT_COMPARATOR_OUTPUT;
	protected static final VoxelShape[] BITES_TO_SHAPE;

	public FrostedFrothBlock(Settings settings) {
		super(settings);
		this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(BITES, 0));
	}

	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return BITES_TO_SHAPE[state.get(BITES)];
	}

	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		ItemStack itemStack = player.getStackInHand(hand);

		if (world.isClient && player instanceof WithdrawableEntity withdrawable) {
			if (tryEat(world, pos, state, player).isAccepted() && !withdrawable.isWithdrawal() && player.hasStatusEffect(StatusEffects.HUNGER)) {
				return ActionResult.SUCCESS;
			}

			if (itemStack.isEmpty()) {
				return ActionResult.CONSUME;
			}
		}

		return tryEat(world, pos, state, player);
	}

	protected static ActionResult tryEat(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player) {
		if (!player.canConsume(false)) {
			return ActionResult.PASS;
		} else {
			int remainingBites = state.get(BITES);

			player.incrementStat(Stats.EAT_CAKE_SLICE);
			player.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, Ticks.getSeconds(30), 0, true, true));

			if (player instanceof WithdrawableEntity withdrawable && !withdrawable.isWithdrawal()) {
				withdrawable.setWithdrawal(Ticks.getMinutes(2));
			}

			world.emitGameEvent(player, GameEvent.EAT, pos);

			if (remainingBites < 6) {
				world.setBlockState(pos, (BlockState)state.with(BITES, remainingBites + 1), 3);
			} else {
				world.removeBlock(pos, false);
				world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
			}

			return ActionResult.SUCCESS;
		}
	}

	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return world.getBlockState(pos.down()).isSolid();
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(new Property[]{BITES});
	}

	public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return getComparatorOutput(state.get(BITES));
	}

	public static int getComparatorOutput(int bites) {
		return (7 - bites) * 2;
	}

	public boolean hasComparatorOutput(BlockState state) {
		return true;
	}

	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		return false;
	}

	static {
		BITES = Properties.BITES;
		DEFAULT_COMPARATOR_OUTPUT = getComparatorOutput(0);
		BITES_TO_SHAPE = new VoxelShape[]{Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 8.0, 15.0), Block.createCuboidShape(3.0, 0.0, 1.0, 15.0, 8.0, 15.0), Block.createCuboidShape(5.0, 0.0, 1.0, 15.0, 8.0, 15.0), Block.createCuboidShape(7.0, 0.0, 1.0, 15.0, 8.0, 15.0), Block.createCuboidShape(9.0, 0.0, 1.0, 15.0, 8.0, 15.0), Block.createCuboidShape(11.0, 0.0, 1.0, 15.0, 8.0, 15.0), Block.createCuboidShape(13.0, 0.0, 1.0, 15.0, 8.0, 15.0)};
	}
}
