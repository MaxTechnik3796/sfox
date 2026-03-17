package cz.maxtechnik.sfox;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(SfoxMod.MODID)
public class SfoxMod {
	public static final String MODID = "sfox";
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

	public static final RegistryObject<Item> FOX = ITEMS.register("fox",
			() -> new FoxHatItem(Rarity.UNCOMMON, "sfox:textures/entities/fox_model.png", "red"));

	public static final RegistryObject<Item> SNOW_FOX = ITEMS.register("snow_fox",
			() -> new FoxHatItem(Rarity.EPIC, "sfox:textures/entities/snow_fox_model.png", "snow"));

	@SuppressWarnings("removal") // Tímto potlačíme varování u staršího způsobu načítání pro Forge 1.20.1
	public SfoxMod() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		ITEMS.register(bus);
		bus.addListener(this::addCreative);
	}

	private void addCreative(BuildCreativeModeTabContentsEvent tabData) {
		if (tabData.getTabKey() == CreativeModeTabs.COMBAT) {
			tabData.accept(FOX.get());
			tabData.accept(SNOW_FOX.get());
		}
	}
}