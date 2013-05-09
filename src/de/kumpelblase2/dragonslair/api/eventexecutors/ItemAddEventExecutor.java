package de.kumpelblase2.dragonslair.api.eventexecutors;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import de.kumpelblase2.dragonslair.DragonsLairMain;
import de.kumpelblase2.dragonslair.api.ActiveDungeon;
import de.kumpelblase2.dragonslair.api.Event;

public class ItemAddEventExecutor implements EventExecutor
{
	@Override
	public boolean executeEvent(final Event e, final Player p)
	{
		try
		{
			final String scope = e.getOption("scope");
			Material itemMat = Material.AIR;
			if(e.getOption("item_id").equals("money"))
			{
				if(DragonsLairMain.getInstance().isEconomyEnabled())
				{
					final double amount = Double.parseDouble(e.getOption("amount"));
					final Economy ec = DragonsLairMain.getInstance().getEconomy();
					if(scope == null || scope.equalsIgnoreCase("single"))
						ec.depositPlayer(p.getName(), amount);
					else
					{
						final ActiveDungeon d = DragonsLairMain.getDungeonManager().getDungeonOfPlayer(p.getName());
						if(d != null)
							for(final String member : d.getCurrentParty().getMembers())
								ec.depositPlayer(member, amount);
					}
				}
			}
			else
			{
				if(Material.getMaterial(e.getOption("item_id").replace(" ", "_").toUpperCase()) == null)
					itemMat = Material.getMaterial(Integer.parseInt(e.getOption("item_id")));
				else
					itemMat = Material.getMaterial(e.getOption("item_id").replace(" ", "_").toUpperCase());
				final int amount = Integer.parseInt(e.getOption("amount"));
				final String damageString = e.getOption("damage");
				final short damage = (damageString != null) ? Short.parseShort(damageString) : 0;
				final ItemStack item = new ItemStack(itemMat, amount, damage);
				if(scope == null || scope.equalsIgnoreCase("single"))
					p.getInventory().addItem(item);
				else
				{
					final ActiveDungeon d = DragonsLairMain.getDungeonManager().getDungeonOfPlayer(p.getName());
					if(d != null)
						for(final String member : d.getCurrentParty().getMembers())
							Bukkit.getPlayer(member).getInventory().addItem(item);
				}
			}
		}
		catch(final Exception ex)
		{
			DragonsLairMain.Log.warning("Couldn't execute event with id: " + e.getID());
			DragonsLairMain.Log.warning(ex.getMessage());
			return false;
		}
		return true;
	}
}
