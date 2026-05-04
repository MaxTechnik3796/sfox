package cz.maxtechnik.sfox;

import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.EnumMap;
import java.util.List;

@Mod(SfoxMod.MODID)
public class SfoxMod {
	public static final String MODID = "sfox";
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
	public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, MODID);

	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> FOX_ARMOR_MATERIAL = ARMOR_MATERIALS.register("fox", () -> new ArmorMaterial(
			Util.make(new EnumMap<>(ArmorItem.Type.class), map -> map.put(ArmorItem.Type.HELMET, 2)),
			0, BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.FOX_AMBIENT), Ingredient::of,
			List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(MODID, "fox_model"))),
			0, 0
	));

	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> SNOW_FOX_ARMOR_MATERIAL = ARMOR_MATERIALS.register("snow_fox", () -> new ArmorMaterial(
			Util.make(new EnumMap<>(ArmorItem.Type.class), map -> map.put(ArmorItem.Type.HELMET, 2)),
			0, BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.FOX_AMBIENT), Ingredient::of,
			List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(MODID, "snow_fox_model"))),
			0, 0
	));

	public static final DeferredItem<Item> FOX = ITEMS.register("fox",
			() -> new FoxHatItem(FOX_ARMOR_MATERIAL, new Properties().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON), "red"));

	public static final DeferredItem<Item> SNOW_FOX = ITEMS.register("snow_fox",
			() -> new FoxHatItem(SNOW_FOX_ARMOR_MATERIAL, new Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC), "snow"));

	public SfoxMod(IEventBus modEventBus) {
		ITEMS.register(modEventBus);
		ARMOR_MATERIALS.register(modEventBus);
		modEventBus.addListener(this::addCreative);
	}

	private void addCreative(BuildCreativeModeTabContentsEvent tabData) {
		if (tabData.getTabKey() == CreativeModeTabs.COMBAT) {
			tabData.accept(FOX.get());
			tabData.accept(SNOW_FOX.get());
		}
	}
}