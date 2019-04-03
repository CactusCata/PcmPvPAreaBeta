package com.gmail.cactuscata.pcmpvparea.commands.vanish;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.cactuscata.pcmpvparea.Main;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class VanishManager {

	private ArrayList<Player> vanished = new ArrayList<Player>();
	private int taskId;

	public ArrayList<Player> getList() {
		return vanished;
	}

	public boolean isVanished(Player player) {
		if (vanished.contains(player))
			return true;
		return false;

	}

	public void actionMessageVanish(Player playersender, Main main) {
		new BukkitRunnable() {

			public void run() {
				if (VanishManager.this.vanished.contains(playersender)) {

					IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer
							.a("{\"text\": \"Vous êtes maintenant en Vanish\"}");
					PacketPlayOutChat packetPlayOutChat = new PacketPlayOutChat(cbc, (byte) 2);
					((CraftPlayer) playersender).getHandle().playerConnection.sendPacket(packetPlayOutChat);
				} else {
					stopSchreduler();
				}
			}
		}.runTaskTimer(main, 0L, 40L);
	}

	private void stopSchreduler() {
		Bukkit.getServer().getScheduler().cancelTask(this.taskId);
	}

}