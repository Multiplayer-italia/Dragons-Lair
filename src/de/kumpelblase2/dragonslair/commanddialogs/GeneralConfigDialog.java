package de.kumpelblase2.dragonslair.commanddialogs;

import org.bukkit.ChatColor;
import org.bukkit.conversations.*;
import de.kumpelblase2.dragonslair.DragonsLairMain;
import de.kumpelblase2.dragonslair.commanddialogs.chapters.ChapterManageDialog;
import de.kumpelblase2.dragonslair.commanddialogs.dialogs.DialogManageDialog;
import de.kumpelblase2.dragonslair.commanddialogs.dungeon.DungeonManageDialog;
import de.kumpelblase2.dragonslair.commanddialogs.event.EventManageDialog;
import de.kumpelblase2.dragonslair.commanddialogs.npc.NPCManageDialog;
import de.kumpelblase2.dragonslair.commanddialogs.objectives.ObjectiveManageDialog;
import de.kumpelblase2.dragonslair.commanddialogs.scheduledevents.ScheduledEventsManageDialog;
import de.kumpelblase2.dragonslair.commanddialogs.trigger.TriggerManageDialog;

public class GeneralConfigDialog extends FixedSetPrompt
{
	public GeneralConfigDialog()
	{
		super("dungeons", "npcs", "events", "triggers", "chapters", "objectives", "dialogs", "scheduled events", "help", "info", "exit");
	}

	@Override
	public String getPromptText(final ConversationContext arg0)
	{
		arg0.getForWhom().sendRawMessage(ChatColor.GREEN + "Welcome to the configuration of Dragons Lair! You can quit this at any point by saying '/exit' in the chat.");
		arg0.getForWhom().sendRawMessage(ChatColor.YELLOW + "Note: While you're in this configuration, you can't chat!");
		arg0.getForWhom().sendRawMessage(ChatColor.GREEN + "What do you want to manage?");
		return ChatColor.AQUA + "dungeons, npcs, events, triggers, chapters, objectives, dialogs, scheduled events, help, info, exit";
	}

	@Override
	protected Prompt acceptValidatedInput(final ConversationContext arg0, final String arg1)
	{
		if(arg1.equals("dungeons"))
			return new DungeonManageDialog();
		else if(arg1.equals("npcs"))
			return new NPCManageDialog();
		else if(arg1.equals("events"))
			return new EventManageDialog();
		else if(arg1.equals("triggers"))
			return new TriggerManageDialog();
		else if(arg1.equals("help"))
			return new HelpDialog();
		else if(arg1.equals("chapters"))
			return new ChapterManageDialog();
		else if(arg1.equals("objectives"))
			return new ObjectiveManageDialog();
		else if(arg1.equals("dialogs"))
			return new DialogManageDialog();
		else if(arg1.equals("scheduled events"))
			return new ScheduledEventsManageDialog();
		else if(arg1.equals("info"))
		{
			arg0.getForWhom().sendRawMessage(ChatColor.GOLD + "----------------------------------------------");
			arg0.getForWhom().sendRawMessage(ChatColor.GOLD + "  Running Dragons Lair v" + DragonsLairMain.getInstance().getDescription().getVersion());
			arg0.getForWhom().sendRawMessage(ChatColor.GOLD + "  Made by kumpelblase2");
			arg0.getForWhom().sendRawMessage(ChatColor.GOLD + "  Have fun ;)");
			arg0.getForWhom().sendRawMessage(ChatColor.GOLD + "----------------------------------------------");
			return this;
		}
		else
			return END_OF_CONVERSATION;
	}
}
