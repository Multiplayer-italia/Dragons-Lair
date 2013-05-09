package de.kumpelblase2.dragonslair;

public final class TableColumns
{
	public final class Chapters
	{
		public static final String ID = "chapter_id";
		public static final String NAME = "chapter_name";
	}
	public final class Dialogs
	{
		public static final String ID = "dialog_id";
		public static final String TEXT = "dialog_text";
		public static final String AGREEMENT_ID = "next_agreement_id";
		public static final String DISAGREEMENT_ID = "next_disagreement_id";
		public static final String CONSIDER_AGREEMENT_ID = "next_consider_agreement_id";
		public static final String CONSIDER_DISAGREEMENT_ID = "next_consider_disagreement_id";
		public static final String CONSIDER_ID = "next_consider_id";
	}
	public final class Dungeons
	{
		public static final String ID = "dungeon_id";
		public static final String NAME = "dungeon_name";
		public static final String STARTING_OBJECTIVE = "dungeon_starting_objective";
		public static final String STARTING_CHAPTER = "dungeon_starting_chapter";
		public static final String STARTING_POSITION = "dungeon_starting_pos";
		public static final String SAFE_WORD = "dungeon_safe_word";
		public static final String MIN_PLAYERS = "dungeon_min_players";
		public static final String MAX_PLAYERS = "dungeon_max_players";
		public static final String START_MESSAGE = "dungeon_start_message";
		public static final String END_MESSAGE = "dungeon_end_message";
		public static final String PARTY_READY_MESSAGE = "dungeon_party_ready_message";
		public static final String BREAKABLE_BLOCKS = "dungeon_blocks_breakable";
	}
	public final class Events
	{
		public static final String ID = "event_id";
		public static final String ACTION_TYPE = "event_action_type";
		public static final String ACTION_OPTIONS = "event_action_options";
		public static final String COOLDOWNS = "event_cooldowns";
	}
	public final class NPCs
	{
		public static final String ID = "npc_id";
		public static final String NAME = "npc_name";
		public static final String SKIN = "npc_skin";
		public static final String LOCATION = "npc_location";
		public static final String HELD_ITEM_ID = "npc_held_item";
		public static final String ARMOR = "npc_armor";
		public static final String SHOULD_SPAWN_AT_BEGINNING = "npc_spawned_from_beginning";
		public static final String INVINCIBLE = "npc_invincible";
		public static final String TYPE = "npc_type";
	}
	public final class Objectives
	{
		public static final String ID = "objective_id";
		public static final String DESCRIPTION = "objective_description";
	}
	public final class Triggers
	{
		public static final String ID = "trigger_id";
		public static final String TYPE = "trigger_type";
		public static final String TYPE_OPTIONS = "trigger_type_options";
		public static final String ACTION_EVENT_ID = "trigger_action_event";
		public static final String COOLDOWNS = "trigger_cooldowns";
	}
	public final class Parties
	{
		public static final String ID = "party_id";
		public static final String MEMBERS = "party_members";
		public static final String OBJECTIVE_ID = "party_objective_id";
		public static final String CHAPTER_ID = "party_chapter_id";
		public static final String DUNGEON_ID = "party_dungeon_id";
	}
	public final class Player_Saves
	{
		public static final String NAME = "player_name";
		public static final String ARMOR = "player_armor";
		public static final String ITEMS = "player_items";
		public static final String HEALTH = "player_health";
		public static final String HUNGER = "player_hunger";
		public static final String LOCATION = "player_location";
		public static final String PARTY_ID = "player_party_id";
	}
	public final class Log
	{
		public static final String DUNGEON_NAME = "dungeon_name";
		public static final String PARTY_ID = "party_id";
		public static final String LOG_TYPE = "log_type";
		public static final String LOCATION = "location";
		public static final String BEFORE_DATA = "before_data";
		public static final String AFTER_DATA = "after_data";
	}
	public final class Scheduled_Events
	{
		public static final String SEVENT_ID = "schedule_id";
		public static final String REPEATING = "repeating";
		public static final String EVENT_IDS = "event_ids";
		public static final String INIT_DELAY = "init_delay";
		public static final String REPEATING_DELAY = "repeating_delay";
		public static final String AUTO_START = "auto_start";
	}
	public final class Death_Locations
	{
		public static final String PLAYER_NAME = "player_name";
		public static final String PARTY_ID = "party_id";
		public static final String DEATH_LOCATION = "death_location";
		public static final String ARMOR = "armor";
		public static final String INVENTORY = "inventory";
	}
}
