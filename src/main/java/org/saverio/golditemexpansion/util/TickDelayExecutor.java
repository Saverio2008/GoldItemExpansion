package org.saverio.golditemexpansion.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

import java.util.*;

public class TickDelayExecutor {
    private static final Map<MinecraftServer, List<ScheduledTask>> TASKS = new HashMap<>();

    public static void runLater(MinecraftServer server, int delayTicks, Runnable task) {
        TASKS.computeIfAbsent(server, s -> {
            ServerTickEvents.END_SERVER_TICK.register(TickDelayExecutor::tick);
            return new ArrayList<>();
        }).add(new ScheduledTask(delayTicks, task));
    }

    private static void tick(MinecraftServer server) {
        List<ScheduledTask> list = TASKS.get(server);
        if (list == null) return;

        Iterator<ScheduledTask> iter = list.iterator();
        while (iter.hasNext()) {
            ScheduledTask task = iter.next();
            task.ticksLeft--;
            if (task.ticksLeft <= 0) {
                task.runnable.run();
                iter.remove();
            }
        }
    }

    private static class ScheduledTask {
        int ticksLeft;
        Runnable runnable;
        ScheduledTask(int ticks, Runnable runnable) {
            this.ticksLeft = ticks;
            this.runnable = runnable;
        }
    }
}
