package com.natamus.scaffoldingdropsnearby;

import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.check.ShouldLoadCheck;
import com.natamus.scaffoldingdropsnearby.events.ScaffoldingEvent;
import com.natamus.scaffoldingdropsnearby.util.Reference;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public class ModFabric implements ModInitializer {
	
	@Override
	public void onInitialize() {
		if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
			return;
		}

		setGlobalConstants();
		ModCommon.init();

		loadEvents();

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadEvents() {
		PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, entity) -> {
			ScaffoldingEvent.onBlockBreak(world, player, pos, state, entity);
		});

		ServerEntityEvents.ENTITY_LOAD.register((Entity entity, ServerLevel world) -> {
			ScaffoldingEvent.onScaffoldingItem(world, entity);
		});
	}

	private static void setGlobalConstants() {

	}
}
