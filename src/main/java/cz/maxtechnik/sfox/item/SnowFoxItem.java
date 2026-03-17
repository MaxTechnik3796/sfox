package cz.maxtechnik.sfox.item;

import cz.maxtechnik.sfox.FoxProcedure;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.Minecraft;
import cz.maxtechnik.sfox.client.model.FoxModel;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.Map;
import java.util.Collections;
public abstract class SnowFoxItem extends ArmorItem{
	public SnowFoxItem(Type type,Properties properties){
		super(new ArmorMaterial(){
			@Override
			public int getDurabilityForType(@NotNull Type type){
				return 0;
			}
			@Override
			public int getDefenseForType(@NotNull Type type){
				return new int[]{0,0,0,2}[type.getSlot().getIndex()];
			}
			@Override
			public int getEnchantmentValue(){
				return 0;
			}
			@Override
			public @NotNull SoundEvent getEquipSound(){
				return Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft","entity.fox.ambient")));
			}
			@Override
			public @NotNull Ingredient getRepairIngredient(){
				return Ingredient.of();
			}
			@Override
			public @NotNull String getName(){
				return "snow_fox_armor";
			}
			@Override
			public float getToughness(){
				return 0f;
			}
			@Override
			public float getKnockbackResistance(){
				return 0f;
			}
		},type,properties);
	}
	public static class Helmet extends SnowFoxItem{
		public Helmet(){
			super(Type.HELMET,new Properties().fireResistant().rarity(Rarity.EPIC));
		}
		@Override
		public void initializeClient(Consumer<IClientItemExtensions> consumer){
			consumer.accept(new IClientItemExtensions(){
				@Override
				public @NotNull HumanoidModel getHumanoidArmorModel(LivingEntity living,ItemStack stack,EquipmentSlot slot,HumanoidModel defaultModel){
					HumanoidModel armorModel=new HumanoidModel(new ModelPart(Collections.emptyList(),Map.of("head",new FoxModel(Minecraft.getInstance().getEntityModels().bakeLayer(FoxModel.LAYER_LOCATION)).Head,"hat",new ModelPart(Collections.emptyList(),Collections.emptyMap()),"body",new ModelPart(Collections.emptyList(),Collections.emptyMap()),"right_arm",new ModelPart(Collections.emptyList(),Collections.emptyMap()),"left_arm",new ModelPart(Collections.emptyList(),Collections.emptyMap()),"right_leg",new ModelPart(Collections.emptyList(),Collections.emptyMap()),"left_leg",new ModelPart(Collections.emptyList(),Collections.emptyMap()))));
					armorModel.crouching=living.isShiftKeyDown();
					armorModel.riding=defaultModel.riding;
					armorModel.young=living.isBaby();
					return armorModel;
				}
			});
		}
		@Override
		public String getArmorTexture(ItemStack stack,Entity entity,EquipmentSlot slot,String type){
			return "sfox:textures/entities/snow_fox_model.png";
		}
		@Override
		public @NotNull InteractionResult useOn(@NotNull UseOnContext context){
			FoxProcedure.place(context.getLevel(),context.getClickedPos().getX(),context.getClickedPos().getY(),context.getClickedPos().getZ(),context.getClickedFace(),context.getPlayer(),context.getItemInHand());
			return InteractionResult.SUCCESS;
		}
		@Override
		public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level,@NotNull Player player,@NotNull InteractionHand hand){
			return this.swapWithEquipmentSlot(this,level,player,hand);
		}
	}
}