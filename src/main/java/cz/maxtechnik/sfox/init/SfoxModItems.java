
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package cz.maxtechnik.sfox.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.Item;

import cz.maxtechnik.sfox.item.SnowFoxItem;
import cz.maxtechnik.sfox.item.SnowFoxArmorItem;
import cz.maxtechnik.sfox.item.FoxItem;
import cz.maxtechnik.sfox.item.FoxArmorItem;
import cz.maxtechnik.sfox.SfoxMod;

public class SfoxModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, SfoxMod.MODID);
	public static final RegistryObject<Item> FOX = REGISTRY.register("fox", () -> new FoxItem());
	public static final RegistryObject<Item> FOX_ARMOR_HELMET = REGISTRY.register("fox_armor_helmet", () -> new FoxArmorItem.Helmet());
	public static final RegistryObject<Item> SNOW_FOX = REGISTRY.register("snow_fox", () -> new SnowFoxItem());
	public static final RegistryObject<Item> SNOW_FOX_ARMOR_HELMET = REGISTRY.register("snow_fox_armor_helmet", () -> new SnowFoxArmorItem.Helmet());
	// Start of user code block custom items
	// End of user code block custom items
}
