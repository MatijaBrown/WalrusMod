package com.beatles.walrusmod.init;

import com.beatles.walrusmod.WalrusMod;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {

	public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WalrusMod.MODID);
	
	public static final RegistryObject<Item> WIRE = ITEMS.register("wire",
			() -> new Item(new Item.Properties()));
	
	public static final RegistryObject<Item> BOX_OF_CORNFLAKES = ITEMS.register("box_of_cornflakes",
			() -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> CORNFLAKE = ITEMS.register("cornflake",
			() -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> BOWL_OF_CEREAL = ITEMS.register("bowl_of_cereal",
			() -> new Item(new Item.Properties()));

	public static final RegistryObject<BlockItem> SWEETCORN_SEEDS = ITEMS.register("sweetcorn_seeds",
			() -> new ItemNameBlockItem(BlockInit.SWEETCORN_CROP.get(), new Item.Properties()));
	public static final RegistryObject<Item> SWEETCORN = ITEMS.register("sweetcorn",
			() -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationMod(1.0f).build())));

}
