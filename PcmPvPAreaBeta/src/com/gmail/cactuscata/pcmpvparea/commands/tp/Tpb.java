package com.gmail.cactuscata.pcmpvparea.commands.tp;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.cactuscata.pcmpvparea.enums.PrefixMessage;

public class Tpb implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("tpb")) {

			if (args.length < 2) {
				sender.sendMessage(
						PrefixMessage.PREFIX + "Veillez préciser quel joueur doit être téléporté à quel joueur !");
				return true;
			}

			Player player1 = Bukkit.getPlayerExact(args[0]);
			Player player2 = Bukkit.getPlayerExact(args[1]);
			if (player1 == null || !player1.isOnline()) {
				sender.sendMessage(PrefixMessage.PREFIX + "Le joueur " + args[0] + " n'a pas été trouvé");
				return true;
			}
			if (player2 == null || !player2.isOnline()) {
				sender.sendMessage(PrefixMessage.PREFIX + "Le joueur " + args[1] + " n'a pas été trouvé");
				return true;
			}

			player1.teleport(player2);

			sender.sendMessage(PrefixMessage.PREFIX + player1.getDisplayName() + " a été téléporté sur "
					+ player2.getDisplayName() + " !");

			return true;

		}
		return false;
	}

}
