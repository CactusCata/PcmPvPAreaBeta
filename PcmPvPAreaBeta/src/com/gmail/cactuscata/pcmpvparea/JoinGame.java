package com.gmail.cactuscata.pcmpvparea;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.gmail.cactuscata.pcmpvparea.commands.vanish.VanishManager;
import com.gmail.cactuscata.pcmpvparea.enums.PrefixMessage;
import com.gmail.cactuscata.pcmpvparea.staff.ScoreTeam;

public class JoinGame implements Listener {

	private Main main;
	private VanishManager vanishManager;
	private ScoreTeam scoreTeam;

	public JoinGame(Main main, VanishManager vanishManager, ScoreTeam scoreTeam) {
		this.main = main;
		this.vanishManager = vanishManager;
		this.scoreTeam = scoreTeam;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) throws IOException {

		Player player = event.getPlayer();
		event.setJoinMessage(null);

		File file = new File(main.getDataFolder(), "players/" + player.getUniqueId() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		if (!file.exists()) {
			config.set("general.pseudo", player.getName());
			config.set("general.staff", "Aucun");
			config.set("general.prefix", "§e");
			config.set("general.suffix", "§f");
			config.save(file);

			Bukkit.broadcastMessage(
					PrefixMessage.PREFIX + player.getName() + " est nouveau sur le PvPArea, veuillez lui faire bon acceuil !");
		}

		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();
		scoreTeam.createTeam(player.getName(), board);
		scoreTeam.joinTeam(player, board);
		scoreTeam.setPrefix(board, player, config.getString("general.prefix") + player.getName());
		scoreTeam.setSuffix(board, player, "§6[§3" + player.getWorld().getName() + "§6]");
		player.setDisplayName(config.getString("general.prefix") + player.getName());

		if (config.getString("general.staff").equals("Aucun"))
			for (Player playerVanished : vanishManager.getList())
				player.hidePlayer(playerVanished);

		player.sendMessage(PrefixMessage.PREFIX + "Bienvenue à toi " + player.getName() + " sur le monde PvPAréa !");
		player.teleport(new Location(Bukkit.getWorld("Spawn"), -0.5d, 65.5d, -0.5d, 0.0f, 10.0f));
		player.setGameMode(GameMode.ADVENTURE);
		player.setTotalExperience(0);
		player.setExp(0.0f);
		player.setFoodLevel(20);
		player.setHealth(20.0d);
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
		player.getInventory().clear();
		player.updateInventory();

	}

}
