package com.gmail.cactuscata.pcmpvparea;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.gmail.cactuscata.pcmpvparea.commands.spy.MessageObj;
import com.gmail.cactuscata.pcmpvparea.commands.vanish.VanishManager;
import com.gmail.cactuscata.pcmpvparea.staff.ScoreTeam;

public class LeaveGame implements Listener {

	private MessageObj messageObj;
	private VanishManager vanishManager;
	private ScoreTeam scoreTeam;

	public LeaveGame(MessageObj messageObj, VanishManager vanishManager, ScoreTeam scoreTeam) {
		this.messageObj = messageObj;
		this.vanishManager = vanishManager;
		this.scoreTeam = scoreTeam;
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {

		Player player = event.getPlayer();

		event.setQuitMessage("");
		if (messageObj.getSocial().contains(player.getName()))
			messageObj.getSocial().remove(player.getName());

		if (vanishManager.getList().contains(player)){
			player.removePotionEffect(PotionEffectType.NIGHT_VISION);
			vanishManager.getList().remove(player);
		}

		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();
		scoreTeam.deleteTeam(board, player);

	}

}
