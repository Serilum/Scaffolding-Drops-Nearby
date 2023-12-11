package com.natamus.scaffoldingdropsnearby.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ScaffoldingBlockItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ScaffoldingEvent {
	private static final CopyOnWriteArrayList<BlockPos> lastScaffoldings = new CopyOnWriteArrayList<BlockPos>();
	private static final HashMap<BlockPos, Date> lastaction = new HashMap<BlockPos, Date>();
	
	public static void onScaffoldingItem(Level world, Entity entity) {
		if (world.isClientSide) {
			return;
		}
		
		if (!(entity instanceof ItemEntity)) {
			return;	
		}
		
		ItemEntity ie = (ItemEntity)entity;
		ItemStack itemstack = ie.getItem();
		if (!(itemstack.getItem() instanceof ScaffoldingBlockItem)) {
			return;
		}
		
		Date now = new Date();
		
		BlockPos scafpos = entity.blockPosition();
		BlockPos lowscafpos = new BlockPos(scafpos.getX(), 1, scafpos.getZ());
		for (BlockPos lspos : lastScaffoldings) {
			if (lastaction.containsKey(lspos)) {
				Date lastdate = lastaction.get(lspos);
				long ms = (now.getTime()-lastdate.getTime());
				if (ms > 2000) {
					lastScaffoldings.remove(lspos);
					lastaction.remove(lspos);
					continue;
				}			
			}
			
			if (lowscafpos.closerThan(new BlockPos(lspos.getX(), 1, lspos.getZ()), 20)) {
				entity.teleportTo(lspos.getX(), lspos.getY()+1, lspos.getZ());
				lastaction.put(lspos.immutable(), now);
			}
		}
	}
	
	public static void onBlockBreak(Level world, Player player, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		if (world.isClientSide) {
			return;
		}
		
		Block block = state.getBlock();
		if (block.equals(Blocks.SCAFFOLDING)) {
			lastScaffoldings.add(pos);
			lastaction.put(pos, new Date());
		}
	}
}
