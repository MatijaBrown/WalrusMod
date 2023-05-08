package com.beatles.walrusmod.client.block.custom;

import com.beatles.walrusmod.client.block.entity.ToasterBlockEntity;
import com.beatles.walrusmod.init.BlockEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

public class ToasterBlock extends BaseEntityBlock {

	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	
	private static final VoxelShape SHAPE = Block.box(4.0, 0.0, 5.0, 12.0, 5.0, 11.0);
	
	public ToasterBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_,
			CollisionContext p_60558_) {
		return SHAPE;
	}

	// BLOCK ENTITY

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState,
			boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof ToasterBlockEntity) {
				((ToasterBlockEntity)blockEntity).drops();
			}
		}
		super.onRemove(state, level, pos, newState, isMoving);
	}
	
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
			InteractionHand handler, BlockHitResult hitResult) {
		if (!level.isClientSide()) {
			BlockEntity entity = level.getBlockEntity(pos);
			if (entity instanceof ToasterBlockEntity) {
				NetworkHooks.openScreen((ServerPlayer)player, (ToasterBlockEntity)entity, pos);
			} else {
				throw new IllegalStateException("The container provider is missing!");
			}
		}
		
		return InteractionResult.sidedSuccess(level.isClientSide());
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new ToasterBlockEntity(pos, state);
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
			BlockEntityType<T> type) {
		return createTickerHelper(type, BlockEntityInit.TOASTER.get(), ToasterBlockEntity::tick);
	}
	
}
