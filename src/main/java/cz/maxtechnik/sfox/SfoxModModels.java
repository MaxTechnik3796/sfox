package cz.maxtechnik.sfox;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.api.distmarker.Dist;
import cz.maxtechnik.sfox.client.model.FoxModel;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

@EventBusSubscriber(modid = SfoxMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SfoxModModels{
	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event){
		event.registerLayerDefinition(FoxModel.LAYER_LOCATION, FoxModel::createBodyLayer);
	}

	@SubscribeEvent
	public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
		event.registerItem(new IClientItemExtensions() {
			@Override
			public @NotNull HumanoidModel<?> getHumanoidArmorModel(@NotNull LivingEntity living, @NotNull ItemStack stack, @NotNull EquipmentSlot slot, @NotNull HumanoidModel<?> defaultModel) {
				HumanoidModel<?> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(), Map.of(
						"head", new FoxModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(FoxModel.LAYER_LOCATION)).Head,
						"hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
						"body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
						"right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
						"left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
						"right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
						"left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap())
				)));
				armorModel.crouching = living.isShiftKeyDown();
				armorModel.riding = defaultModel.riding;
				armorModel.young = living.isBaby();
				return armorModel;
			}
		}, SfoxMod.FOX.get(), SfoxMod.SNOW_FOX.get());
	}
}
