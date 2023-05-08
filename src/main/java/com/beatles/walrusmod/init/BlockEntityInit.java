package com.beatles.walrusmod.init;

import com.beatles.walrusmod.WalrusMod;
import com.beatles.walrusmod.client.block.entity.ToasterBlockEntity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {

	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, WalrusMod.MODID);
	
	public static final RegistryObject<BlockEntityType<ToasterBlockEntity>> TOASTER = BLOCK_ENTITIES.register("toaster",
			() -> BlockEntityType.Builder.of(ToasterBlockEntity::new, BlockInit.TOASTER.get()).build(null));
	
}
