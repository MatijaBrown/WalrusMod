package com.beatles.walrusmod.init;

import com.beatles.walrusmod.WalrusMod;
import com.beatles.walrusmod.client.screen.ToasterMenu;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuInit {

	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, WalrusMod.MODID);
	
	public static final RegistryObject<MenuType<ToasterMenu>> TOASTER_MENU = registerMenuType(ToasterMenu::new, "toaster_menu");
	
	private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
		return MENUS.register(name, () -> IForgeMenuType.create(factory));
	}
	
}
