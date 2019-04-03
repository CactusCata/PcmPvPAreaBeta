package com.gmail.cactuscata.pcmpvparea.staff;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.gmail.cactuscata.pcmpvparea.Main;
import com.gmail.cactuscata.pcmpvparea.api.UUIDFetcher;
import com.gmail.cactuscata.pcmpvparea.commands.vanish.VanishManager;
import com.gmail.cactuscata.pcmpvparea.enums.PrefixMessage;

public class SetStaff implements CommandExecutor {

	private Main main;
	private VanishManager vanishManager;
	private ScoreTeam scoreTeam;

	public SetStaff(Main main, VanishManager vanishManager, ScoreTeam scoreTeam) {
		this.main = main;
		this.vanishManager = vanishManager;
		this.scoreTeam = scoreTeam;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("setstaff")) {

			if (args.length < 1) {
				sender.sendMessage(PrefixMessage.PREFIX + "Veillez préciser le joueur !");
				return true;
			}
			if (args.length < 2) {
				sender.sendMessage(PrefixMessage.PREFIX + "Veillez préciser le grade !");
				return true;
			}

			StaffList staffList = StaffList.getStaff(args[1]);

			if (!staffList.contains(args[1])) {
				sender.sendMessage(PrefixMessage.PREFIX + "Le grade " + args[1] + " n'éxiste pas !");
				return true;
			}

			if (args.length < 3) {
				sender.sendMessage(PrefixMessage.PREFIX + "Il manque le mot de passe !");
				return true;
			}
			if (!(args[2].equalsIgnoreCase("cocacola"))) {
				sender.sendMessage(PrefixMessage.PREFIX + "Mot de passe incorrect !");
				return true;
			}

			String prefix = staffList.getPrefix();
			String suffix = staffList.getSuffix();
			String staff = staffList.getNameOfStaff();

			File file;
			FileConfiguration config;
			Player target = Bukkit.getPlayerExact(args[0]);

			if (target != null && target.isOnline()) {

				ScoreboardManager manager = Bukkit.getScoreboardManager();
				Scoreboard board = manager.getMainScoreboard();
				scoreTeam.deleteTeam(board, target);
				scoreTeam.createTeam(target.getName(), board);
				scoreTeam.joinTeam(target, board);
				scoreTeam.setPrefix(board, target, prefix);
				scoreTeam.setSuffix(board, target, "§6[§3" + target.getWorld().getName() + "§6]");
				target.setDisplayName(prefix + target.getName() + suffix);

				file = new File(main.getDataFolder(), "players/" + target.getUniqueId().toString() + ".yml");
				config = YamlConfiguration.loadConfiguration(file);

				if (sender instanceof Player) {
					Player playersender = (Player) sender;
					target.sendMessage(PrefixMessage.PREFIX + playersender.getDisplayName() + ChatColor.YELLOW
							+ " vous a mis le grade " + staff + ChatColor.YELLOW + " !");
					if (staff.equals("Aucun")) {

						for (Player player : vanishManager.getList())
							target.hidePlayer(player);
					} else {
						for (Player player : vanishManager.getList())
							target.showPlayer(player);

					}

				} else

					target.sendMessage(
							PrefixMessage.PREFIX + "La console vous a mis le grade " + staff + ChatColor.YELLOW + " !");

			} else {

				UUID uuid = UUIDFetcher.getUUIDOf(args[0]);

				try {
					file = new File(main.getDataFolder(), "players/" + uuid.toString() + ".yml");
					config = YamlConfiguration.loadConfiguration(file);
				} catch (NullPointerException e) {
					sender.sendMessage(PrefixMessage.PREFIX + "Le joueur n'existe pas !");
					return true;
				}

			}

			config.set("general.staff", staff);
			config.set("general.prefix", prefix);
			config.set("general.suffix", suffix);

			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}

			sender.sendMessage(PrefixMessage.PREFIX + "Vous avez mis le grade " + staff + " au joueur " + prefix
					+ args[0] + suffix + ChatColor.YELLOW + " !");

			return true;
		}
		return false;
	}

}
