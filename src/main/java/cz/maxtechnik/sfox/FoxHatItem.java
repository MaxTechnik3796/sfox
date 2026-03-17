package cz.maxtechnik.sfox;

import cz.maxtechnik.sfox.client.model.FoxModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class FoxHatItem extends ArmorItem {
    private final String texture;
    private final String foxType;

    public FoxHatItem(Rarity rarity, String texture, String foxType) {
        super(new ArmorMaterial() {
            @Override public int getDurabilityForType(@NotNull Type t) { return 0; }
            @Override public int getDefenseForType(@NotNull Type t) { return t == Type.HELMET ? 2 : 0; }
            @Override public int getEnchantmentValue() { return 0; }
            @Override public @NotNull SoundEvent getEquipSound() { return Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(ResourceLocation.fromNamespaceAndPath("minecraft", "entity.fox.ambient"))); }
            @Override public @NotNull Ingredient getRepairIngredient() { return Ingredient.of(); }
            @Override public @NotNull String getName() { return "fox_armor"; }
            @Override public float getToughness() { return 0f; }
            @Override public float getKnockbackResistance() { return 0f; }
        }, Type.HELMET, new Properties().fireResistant().rarity(rarity));
        this.texture = texture;
        this.foxType = foxType;
    }

    public String getFoxType() {
        return foxType;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return this.texture;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel<?> defaultModel) {
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
        });
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        FoxProcedure.place(context.getLevel(), context.getClickedPos(), context.getClickedFace(), context.getPlayer(), context.getItemInHand());
        return InteractionResult.SUCCESS;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        return this.swapWithEquipmentSlot(this, level, player, hand);
    }
}