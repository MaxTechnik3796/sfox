package cz.maxtechnik.sfox.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.Direction;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;

import cz.maxtechnik.sfox.init.SfoxModItems;
import cz.maxtechnik.sfox.init.SfoxModGameRules;
import cz.maxtechnik.sfox.SfoxMod;

public class FoxPlaceProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Direction direction, Entity entity, ItemStack itemstack) {
        if (direction == null || entity == null)
            return;
        double xx = 0;
        double yy = 0;
        double zz = 0;
        if (direction == Direction.UP) {
            xx = x + 0.5;
            yy = y + 1;
            zz = z + 0.5;
        } else if (direction == Direction.DOWN) {
            xx = x + 0.5;
            yy = y - 1;
            zz = z + 0.5;
        } else if (direction == Direction.NORTH) {
            xx = x + 0.5;
            yy = y;
            zz = z - 0.5;
        } else if (direction == Direction.SOUTH) {
            xx = x + 0.5;
            yy = y;
            zz = z + 1.5;
        } else if (direction == Direction.EAST) {
            xx = x + 1.5;
            yy = y;
            zz = z + 0.5;
        } else if (direction == Direction.WEST) {
            xx = x - 0.5;
            yy = y;
            zz = z + 0.5;
        } else {
            xx = x + 0.5;
            yy = y;
            zz = z + 0.5;
        }

        String customNBT = "";
        if (itemstack.hasCustomHoverName()) {
            String nameJson = Component.Serializer.toJson(itemstack.getHoverName());
            customNBT += ",CustomName:" + nameJson;
            customNBT += ",CustomNameVisible:1";
        }

        if (itemstack.hasTag() && itemstack.getTag().contains("FoxAge")) {
            int age = itemstack.getTag().getInt("FoxAge");
            customNBT += ",Age:" + age;
        }

        String foxType;
        if (itemstack.getItem() == SfoxModItems.FOX.get()) {
            foxType = "red";
        } else if (itemstack.getItem() == SfoxModItems.SNOW_FOX.get()) {
            foxType = "snow";
        } else {
            return;
        }

        if (entity instanceof Player _player) {
            ItemStack _stktoremove = itemstack.copy();
            _stktoremove.setCount(1);
            _player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
        }

        String finalCommand = String.format("summon fox ~ ~ ~ {Type:%s%s}", foxType, customNBT);

        if (world instanceof ServerLevel _level)
            _level.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(xx, yy, zz), Vec2.ZERO, _level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(),
                    finalCommand);

        if (world.getLevelData().getGameRules().getBoolean(SfoxModGameRules.ENABLE_PRINT_SFOX)) {
            SfoxMod.LOGGER.info("I place fox at: " + xx + " " + yy + " " + zz + " with command: " + finalCommand);
        }
    }
}
