package com.jn.roadbrush;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
	
	// TODO: Add edit history
	
	private static Main instance = null;
	private Map<UUID, RoadBrush> roadBrushes = new HashMap<>();

	@Override
	public void onEnable() 
	{
		instance = this;

		getCommand("roadbrush").setExecutor(new Commands());
		Bukkit.getServer().getPluginManager().registerEvents(new EventManager(), instance);
	}

	public static Main getInstance() {
		return instance;
	}

	public boolean doesPlayerHaveBrush(UUID uuid) {
		return getRoadBrushes().containsKey(uuid);
	}

	public RoadBrush getPlayerBrush(UUID uuid) {
		return getRoadBrushes().get(uuid);
	}

	public Map<UUID, RoadBrush> getRoadBrushes() {
		return roadBrushes;
	}

	public void setRoadBrushes(Map<UUID, RoadBrush> roadBrushes) {
		this.roadBrushes = roadBrushes;
	}

}
