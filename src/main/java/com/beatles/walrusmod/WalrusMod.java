package com.beatles.walrusmod;

import com.beatles.walrusmod.client.screen.ToasterScreen;
import com.beatles.walrusmod.init.BlockEntityInit;
import com.beatles.walrusmod.init.BlockInit;
import com.beatles.walrusmod.init.ItemInit;
import com.beatles.walrusmod.init.MenuInit;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(WalrusMod.MODID)
public class WalrusMod {
	public static final String MODID = "walrusmod";
	
	public WalrusMod() {
		IEventBus bus =  FMLJavaModLoadingContext.get().getModEventBus();
		
		ItemInit.ITEMS.register(bus);
		BlockInit.BLOCKS.register(bus);
		BlockEntityInit.BLOCK_ENTITIES.register(bus);
		MenuInit.MENUS.register(bus);
		
		bus.addListener(this::addCreative);
		
		// Setze die "ClientModEvents"-Klasse auf den Forge-EventBus.
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void addCreative(CreativeModeTabEvent.BuildContents event) {
		if (event.getTab() == CreativeModeTabs.REDSTONE_BLOCKS) {
			event.accept(ItemInit.WIRE);
		} else if (event.getTab() == CreativeModeTabs.FOOD_AND_DRINKS) {
			event.accept(ItemInit.BOWL_OF_CEREAL);
			event.accept(ItemInit.BOX_OF_CORNFLAKES);
			event.accept(ItemInit.CORNFLAKE);
		} else if (event.getTab() == CreativeModeTabs.NATURAL_BLOCKS) {
			event.accept(ItemInit.SWEETCORN);
			event.accept(ItemInit.SWEETCORN_SEEDS);
		} else if (event.getTab() == CreativeModeTabs.BUILDING_BLOCKS) {
			event.accept(BlockInit.CORNFLAKE_BLOCK);
			event.accept(BlockInit.YELLOW_MATTER_CUSTARD);
		}
	}
	
	@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ClientModEvents {
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
			// Registrieren der Menü-GUIs
			MenuScreens.register(MenuInit.TOASTER_MENU.get(), ToasterScreen::new);
		}
	}
	
}
