package com.natamus.scaffoldingdropsnearby.neoforge.events;

import com.natamus.collective.functions.WorldFunctions;
import com.natamus.scaffoldingdropsnearby.events.ScaffoldingEvent;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class NeoForgeScaffoldingEvent {
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
