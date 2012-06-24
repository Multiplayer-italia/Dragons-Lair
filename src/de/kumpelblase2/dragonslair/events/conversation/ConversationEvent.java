package de.kumpelblase2.dragonslair.events.conversation;

import org.bukkit.conversations.Conversation;
import com.topcat.npclib.entity.HumanNPC;
import de.kumpelblase2.dragonslair.DragonsLairMain;
import de.kumpelblase2.dragonslair.api.NPC;
import de.kumpelblase2.dragonslair.events.BaseEvent;

public class ConversationEvent extends BaseEvent
{
	protected NPC npc;
	protected HumanNPC hnpc;
	protected Conversation conv;
	
	public ConversationEvent(String inName, Conversation inConv)
	{
		this.npc = DragonsLairMain.getSettings().getNPCByName(inName);
		this.hnpc = DragonsLairMain.getInstance().getDungeonManager().getNPCByName(inName);
		this.conv = inConv;
	}
	
	public NPC getNPC()
	{
		return this.npc;
	}
	
	public HumanNPC getNPCEntity()
	{
		return this.hnpc;
	}
	
	public Conversation getConversation()
	{
		return this.conv;
	}
}