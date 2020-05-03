package me.spthiel.dbc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class TeamUtils {
	
	private static final String TEAMPREFIX = "DBC";
	
	private ScoreboardManager manager;
	private Scoreboard        board;
	
	public TeamUtils() {
		manager = Bukkit.getScoreboardManager();
		board = manager.getMainScoreboard();
	}
	
	public Team createOrGetColorTeam(ChatColor color) {
		String teamname = TEAMPREFIX + "_" + color.name();
		Team t = board.getTeam(teamname);
		if(t == null) {
			t = board.registerNewTeam(teamname);
			t.setDisplayName(teamname);
			t.setColor(color);
		}
		return t;
	}
	
}
