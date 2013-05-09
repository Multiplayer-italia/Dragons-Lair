package de.kumpelblase2.dragonslair.commanddialogs.event;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.conversations.*;
import de.kumpelblase2.dragonslair.DragonsLairMain;
import de.kumpelblase2.dragonslair.api.*;
import de.kumpelblase2.dragonslair.conversation.AnswerConverter;
import de.kumpelblase2.dragonslair.conversation.AnswerType;
import de.kumpelblase2.dragonslair.utilities.GeneralUtilities;

@SuppressWarnings("unchecked")
public class EventCreateDialog extends ValidatingPrompt
{
	@Override
	public String getPromptText(final ConversationContext arg0)
	{
		if(arg0.getSessionData("event_type") == null)
		{
			arg0.getForWhom().sendRawMessage(ChatColor.GREEN + "Please enter the type of the event:");
			final StringBuilder types = new StringBuilder();
			for(final EventActionType type : EventActionType.values())
				types.append(ChatColor.AQUA + type.toString() + ChatColor.WHITE + ", ");
			return types.deleteCharAt(types.length() - 2).toString();
		}
		else if(arg0.getSessionData("add_option") == null)
		{
			if(arg0.getSessionData("event_options") == null)
				arg0.setSessionData("event_options", new ArrayList<Option>());
			final StringBuilder currentOptions = new StringBuilder("Current options:");
			final ArrayList<Option> options = (ArrayList<Option>)arg0.getSessionData("event_options");
			for(int i = 0; i < options.size(); i++)
			{
				currentOptions.append(options.get(i).getAsString());
				if(i != options.size() - 1)
					currentOptions.append("; ");
			}
			return ChatColor.GREEN + "Do you want to add another option to the event?";
		}
		else if((Boolean)arg0.getSessionData("add_option"))
		{
			arg0.getForWhom().sendRawMessage(ChatColor.GREEN + "Please enter the type of the option:");
			return ChatColor.YELLOW + EventActionOptions.valueOf(((String)arg0.getSessionData("event_type")).replace(" ", "_").toUpperCase()).getOptionString();
		}
		else if(arg0.getSessionData("event_option_type") != null)
			return ChatColor.GREEN + "Please enter the value:";
		return null;
	}

	@Override
	protected Prompt acceptValidatedInput(final ConversationContext arg0, final String arg1)
	{
		if(arg1.equals("cancel"))
		{
			arg0.setSessionData("event_type", null);
			arg0.setSessionData("add_option", null);
			arg0.setSessionData("event_options", null);
			arg0.setSessionData("event_option_type", null);
			return new EventManageDialog();
		}
		if(arg0.getSessionData("event_type") == null)
		{
			if(arg1.equals("back"))
				return new EventManageDialog();
			arg0.setSessionData("event_type", arg1.toUpperCase().replace(" ", "_"));
		}
		else if(arg0.getSessionData("add_option") == null)
		{
			if(arg1.equals("back"))
			{
				arg0.setSessionData("event_type", null);
				return this;
			}
			final AnswerType answer = new AnswerConverter(arg1).convert();
			if(answer == AnswerType.AGREEMENT || answer == AnswerType.CONSIDERING_AGREEMENT || answer == AnswerType.CONSIDERING)
				arg0.setSessionData("add_option", true);
			else
			{
				final Event e = this.createEvent(arg0);
				if(e != null)
				{
					DragonsLairMain.debugLog("Created event with id '" + e.getID() + "'");
					arg0.setSessionData("event_type", null);
					arg0.setSessionData("add_option", null);
					arg0.setSessionData("event_options", null);
					arg0.setSessionData("event_option_type", null);
				}
				else
				{
					arg0.getForWhom().sendRawMessage(ChatColor.RED + "Missing required option.");
					arg0.setSessionData("event_type", null);
					return this;
				}
				return new EventManageDialog();
			}
		}
		else if((Boolean)arg0.getSessionData("add_option"))
		{
			if(arg1.equals("back"))
			{
				arg0.setSessionData("add_option", null);
				return this;
			}
			arg0.setSessionData("event_option_type", arg1);
			arg0.setSessionData("add_option", false);
		}
		else
		{
			if(arg1.equals("back"))
			{
				arg0.setSessionData("add_option", true);
				return this;
			}
			final String type = (String)arg0.getSessionData("event_option_type");
			final String value = arg1;
			final Option o = new Option(type, value);
			if(arg0.getSessionData("event_options") == null)
			{
				final ArrayList<Option> options = new ArrayList<Option>();
				options.add(o);
				arg0.setSessionData("event_options", options);
			}
			else
			{
				final ArrayList<Option> options = (ArrayList<Option>)arg0.getSessionData("event_options");
				options.add(o);
				arg0.setSessionData("event_options", options);
			}
			arg0.setSessionData("event_option_type", null);
			arg0.setSessionData("add_option", null);
		}
		return this;
	}

	private Event createEvent(final ConversationContext context)
	{
		final List<Option> options = (ArrayList<Option>)context.getSessionData("event_options");
		for(final String required : EventActionOptions.valueOf(((String)context.getSessionData("event_type"))).getRequiredTypes())
		{
			boolean found = false;
			for(final Option option : options)
				if(option.getType().equalsIgnoreCase(required))
				{
					found = true;
					break;
				}
			if(!found)
				return null;
		}
		final Event e = new Event();
		e.setActionType(EventActionType.valueOf(((String)context.getSessionData("event_type")).replace(" ", "_").toUpperCase()));
		e.setOptions((options).toArray(new Option[0]));
		e.save();
		DragonsLairMain.getSettings().getEvents().put(e.getID(), e);
		return e;
	}

	@Override
	protected boolean isInputValid(final ConversationContext arg0, final String arg1)
	{
		if(arg1.equals("back") || arg1.equals("cancel"))
			return true;
		if(arg0.getSessionData("event_type") == null)
			try
			{
				EventActionType.valueOf(arg1.replace(" ", "_").toUpperCase());
				return true;
			}
			catch(final Exception e)
			{
				arg0.getForWhom().sendRawMessage(ChatColor.RED + "There is no such event type.");
				return false;
			}
		else if(arg0.getSessionData("add_option") == null)
		{
			final AnswerType answer = new AnswerConverter(arg1).convert();
			if(answer != AnswerType.NOTHING)
				return true;
			return false;
		}
		else if((Boolean)arg0.getSessionData("add_option"))
		{
			if(EventActionOptions.valueOf(((String)arg0.getSessionData("event_type")).replace(" ", "_").toUpperCase()).hasOption(arg1))
				return true;
			else
			{
				arg0.getForWhom().sendRawMessage(ChatColor.RED + "This option is not avaiable for this event type.");
				return false;
			}
		}
		else
		{
			final String option = (String)arg0.getSessionData("event_option_type");
			return GeneralUtilities.isValidOptionInput(arg0, arg1, option);
		}
	}
}
