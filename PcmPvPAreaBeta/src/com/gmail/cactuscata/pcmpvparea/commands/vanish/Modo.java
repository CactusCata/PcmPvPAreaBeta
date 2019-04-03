package com.gmail.cactuscata.pcmpvparea.commands.vanish;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.gmail.cactuscata.pcmpvparea.Main;
import com.gmail.cactuscata.pcmpvparea.enums.PrefixMessage;
import com.gmail.cactuscata.pcmpvparea.staff.ScoreTeam;

public class Modo implements CommandExecutor {

	private Main main;
	private VanishManager vanishManager;
	private ScoreTeam scoreTeam;

	public Modo(Main main, VanishManager vanishManager, ScoreTeam scoreTeam) {
		this.main = main;
		this.vanishManager = vanishManager;
		this.scoreTeam = scoreTeam;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("vanish")) {

			if (!(sender instanceof Player)) {

				sender.sendMessage(PrefixMessage.PREFIX_SENDER_BE_PLAYER + "");
				return true;
			}

			Player playersender = (Player) sender;
			File file;
			FileConfiguration config = null;

			if (vanishManager.getList().contains(playersender)) {

				for (Player playergetter : Bukkit.getOnlinePlayers())
					playergetter.showPlayer(playersender);

				file = new File(main.getDataFolder(), "players/" + playersender.getUniqueId() + ".yml");
				config = YamlConfiguration.loadConfiguration(file);

				playersender.sendMessage(PrefixMessage.PREFIX_VANISH + "Vous n'êtes plus en vanish");
				playersender.setDisplayName(
						config.get("general.prefix") + playersender.getName() + config.get("general.suffix"));
				playersender.setPlayerListName(playersender.getDisplayName());
				if (playersender.getGameMode() == GameMode.SURVIVAL || playersender.getGameMode() == GameMode.ADVENTURE)
					playersender.setAllowFlight(false);
				playersender.removePotionEffect(PotionEffectType.NIGHT_VISION);

				vanishManager.getList().remove(playersender);

				return true;
				// <-- Avant ça c'est quand le joueur est en Vanish et qu'on
				// l'enleve du Vanish
			} else {

				for (Player playergetter : Bukkit.getOnlinePlayers()) {

					file = new File(main.getDataFolder(), "players/" + playergetter.getUniqueId() + ".yml");
					config = YamlConfiguration.loadConfiguration(file);

					if (config.get("general.staff").equals("Aucun"))
						playergetter.hidePlayer(playersender);

				}

				playersender.sendMessage(PrefixMessage.PREFIX_VANISH + "Vous êtes maintenant en vanish");
				ScoreboardManager manager = Bukkit.getScoreboardManager();
				Scoreboard board = manager.getMainScoreboard();
				scoreTeam.setSuffix(board, playersender, "§e[§fVanish§e]");
				playersender.setDisplayName(playersender.getDisplayName() + "§f[Vanish]");

				playersender.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 0, true));
				playersender.setGameMode(GameMode.CREATIVE);
				playersender.setAllowFlight(true);
				vanishManager.getList().add(playersender);

				return true;

			}

		}

		return false;
	}

}
