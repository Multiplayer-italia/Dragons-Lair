package de.kumpelblase2.dragonslair.api;

import java.sql.*;
import de.kumpelblase2.dragonslair.*;

public class Chapter
{
	private int id;
	private String name;

	public Chapter()
	{
		this.id = -1;
	}

	public Chapter(final ResultSet result)
	{
		try
		{
			this.id = result.getInt(TableColumns.Chapters.ID);
			this.name = result.getString(TableColumns.Chapters.NAME);
		}
		catch(final SQLException e)
		{
			e.printStackTrace();
		}
	}

	public int getID()
	{
		return this.id;
	}

	public void setName(final String newname)
	{
		this.name = newname;
	}

	public String getName()
	{
		return this.name;
	}

	public void save()
	{
		try
		{
			if(this.id != -1)
			{
				final PreparedStatement st = DragonsLairMain.createStatement("REPLACE INTO " + Tables.CHAPTERS + "(" + TableColumns.Chapters.ID + "," + TableColumns.Chapters.NAME + ") VALUES(?,?)");
				st.setInt(1, this.id);
				st.setString(2, this.name);
				st.execute();
			}
			else
			{
				final PreparedStatement st = DragonsLairMain.createStatement("INSERT INTO " + Tables.CHAPTERS + "(" + TableColumns.Chapters.NAME + ") VALUES(?)");
				st.setString(1, this.name);
				st.execute();
				final ResultSet keys = st.getGeneratedKeys();
				if(keys.next())
					this.id = keys.getInt(1);
			}
		}
		catch(final Exception e)
		{
			DragonsLairMain.Log.warning("Unable to save chapter " + this.id);
			DragonsLairMain.Log.warning(e.getMessage());
		}
	}

	public void remove()
	{
		try
		{
			final PreparedStatement st = DragonsLairMain.createStatement("REMOVE FROM " + Tables.CHAPTERS + " WHERE `" + TableColumns.Chapters.ID + "` = ?");
			st.setInt(1, this.id);
			st.execute();
		}
		catch(final Exception e)
		{
			DragonsLairMain.Log.warning("Unable to remove chapter " + this.id);
			DragonsLairMain.Log.warning(e.getMessage());
		}
	}
}
