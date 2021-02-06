package com.jn.roadbrush;

import java.util.Set;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EventManager implements Listener {

	@EventHandler
	public void onRightClick(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_AIR || 
				event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player player = event.getPlayer();

			if(Main.getInstance().doesPlayerHaveBrush(player.getUniqueId())) {
				RoadBrush brush = Main.getInstance().getPlayerBrush(player.getUniqueId());
				if(player.getInventory().getItemInMainHand().equals(brush.itemStack)) {
					brush.paint(player.getTargetBlock((Set<Material>) null, 120).getLocation());
				}
			}
		}
	}

}