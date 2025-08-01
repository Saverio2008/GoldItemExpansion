package org.saverio.golditemexpansion.forge.event;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import static org.saverio.golditemexpansion.Golditemexpansion.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public final class DelayedTaskManager {

    private static class DelayedTask {
        public int ticksRemaining;
        public Runnable task;

        public DelayedTask(int delayTicks, Runnable task) {
            this.ticksRemaining = delayTicks;
            this.task = task;
        }
    }

    private static final Queue<DelayedTask> tasks = new LinkedList<>();

    public static void scheduleDelayedTask(int delayTicks, Runnable task) {
        tasks.offer(new DelayedTask(delayTicks, task));
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Iterator<DelayedTask> iter = tasks.iterator();
            while (iter.hasNext()) {
                DelayedTask t = iter.next();
                t.ticksRemaining--;
                if (t.ticksRemaining <= 0) {
                    t.task.run();
                    iter.remove();
                }
            }
        }
    }
}