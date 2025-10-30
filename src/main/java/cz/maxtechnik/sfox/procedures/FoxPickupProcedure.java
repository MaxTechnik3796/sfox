package cz.maxtechnik.sfox.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import cz.maxtechnik.sfox.init.SfoxModItems;
import cz.maxtechnik.sfox.init.SfoxModGameRules;
import cz.maxtechnik.sfox.SfoxMod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class FoxPickupProcedure {
	@SubscribeEvent
	public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
		if (event.getHand() != event.getEntity().getUsedItemHand())
			return;
		execute(event, event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), event.getTarget(), event.getEntity());
	}
    private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
        if (entity == null || sourceentity == null || !sourceentity.isShiftKeyDown() || !(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString()).equals("minecraft:fox"))
            return;

        CompoundTag entityNBT = entity.saveWithoutId(new CompoundTag());
        String foxType = entityNBT.getString("Type");

        ItemStack foxItemStack = null;

        if (foxType.equals("red")) {
            foxItemStack = new ItemStack(SfoxModItems.FOX.get()).copy();
        } else if (foxType.equals("snow")) {
            foxItemStack = new ItemStack(SfoxModItems.SNOW_FOX.get()).copy();
        } else {
            if (world.getLevelData().getGameRules().getBoolean(SfoxModGameRules.ENABLE_PRINT_SFOX)) {
                SfoxMod.LOGGER.info("Fox type: " + foxType + "{ERROR}");
            }
            return;
        }

        if (entityNBT.contains("CustomName") && !entityNBT.getString("CustomName").isEmpty()) {
            foxItemStack.setHoverName(Component.Serializer.fromJson(entityNBT.getString("CustomName")));
        }

        if (entityNBT.contains("Age")) {
            foxItemStack.getOrCreateTag().putInt("FoxAge", entityNBT.getInt("Age"));
        }

        if (sourceentity instanceof Player _player) {
            foxItemStack.setCount(1);
            ItemHandlerHelper.giveItemToPlayer(_player, foxItemStack);
        }

        if (world instanceof ServerLevel _level)
            _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
                    ("tp " + entity.getStringUUID() + " 0 -64 0"));
        if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
            _entity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 60, 0, true, false));
        SfoxMod.queueServerWork(40, () -> {
            if (world instanceof ServerLevel _level)
                _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
                        ("kill " + entity.getStringUUID()));
        });
    }
}
