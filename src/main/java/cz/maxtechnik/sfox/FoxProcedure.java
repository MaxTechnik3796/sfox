package cz.maxtechnik.sfox;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;

import java.util.Objects;
@Mod.EventBusSubscriber
public class FoxProcedure{
	@SubscribeEvent
	public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event){
		if(event.getHand()!=event.getEntity().getUsedItemHand())
			return;
		pickup(event.getTarget(),event.getEntity());
	}
	private static void pickup(Entity entity,Entity sourceentity){
		if(entity==null||sourceentity==null||!sourceentity.isShiftKeyDown()||!(Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType())).toString()).equals("minecraft:fox"))return;
		CompoundTag entityNBT=entity.saveWithoutId(new CompoundTag());
		String foxType=entityNBT.getString("Type");
		ItemStack foxItemStack;
		if(foxType.equals("red")){
			foxItemStack=new ItemStack(SfoxMod.FOX.get()).copy();
		}else if(foxType.equals("snow")){
			foxItemStack=new ItemStack(SfoxMod.SNOW_FOX.get()).copy();
		}else{
			return;
		}
		if(entityNBT.contains("CustomName")&&!entityNBT.getString("CustomName").isEmpty()){
			foxItemStack.setHoverName(Component.Serializer.fromJson(entityNBT.getString("CustomName")));
		}
		if(entityNBT.contains("Age")){
			foxItemStack.getOrCreateTag().putInt("FoxAge",entityNBT.getInt("Age"));
		}
		if(sourceentity instanceof Player player){
			foxItemStack.setCount(1);
			ItemHandlerHelper.giveItemToPlayer(player,foxItemStack);
		}
		if(entity instanceof LivingEntity livingEntity){
			ItemStack heldItem=livingEntity.getMainHandItem();
			if(!heldItem.isEmpty()){
				livingEntity.spawnAtLocation(heldItem);
			}
		}
		if(!entity.level().isClientSide()){
			entity.discard();
		}
	}
	public static void place(LevelAccessor world,double x,double y,double z,Direction direction,Entity entity,ItemStack itemstack){
		if(direction==null||entity==null)
			return;
		x=x+direction.getStepX()+0.5;
		y=y+direction.getStepY();
		z=z+direction.getStepZ()+0.5;
		String customNBT="";
		if(itemstack.hasCustomHoverName()){
			String nameJson=Component.Serializer.toJson(itemstack.getHoverName());
			customNBT+=",CustomName:"+"'"+nameJson+"'";
		}
		if(itemstack.hasTag()){
			assert itemstack.getTag()!=null;
			if(itemstack.getTag().contains("FoxAge")){
				int age=itemstack.getTag().getInt("FoxAge");
				customNBT+=",Age:"+age;
			}
		}
		String foxType;
		if(itemstack.getItem()==SfoxMod.FOX.get()){
			foxType="red";
		}else if(itemstack.getItem()==SfoxMod.SNOW_FOX.get()){
			foxType="snow";
		}else{
			return;
		}
		if(entity instanceof Player player){
			ItemStack stackToRemove=itemstack.copy();
			stackToRemove.setCount(1);
			player.getInventory().clearOrCountMatchingItems(p->stackToRemove.getItem()==p.getItem(),1,player.inventoryMenu.getCraftSlots());
		}
		String finalCommand=String.format("summon fox ~ ~ ~ {Type:%s%s}",foxType,customNBT);
		if(world instanceof ServerLevel level)
			level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL,new Vec3(x,y,z),Vec2.ZERO,level,4,"",Component.literal(""),level.getServer(),null).withSuppressedOutput(),finalCommand);
	}
}