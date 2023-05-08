package com.beatles.walrusmod.init;

import java.util.function.Supplier;

import com.beatles.walrusmod.WalrusMod;
import com.beatles.walrusmod.client.block.custom.SweetcornCropBlock;
import com.beatles.walrusmod.client.block.custom.ToasterBlock;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			WalrusMod.MODID);

	public static final RegistryObject<Block> YELLOW_MATTER_CUSTARD = register("yellow_matter_custard",
			() -> new Block(BlockBehaviour.Properties.of(Material.CAKE).strength(0.5f)), new Item.Properties());
	public static final RegistryObject<Block> CORNFLAKE_BLOCK = register("cornflake_block",
			() -> new Block(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.5f)), new Item.Properties());
	
	public static final RegistryObject<Block> TOASTER = register("toaster",
			() -> new ToasterBlock(BlockBehaviour.Properties.of(Material.METAL).strength(3.0f).noOcclusion()), new Item.Properties());
	
	// Kein Item für diese Blöcke; ist ja eine Pflanze.
	public static final RegistryObject<Block> SWEETCORN_CROP = BLOCKS.register("sweetcorn_crop",
			() -> new SweetcornCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));

	private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> supplier,
			Item.Properties properties) {
		RegistryObject<T> block = BLOCKS.register(name, supplier);
		ItemInit.ITEMS.register(name, () -> new BlockItem(block.get(), properties));
		return block;
	}

}
