package de.kumpelblase2.dragonslair.commanddialogs.dungeon;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import de.kumpelblase2.dragonslair.DragonsLairMain;
import de.kumpelblase2.dragonslair.api.Dungeon;
import de.kumpelblase2.dragonslair.conversation.AnswerConverter;
import de.kumpelblase2.dragonslair.conversation.AnswerType;
import de.kumpelblase2.dragonslair.utilities.WorldUtility;

public class DungeonCreateDialog extends ValidatingPrompt
{
	@Override
	public String getPromptText(ConversationContext context)
	{
		if(context.getSessionData("dungeon_name") == null)
		{
			return ChatColor.GREEN + "Please enter a name for the dungeon:";
		}
		else if(context.getSessionData("starting_objective") == null)
		{
			return ChatColor.GREEN + "Please enter the id of the first objective:";
		}
		else if(context.getSessionData("starting_chapter") == null)
		{
			return ChatColor.GREEN + "Please enter the id of the starting chapter:";
		}
		else if(context.getSessionData("starting_pos") == null)
		{
			return ChatColor.GREEN + "Please specify the starting position. You can insert 'here' to use the current location.";
		}
		else if(context.getSessionData("safe_word") == null)
		{
			return ChatColor.GREEN + "Please enter a safe word to leave the dungeon:";
		}
		else if(context.getSessionData("min_players") == null)
		{
			return ChatColor.GREEN + "Please enter the minimum amount of players to start the dungeon:";
		}
		else if(context.getSessionData("max_players") == null)
		{
			return ChatColor.GREEN + "Please specify the maximum amount of players to a be able to play at the same time, 0 if infinite:";
		}
		else if(context.getSessionData("starting_message") == null)
		{
			return ChatColor.GREEN + "Please enter a message that should be displayed when the dungeon starts:";
		}
		else if(context.getSessionData("end_message") == null)
		{
			return ChatColor.GREEN + "Please enter a message that should be displayed when the dungeon ends:";
		}
		else if(context.getSessionData("party_ready_message") == null)
		{
			return ChatColor.GREEN + "Please enter a message that should be displayed when registered players can start:";
		}
		else
		{
			return ChatColor.GREEN + "Should blocks be breakable in the dungeon?";
		}
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, String input)
	{
		if(input.equals("cancel"))
		{
			context.setSessionData("dungeon_name", null);
			context.setSessionData("starting_objective", null);
			context.setSessionData("starting_chapter", null);
			context.setSessionData("starting_pos", null);
			context.setSessionData("safe_word", null);
			context.setSessionData("min_players", null);
			context.setSessionData("max_players", null);
			context.setSessionData("starting_message", null);
			context.setSessionData("end_message", null);
			context.setSessionData("party_ready_message", null);
			return new DungeonManageDialog();
		}
		
		if(context.getSessionData("dungeon_name") == null)
		{
			if(input.equals("back"))
				return new DungeonManageDialog();
			
			context.setSessionData("dungeon_name", input);
		}
		else if(context.getSessionData("starting_objective") == null)
		{
			if(input.equals("back"))
			{
				context.setSessionData("dungeon_name", null);
				return this;
			}
			
			context.setSessionData("starting_objective", Integer.parseInt(input));
		}
		else if(context.getSessionData("starting_chapter") == null)
		{
			if(input.equals("back"))
			{
				context.setSessionData("starting_objective", null);
				return this;
			}
			
			context.setSessionData("starting_chapter", Integer.parseInt(input));
		}
		else if(context.getSessionData("starting_pos") == null)
		{
			if(input.equals("back"))
			{
				context.setSessionData("starting_chapter", null);
				return this;
			}
			
			if(input.equals("here"))
			{
				Player p = (Player)context.getForWhom();
				context.setSessionData("starting_pos", p.getLocation());
			}
			else
			{
				context.setSessionData("starting_pos", WorldUtility.stringToLocation(input));
			}
		}
		else if(context.getSessionData("safe_word") == null)
		{
			if(input.equals("back"))
			{
				context.setSessionData("starting_pos", null);
				return this;
			}
			
			context.setSessionData("safe_word", input);
		}
		else if(context.getSessionData("min_players") == null)
		{
			if(input.equals("back"))
			{
				context.setSessionData("safe_word", null);
				return this;
			}
			
			context.setSessionData("min_players", Integer.parseInt(input));
		}
		else if(context.getSessionData("max_players") == null)
		{
			if(input.equals("back"))
			{
				context.setSessionData("min_players", null);
				return this;
			}
			
			context.setSessionData("max_players", Integer.parseInt(input));
		}
		else if(context.getSessionData("starting_message") == null)
		{
			if(input.equals("back"))
			{
				context.setSessionData("max_players", null);
				return this;
			}
			
			context.setSessionData("starting_message", input);
		}
		else if(context.getSessionData("end_message") == null)
		{
			if(input.equals("back"))
			{
				context.setSessionData("starting_message", null);
				return this;
			}
			
			context.setSessionData("end_message", input);
		}
		else if(context.getSessionData("party_ready_mesage") == null)
		{
			if(input.equals("back"))
			{
				context.setSessionData("end_message", null);
				return this;
			}
			
			context.setSessionData("party_ready_message", input);
		}
		else
		{
			String name = (String)context.getSessionData("dungeon_name");
			int startObject = (Integer)context.getSessionData("starting_objective");
			int startChapter = (Integer)context.getSessionData("starting_chapter");
			Location startingPos = (Location)context.getSessionData("starting_pos");
			String safeWord = (String)context.getSessionData("safe_word");
			int minPlayers = (Integer)context.getSessionData("min_players");
			int maxPlayers = (Integer)context.getSessionData("max_players");
			String startingMessage = (String)context.getSessionData("starting_message");
			String endMessage = (String)context.getSessionData("end_message");
			String readyMessage = (String)context.getSessionData("party_ready_message");
			AnswerType answer = new AnswerConverter(input).convert();
			boolean blocksBreakable = (answer == AnswerType.AGREEMENT || answer == AnswerType.CONSIDERING_AGREEMENT);
			Dungeon d = new Dungeon();
			d.setName(name);
			d.setStartingObjective(startObject);
			d.setStartingChapter(startChapter);
			d.setStartingLocation(startingPos);
			d.setSafeWord(safeWord);
			d.setMinPlayers(minPlayers);
			d.setMaxPlayers(maxPlayers);
			d.setStartingMessage(startingMessage);
			d.setEndMessage(endMessage);
			d.setPartyReadyMessage(readyMessage);
			d.setBlocksBreakable(blocksBreakable);
			d.save();
			DragonsLairMain.getSettings().getDungeons().put(d.getID(), d);
			context.getForWhom().sendRawMessage(ChatColor.GREEN + "The dungeon is '" + context.getSessionData("dungeon_name") + "' created!");
			
			context.setSessionData("dungeon_name", null);
			context.setSessionData("starting_objective", null);
			context.setSessionData("starting_chapter", null);
			context.setSessionData("starting_pos", null);
			context.setSessionData("safe_word", null);
			context.setSessionData("min_players", null);
			context.setSessionData("max_players", null);
			context.setSessionData("starting_message", null);
			context.setSessionData("end_message", null);
			context.setSessionData("party_ready_message", null);
			return new DungeonManageDialog();
		}
		return this;
	}

