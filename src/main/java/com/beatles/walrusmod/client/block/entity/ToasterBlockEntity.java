package com.beatles.walrusmod.client.block.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.beatles.walrusmod.client.screen.ToasterMenu;
import com.beatles.walrusmod.init.BlockEntityInit;
import com.beatles.walrusmod.init.ItemInit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ToasterBlockEntity extends BlockEntity implements MenuProvider {
	
	protected final ContainerData data;
	
	private final ItemStackHandler itemHandler = new ItemStackHandler(2) {
		@Override
		protected void onContentsChanged(int slot) {
			setChanged();
		}
	};
	
	private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
	
	private int progress = 0;
	private int maxProgress = 78;

	public ToasterBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityInit.TOASTER.get(), pos, state);
		this.data = new ContainerData() {
			@Override
			public int get(int index) {
				return switch (index) {
					case 0 -> ToasterBlockEntity.this.progress;
					case 1 -> ToasterBlockEntity.this.maxProgress;
					default -> 0;
				};
			}
			
			@Override
			public void set(int index, int value) {
				switch (index) {
					case 0 -> ToasterBlockEntity.this.progress = value;
					case 1 -> ToasterBlockEntity.this.maxProgress = value;
				};
			}
			
			@Override
			public int getCount() {
				return 2;
			}
		};
	}

	@Override
	public Component getDisplayName() {
		// Übersetzt hier nicht, aber ist ja nur ein Test.
		return Component.literal("Toaster");
	}
	
	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
		return new ToasterMenu(id, inventory, this, this.data);
	}
	
	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (cap == ForgeCapabilities.ITEM_HANDLER) {
			return lazyItemHandler.cast();
		}
		return super.getCapability(cap, side);
	}
	
	@Override
	public void onLoad() {
		super.onLoad();
		lazyItemHandler = LazyOptional.of(() -> itemHandler);
	}
	
	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		lazyItemHandler.invalidate();
	}
	
	@Override
	protected void saveAdditional(CompoundTag nbt) {
		nbt.put("inventory", itemHandler.serializeNBT());
		
		super.saveAdditional(nbt);
	}

	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		itemHandler.deserializeNBT(nbt.getCompound("nbt"));
	}

	public void drops() {
		var inventory = new SimpleContainer(itemHandler.getSlots());
		for (int i = 0; i < itemHandler.getSlots(); i++) {
			inventory.setItem(i, itemHandler.getStackInSlot(i));
		}
		
		Containers.dropContents(level, worldPosition, inventory);
	}
	
	private void resetProgress() {
		progress = 0;
	}
	
	private static void craftItem(ToasterBlockEntity entity) {
		if (hasRecipe(entity)) {
			entity.itemHandler.extractItem(0, 1, false);
			entity.itemHandler.setStackInSlot(1, new ItemStack(ItemInit.CORNFLAKE.get(), entity.itemHandler.getStackInSlot(1).getCount() + 1));
			
			entity.resetProgress();
		}
	}
	
	private static boolean hasRecipe(ToasterBlockEntity entity) {
		var inventory = new SimpleContainer(entity.itemHandler.getSlots());
		for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
			inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
		}
		
		boolean hasCornInFirstSlot = entity.itemHandler.getStackInSlot(0).getItem() == ItemInit.SWEETCORN_SEEDS.get();
		
		return hasCornInFirstSlot && canInsertAmountIntoOutputSlot(inventory) && canInsertItemIntoOutputSlot(inventory, new ItemStack(ItemInit.SWEETCORN_SEEDS.get(), 1));
	}

	private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
		return inventory.getItem(0).getItem() == stack.getItem() || inventory.getItem(0).isEmpty();
	}
	
	private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
		return inventory.getItem(0).getMaxStackSize() > inventory.getItem(0).getCount();
	}
	
	public static void tick(Level level, BlockPos pos, BlockState state, ToasterBlockEntity entity) {
		if (level.isClientSide()) {
			return;
		}
		
		if (hasRecipe(entity)) {
			entity.progress++;
			System.out.println("Crafting: " + entity.progress);
			setChanged(level, pos, state);
			
			if (entity.progress >= entity.maxProgress) {
				craftItem(entity);
			}
		} else {
			entity.resetProgress();
			setChanged(level, pos, state);
		}
	}
	
}
