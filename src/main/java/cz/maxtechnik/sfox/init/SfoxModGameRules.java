
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package cz.maxtechnik.sfox.init;

import net.minecraftforge.fml.common.Mod;

import net.minecraft.world.level.GameRules;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SfoxModGameRules {
	public static final GameRules.Key<GameRules.BooleanValue> ENABLE_PRINT_SFOX = GameRules.register("enablePrintSFOX", GameRules.Category.UPDATES, GameRules.BooleanValue.create(false));
}