	@Override
	protected boolean isInputValid(ConversationContext context, String input)
	{	
		if(input.equals("back") || input.equals("cancel"))
			return true;
		
		if(context.getSessionData("dungeon_name") == null)
		{
			if(DragonsLairMain.getSettings().getDungeonByName(input) == null)
				return true;
			else
			{
				context.getForWhom().sendRawMessage(ChatColor.RED + "A dungeon with that name already exists.");
				return false;
			}
		}
		else if(context.getSessionData("starting_objective") == null)
		{
			try
			{
				int i = Integer.parseInt(input);
				if(DragonsLairMain.getSettings().getObjectives().get(i) == null)
				{
					context.getForWhom().sendRawMessage(ChatColor.RED + "The objective does not exist.");
					return false;
				}
				else
					return true;
			}
			catch(Exception e)
			{
				context.getForWhom().sendRawMessage(ChatColor.RED + "It's not a number.");
				return false;
			}
		}
		else if(context.getSessionData("starting_chapter") == null)
		{
			try
			{
				int i = Integer.parseInt(input);
				if(DragonsLairMain.getSettings().getChapters().get(i) == null)
				{
					context.getForWhom().sendRawMessage(ChatColor.RED + "The chapter does not exist.");
					return false;
				}
				else
					return true;
			}
			catch(Exception e)
			{
				context.getForWhom().sendRawMessage(ChatColor.RED + "It's not a number.");
				return false;
			}	
		}
		else if(context.getSessionData("starting_pos") == null)
		{
			if(!input.equals("here"))
			{
				if(WorldUtility.stringToLocation(input) == null)
				{
					context.getForWhom().sendRawMessage(ChatColor.RED + "Invalid location data.");
					return false;
				}
				else
					return true;
			}
		}
		else if(context.getSessionData("safe_word") == null)
		{
			if(input.length() == 0)
				return false;
			
			return true;
		}
		else if(context.getSessionData("min_players") == null)
		{
			try
			{
				Integer.parseInt(input);
				return true;
			}
			catch(Exception e)
			{
				context.getForWhom().sendRawMessage(ChatColor.RED + "It's not a number.");
				return false;
			}
		}
		else if(context.getSessionData("max_players") == null)
		{
			try
			{
				Integer.parseInt(input);
			}
			catch(Exception e)
			{
				context.getForWhom().sendRawMessage(ChatColor.RED + "It's not a number.");
				return false;
			}
		}
		if(context.getSessionData("party_ready_message") != null)
		{
			AnswerType answer = new AnswerConverter(input).convert();
			return answer != AnswerType.NOTHING;
		}
		
		return true;
	}
}
