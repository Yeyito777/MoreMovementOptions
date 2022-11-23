package net.yeyito.Items;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.logging.Logger;

public class DashingItem extends Item {

    public DashingItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        playerEntity.playSound(SoundEvents.BLOCK_WOOL_BREAK, 1.0F, 1.0F);
        if (this.getName().getString().equals("item.more_movement_options.dash_left")) {playerEntity.updateVelocity(2,new Vec3d(15,0,0));} else
            if (this.getName().getString().equals("item.more_movement_options.dash_right")) playerEntity.updateVelocity(2,new Vec3d(-15,0,0)); else
                if (this.getName().getString().equals("item.more_movement_options.dash_forward")) {playerEntity.updateVelocity(1,new Vec3d(0,0,15));} else
                    if (this.getName().getString().equals("item.more_movement_options.dash_backward")) {playerEntity.updateVelocity(2,new Vec3d(0,0,-15));}
        return TypedActionResult.success(playerEntity.getStackInHand(hand));
    }


}