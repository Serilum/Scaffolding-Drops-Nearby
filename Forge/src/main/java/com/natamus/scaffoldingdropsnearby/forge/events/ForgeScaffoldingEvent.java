package com.natamus.scaffoldingdropsnearby.forge.events;

import com.natamus.collective.functions.WorldFunctions;
import com.natamus.scaffoldingdropsnearby.events.ScaffoldingEvent;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

import java.lang.invoke.MethodHandles;

public class ForgeScaffoldingEvent {
	public static void registerEventsInBus() {
		BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgeScaffoldingEvent.class);
	}

	@SubscribeEvent
	public static void onScaffoldingItem(EntityJoinLevelEvent e) {
		ScaffoldingEvent.onScaffoldingItem(e.getLevel(), e.getEntity());
	}
	
	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent e) {
		Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
		if (level == null) {
			return;
		}

		ScaffoldingEvent.onBlockBreak(level, e.getPlayer(), e.getPos(), e.getState(), null);
	}
}
