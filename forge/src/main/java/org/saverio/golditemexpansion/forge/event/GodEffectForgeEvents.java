package org.saverio.golditemexpansion.forge.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.saverio.golditemexpansion.util.GodEffectRemoveSkipManager;

import static org.saverio.golditemexpansion.Golditemexpansion.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class GodEffectForgeEvents {
    @SubscribeEvent
    public static void onUseMilk(LivingEntityUseItemEvent.Finish event) {
        if (event.getItem().is(Items.MILK_BUCKET)) {
            LivingEntity entity = event.getEntity();
            GodEffectRemoveSkipManager.setForgeSkip(entity, true);
            DelayedTaskManager.scheduleDelayedTask(1, () ->
                    GodEffectRemoveSkipManager.setForgeSkip(entity, false));
        }
    }
}
