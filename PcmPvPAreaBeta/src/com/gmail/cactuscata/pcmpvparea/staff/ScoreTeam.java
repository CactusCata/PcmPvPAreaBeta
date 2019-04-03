package com.gmail.cactuscata.pcmpvparea.staff;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreTeam {

	public void createTeam(String NameOfPlayer, Scoreboard board) {
		if (board.getTeam(NameOfPlayer) == null)
			board.registerNewTeam(NameOfPlayer);
	}

	@SuppressWarnings("deprecation")
	public void joinTeam(Player player, Scoreboard board) {

		Team team = board.getTeam(player.getName());
		team.addPlayer(player);

	}

	public void setPrefix(Scoreboard board, Player player, String prefix) {

		Team team = board.getTeam(player.getName());
		team.setPrefix(prefix.substring(0, Math.min(prefix.length(), 16)));

	}

	public void setSuffix(Scoreboard board, Player player, String suffix) {

		Team team = board.getTeam(player.getName());
		team.setSuffix(suffix.substring(0, Math.min(suffix.length(), 16)));

	}

	public String getPrefix(Scoreboard board, Player player) {

		Team team = board.getTeam(player.getName());
		return team.getPrefix();

	}

	public String getSuffix(Scoreboard board, Player player) {

		Team team = board.getTeam(player.getName());
		return team.getSuffix();

	}

	public void deleteTeam(Scoreboard board, Player player) {
		Team team = board.getTeam(player.getName());
		team.unregister();
	}

}
