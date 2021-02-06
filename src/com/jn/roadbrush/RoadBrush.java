package com.jn.roadbrush;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class RoadBrush 
{

	ItemStack itemStack;
	int radius;
	List<Material> pattern = new ArrayList<>();
	List<Material> whitelist = new ArrayList<>();

	public RoadBrush(ItemStack itemStack, int radius, List<Material> pattern, List<Material> whitelist) 
	{
		this.itemStack = itemStack;
		this.radius = radius;
		this.pattern = pattern;
		this.whitelist = whitelist;
	}

	public void paint(Location location) 
	{
		// Bukkit.broadcastMessage("paint " + getTopBlock(block.getLocation()).getType().toString());
		List<Location> circle = getCircle(location);

		World world = location.getWorld();
		for(Location circLoc : circle) 
		{
			Random random = new Random();
			Material generated = pattern.get(random.nextInt(pattern.size()));

			Block block = world.getHighestBlockAt(circLoc);
			if(block.getType() != Material.AIR && block.getType().isSolid()) 
			{
				if(!whitelist.contains(block.getType()))
				{
					for(int y = block.getY(); y > 0; y--)
					{
						block = world.getBlockAt(block.getLocation().subtract(0, 1, 0));

						if(block.getType() != Material.AIR && whitelist.contains(block.getType()))
							break;
					}
				}

				block.setType(generated);
			}
		}
	}

	private List<Location> getCircle(Location center) 
	{
		int a = center.getBlockX();
		final int y = center.getBlockY();
		int b = center.getBlockZ();

		List<Location> locList = new ArrayList<>();
		double radAndAHalf = radius + 0.5;
		int ceilRad = (int) Math.ceil(radAndAHalf);

		for(int x = a - ceilRad; x < a + ceilRad; x++) {
			for(int z = b - ceilRad; z < b + ceilRad; z++) {
				Location loc = new Location(center.getWorld(), x, y, z);
				double realRadius = loc.distance(center);
				if(realRadius <= radius + 0.5)
					locList.add(loc);
			}
		}

		return locList;
	}

}