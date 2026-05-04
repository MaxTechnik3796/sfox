package cz.maxtechnik.sfox;

import cz.maxtechnik.sfox.client.model.FoxModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class FoxHatItem extends ArmorItem {
    private final String foxType;
    private HumanoidModel<?> armorModel = null;

    public FoxHatItem(Holder<ArmorMaterial> material, Properties properties, String foxType) {
        super(material, Type.HELMET, properties);
        this.foxType = foxType;
    }

    public String getFoxType() {
        return foxType;
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