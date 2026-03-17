package cz.maxtechnik.sfox;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

@Mod.EventBusSubscriber
public class FoxProcedure {

	@SubscribeEvent
	public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
		if (event.getHand() != InteractionHand.MAIN_HAND) return;
		pickup(event.getTarget(), event.getEntity());
	}
	private static void pickup(Entity entity, Entity sourceentity) {
		if (entity == null || !(sourceentity instanceof Player player) || !player.isShiftKeyDown()) return;
		if (!"minecraft:fox".equals(Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType())).toString())) return;
		CompoundTag entityNBT = entity.saveWithoutId(new CompoundTag());
		String foxType = entityNBT.getString("Type");
		ItemStack foxItemStack;
		if (foxType.equals("red")) foxItemStack = new ItemStack(SfoxMod.FOX.get());
		else if (foxType.equals("snow")) foxItemStack = new ItemStack(SfoxMod.SNOW_FOX.get());
		else return;
		if (entityNBT.contains("CustomName") && !entityNBT.getString("CustomName").isEmpty()) {
			foxItemStack.setHoverName(Component.Serializer.fromJson(entityNBT.getString("CustomName")));
		}
		if (entityNBT.contains("Age")) {
			foxItemStack.getOrCreateTag().putInt("FoxAge", entityNBT.getInt("Age"));
		}
		foxItemStack.setCount(1);
		if (player.getMainHandItem().isEmpty()) {
			player.setItemInHand(InteractionHand.MAIN_HAND, foxItemStack);
		}
		else if (!player.getInventory().add(foxItemStack)) {
			player.drop(foxItemStack, false);
		}
		if (entity instanceof LivingEntity livingEntity && !livingEntity.getMainHandItem().isEmpty()) {
			livingEntity.spawnAtLocation(livingEntity.getMainHandItem());
		}
		if (!entity.level().isClientSide()) {
			entity.discard();
		}
	}
	public static void place(LevelAccessor world, BlockPos pos, Direction direction, Player player, ItemStack itemstack) {
		if (direction == null || player == null || !(world instanceof ServerLevel level) || !(itemstack.getItem() instanceof FoxHatItem foxItem)) return;

		double x = pos.getX() + direction.getStepX() + 0.5;
		double y = pos.getY() + direction.getStepY();
		double z = pos.getZ() + direction.getStepZ() + 0.5;

		String customNBT = "";
		if (itemstack.hasCustomHoverName()) {
			customNBT += ",CustomName:'" + Component.Serializer.toJson(itemstack.getHoverName()) + "'";
		}
		if (itemstack.hasTag()) {
            assert itemstack.getTag() != null;
            if (itemstack.getTag().contains("FoxAge")) {
                customNBT += ",Age:" + itemstack.getTag().getInt("FoxAge");
            }
        }
		if (!player.isCreative()) {
			itemstack.shrink(1);
		}
		String finalCommand = String.format("summon fox ~ ~ ~ {Type:%s%s}", foxItem.getFoxType(), customNBT);
		level.getServer().getCommands().performPrefixedCommand(
				new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, level, 4, "", Component.empty(), level.getServer(), null).withSuppressedOutput(),
				finalCommand
		);
	}
}