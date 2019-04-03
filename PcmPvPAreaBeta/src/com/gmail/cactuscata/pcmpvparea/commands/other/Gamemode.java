package com.gmail.cactuscata.pcmpvparea.commands.other;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.cactuscata.pcmpvparea.enums.PrefixMessage;

public class Gamemode implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("gamemode") || cmd.getName().equalsIgnoreCase("gm")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage(PrefixMessage.PREFIX_SENDER_BE_PLAYER + "");
				return true;
			}

			if (args.length < 1) {
				sender.sendMessage(PrefixMessage.PREFIX + "Vous devez indiquer le gamemode !");
				return true;
			}

			Player player = (Player) sender;
			int value;

			try {
				value = Integer.parseInt(args[0]);

			} catch (NumberFormatException e) {
				player.sendMessage(PrefixMessage.PREFIX
						+ "La commande doit s'effectuer sous la forme: §a/gamemode §6<§cnombre§6> §b!");
				return true;
			}

			if (value < 0 || value > 3) {

				player.sendMessage(PrefixMessage.PREFIX + "La valeur du gamemode doit être entre 0 et 3");
				return true;

			}

			GameMode[] gamemode = { GameMode.SURVIVAL, GameMode.CREATIVE, GameMode.ADVENTURE, GameMode.SPECTATOR };

			if (player.hasPermission("pcm.pvparea.gamemode." + gamemode[value].toString().toLowerCase())) {

				player.setGameMode(gamemode[value]);
				player.sendMessage(PrefixMessage.PREFIX + "Vous avez été mis en " + gamemode[value].toString().toLowerCase() + " !");
				return true;
			}

			player.sendMessage(PrefixMessage.PREFIX + "Vous n'avez pas la permission d'utiliser le gamemode "
					+ gamemode[value].toString().toLowerCase());
			return true;

		}
		return false;
	}

}
