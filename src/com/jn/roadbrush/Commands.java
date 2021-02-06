package com.jn.roadbrush;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor, TabCompleter
{

	/*
	 * /roadbrush [radius] [whitelisted_blocks]
	 * Example: /roadbrush 5 stone,stone_brick grass_block
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
	{
		if(sender instanceof Player) 
		{
			Player player = (Player) sender;
			
			if(!player.hasPermission("roadbrush.brush")) 
			{
				player.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
				return false;
			}
			if(args.length == 3) 
			{
				int diameter = 5;

				try 
				{
					diameter = Integer.parseInt(args[0]);
				}
				catch(NumberFormatException e) 
				{
					player.sendMessage(ChatColor.RED + "Please enter an appropiate radius!");
					return false;
				}

				ArrayList<Material> matList = new ArrayList<>();
				ArrayList<Material> matWhitelist = new ArrayList<>();

				String blockList = args[1];
				matList = stringToBlockMaterialList(blockList);
				
				String blockWhitelist = args[2];
				matWhitelist = stringToBlockMaterialList(blockWhitelist);

				if(matList == null) 
				{
					player.sendMessage(ChatColor.RED + "Your road list is invalid!");
					return false;
				}
				if(matWhitelist == null)
				{
					player.sendMessage(ChatColor.RED + "Your whitelist is invalid!");
					return false;
				}

				if(player.getInventory().getItemInMainHand().getType() != Material.AIR)
				{
					Main.getInstance().getRoadBrushes().put(player.getUniqueId(),
							new RoadBrush(player.getInventory().getItemInMainHand(), diameter, matList, matWhitelist));
					player.sendMessage(ChatColor.GREEN + "Brush applied successfully!");
				}
				else
				{
					player.sendMessage(ChatColor.RED + "Please select an item to convert into a brush!");
					return false;
				}
			}
			else
			{
				player.sendMessage(ChatColor.RED + "[USAGE] /roadbrush [radius] [block_list] [whitelist]");
				return false;
			}
		}
		else {
			sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
			return false;
		}

		return true;
	}

	//TODO: Get this to work with each entry in comma-separated list in later update.
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) 
	{
		List<String> list = new ArrayList<>();
		if(args.length == 2) 
		{
			// list available blocks

			String argList = args[1];

			// if(args[1].endsWith(",")) argList = "";
			
			for(Material mat : Material.values()) 
				if(mat.isBlock() && mat.isSolid() 
						&& mat.toString().startsWith(argList.toUpperCase())) 
					list.add(mat.toString().toLowerCase());
		}
		return list;
	}

	private ArrayList<Material> stringToBlockMaterialList(String string) 
	{
		String[] split = string.split(",");
		ArrayList<Material> materialList = new ArrayList<>();

		for(String matString : split)
		{
			Material mat = Material.getMaterial(matString.toUpperCase());

			if(mat != null && mat.isBlock() && mat.isSolid()) 
				materialList.add(mat);
			else
				return null;
		}

		return materialList;
	}
}
