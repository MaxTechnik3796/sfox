package cz.maxtechnik.sfox;

import cz.maxtechnik.sfox.item.FoxItem;
import cz.maxtechnik.sfox.item.SnowFoxItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.FriendlyByteBuf;
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.BiConsumer;

@Mod(SfoxMod.MODID)
@SuppressWarnings("removal")
public class SfoxMod{
	public static final Logger LOGGER=LogManager.getLogger(SfoxMod.class);
	public static final String MODID="sfox";
    public static final DeferredRegister<Item>ITEMS=DeferredRegister.create(ForgeRegistries.ITEMS,MODID);
    public static final RegistryObject<Item>FOX=ITEMS.register("fox", FoxItem.Helmet::new);
    public static final RegistryObject<Item>SNOW_FOX=ITEMS.register("snow_fox", SnowFoxItem.Helmet::new);
	public SfoxMod(){
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(bus);
        bus.addListener(this::addCreative);
	}
    private void addCreative(BuildCreativeModeTabContentsEvent tabData){
        if(tabData.getTabKey()==CreativeModeTabs.COMBAT){
            tabData.accept(FOX.get());
            tabData.accept(SNOW_FOX.get());
        }
    }
	private static final String PROTOCOL_VERSION="1";
	public static final SimpleChannel PACKET_HANDLER=NetworkRegistry.newSimpleChannel(ResourceLocation.fromNamespaceAndPath(MODID,MODID),()->PROTOCOL_VERSION,PROTOCOL_VERSION::equals,PROTOCOL_VERSION::equals);
	private static int messageID=0;
	public static<T> void addNetworkMessage(Class<T>messageType,BiConsumer<T,FriendlyByteBuf>encoder,Function<FriendlyByteBuf,T>decoder,BiConsumer<T,Supplier<NetworkEvent.Context>>messageConsumer){
		PACKET_HANDLER.registerMessage(messageID,messageType,encoder,decoder,messageConsumer);
		messageID++;
	}
}