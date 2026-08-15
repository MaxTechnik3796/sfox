package cz.maxtechnik.sfox;

import com.mojang.logging.LogUtils;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.util.EnumMap;
import java.util.List;
@SuppressWarnings("removal")
@Mod(SfoxMod.MODID)
public class SfoxMod{
	public static final String MODID="sfox";
	public static final Logger LOGGER=LogUtils.getLogger();
	public static final DeferredRegister.Items ITEMS=DeferredRegister.createItems(MODID);
	public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS=DeferredRegister.create(Registries.ARMOR_MATERIAL,MODID);
	public static final DeferredHolder<ArmorMaterial,ArmorMaterial> FOX_ARMOR_MATERIAL=ARMOR_MATERIALS.register("fox",()->new ArmorMaterial(
			Util.make(new EnumMap<>(ArmorItem.Type.class),map->map.put(ArmorItem.Type.HELMET,2)),
			0,BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.FOX_AMBIENT),Ingredient::of,
			List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(MODID,"fox_model"))),
			0,0
	));
	public static final DeferredHolder<ArmorMaterial,ArmorMaterial> SNOW_FOX_ARMOR_MATERIAL=ARMOR_MATERIALS.register("snow_fox",()->new ArmorMaterial(
			Util.make(new EnumMap<>(ArmorItem.Type.class),map->map.put(ArmorItem.Type.HELMET,2)),
			0,BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.FOX_AMBIENT),Ingredient::of,
			List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(MODID,"snow_fox_model"))),
			0,0
	));
	public static final DeferredItem<Item> FOX=ITEMS.register("fox",
			()->new FoxHatItem(FOX_ARMOR_MATERIAL,new Properties().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON),"red"));
	public static final DeferredItem<Item> SNOW_FOX=ITEMS.register("snow_fox",
			()->new FoxHatItem(SNOW_FOX_ARMOR_MATERIAL,new Properties().stacksTo(1).fireResistant().rarity(Rarity.EPIC),"snow"));
	public SfoxMod(IEventBus bus){
		ITEMS.register(bus);
		ARMOR_MATERIALS.register(bus);
		bus.addListener(this::commonSetup);
		bus.addListener(this::addCreative);
	}
	private void addCreative(BuildCreativeModeTabContentsEvent tabData){
		if(tabData.getTabKey().equals(CreativeModeTabs.COMBAT)){
			tabData.accept(FOX.get());
			tabData.accept(SNOW_FOX.get());
		}
	}
	private void commonSetup(final FMLCommonSetupEvent event){
		LOGGER.info("SuperFox: Common Setup");
	}
	@SubscribeEvent
	public void onServerStarting(ServerStartingEvent event){
		LOGGER.info("SuperFox: Server Starting");
	}
	@EventBusSubscriber(modid=MODID, bus=EventBusSubscriber.Bus.MOD, value=Dist.CLIENT)
	public static class ClientModEvents{
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event){
			LOGGER.info("SuperFox: Client Setup");
		}
	}
}