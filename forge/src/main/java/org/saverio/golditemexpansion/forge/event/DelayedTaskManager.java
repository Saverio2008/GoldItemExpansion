package org.saverio.golditemexpansion.forge.event;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import static org.saverio.golditemexpansion.Golditemexpansion.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public final class DelayedTaskManager {
    private static final Logger LOGGER = LogManager.getLogger();

    private static class DelayedTask {
        public long executeAtTick;
        public Runnable task;

        public DelayedTask(long executeAtTick, Runnable task) {
            this.executeAtTick = executeAtTick;
            this.task = task;
        }
    }

    private static final Queue<DelayedTask> tasks = new LinkedList<>();

    public static void scheduleDelayedTask(ServerLevel world, int delayTicks, Runnable task) {
        long targetTick = world.getGameTime() + delayTicks;
        tasks.offer(new DelayedTask(targetTick, task));
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (tasks.isEmpty()) return;

            MinecraftServer server = event.getServer();
            if (server == null) return;

            // 获取主世界（这里假设是OVERWORLD）
            ServerLevel overworld = server.getLevel(net.minecraft.world.level.Level.OVERWORLD);
            if (overworld == null) return;

            long currentTick = overworld.getGameTime();

            Iterator<DelayedTask> iter = tasks.iterator();
            while (iter.hasNext()) {
                DelayedTask task = iter.next();
                if (currentTick >= task.executeAtTick) {
                    try {
                        task.task.run();
                    } catch (Throwable e) {
                        LOGGER.error("Error running task", e);
                    }
                    iter.remove();
                }
            }
        }
    }
}