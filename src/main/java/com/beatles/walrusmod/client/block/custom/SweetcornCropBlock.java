package com.beatles.walrusmod.client.block.custom;

import com.beatles.walrusmod.init.ItemInit;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class SweetcornCropBlock extends CropBlock {

	public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 6);
	
	public SweetcornCropBlock(Properties p_52247_) {
		super(p_52247_);
	}
	
	@Override
	protected ItemLike getBaseSeedId() {
		return ItemInit.SWEETCORN.get();
	}

	@Override
	public IntegerProperty getAgeProperty() {
		return AGE;
	}
	
	@Override
	public int getMaxAge() {
		return 6;
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}
	
}
