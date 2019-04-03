package com.gmail.cactuscata.pcmpvparea;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.gmail.cactuscata.pcmpvparea.commands.vanish.VanishManager;

public class PlayerSendMessage implements Listener {

	private VanishManager vanishManag;

	public PlayerSendMessage(VanishManager vanishManag) {
		this.vanishManag = vanishManag;
	}

	@EventHandler
	public void SendMessage(AsyncPlayerChatEvent event) {

		Player player = event.getPlayer();
		event.setCancelled(true);

		String message = event.getMessage();

		if (player.hasPermission("pcm.msg.color"))
			message = message.replace('&', '§');

		for (Player players : player.getWorld().getPlayers())
			players.sendMessage(player.getDisplayName() + message);

		for (Player players : vanishManag.getList())
			if (players.getWorld() != player.getWorld())
				players.sendMessage(player.getWorld().getName() + player.getDisplayName() + message);
	}

}
