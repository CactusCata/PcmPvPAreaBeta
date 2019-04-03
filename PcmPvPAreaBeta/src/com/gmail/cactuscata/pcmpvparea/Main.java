package com.gmail.cactuscata.pcmpvparea;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.cactuscata.pcmpvparea.commands.other.Broadcast;
import com.gmail.cactuscata.pcmpvparea.commands.other.Fly;
import com.gmail.cactuscata.pcmpvparea.commands.other.Gamemode;
import com.gmail.cactuscata.pcmpvparea.commands.other.Heal;
import com.gmail.cactuscata.pcmpvparea.commands.other.Speed;
import com.gmail.cactuscata.pcmpvparea.commands.other.Tps;
import com.gmail.cactuscata.pcmpvparea.commands.spy.MessageObj;
import com.gmail.cactuscata.pcmpvparea.commands.spy.Msg;
import com.gmail.cactuscata.pcmpvparea.commands.spy.R;
import com.gmail.cactuscata.pcmpvparea.commands.spy.SocialSpy;
import com.gmail.cactuscata.pcmpvparea.commands.spy.SpyList;
import com.gmail.cactuscata.pcmpvparea.commands.tp.Tpb;
import com.gmail.cactuscata.pcmpvparea.commands.tp.Tppos;
import com.gmail.cactuscata.pcmpvparea.commands.vanish.Modo;
import com.gmail.cactuscata.pcmpvparea.commands.vanish.VanishManager;
import com.gmail.cactuscata.pcmpvparea.other.Sign;
import com.gmail.cactuscata.pcmpvparea.other.Weather;
import com.gmail.cactuscata.pcmpvparea.staff.ScoreTeam;
import com.gmail.cactuscata.pcmpvparea.staff.SetStaff;

public class Main extends JavaPlugin {

	public void onEnable() {

		MessageObj msgObj = new MessageObj();
		VanishManager vanishManager = new VanishManager();
		PluginManager pm = getServer().getPluginManager();
		ScoreTeam scoreTeam = new ScoreTeam();

		pm.registerEvents(new Sign(), this);
		pm.registerEvents(new Weather(), this);
		pm.registerEvents(new EventsListener(this, vanishManager), this);
		pm.registerEvents(new PlayerSendMessage(vanishManager), this);

		getCommand("spy").setExecutor(new SocialSpy(msgObj));
		getCommand("msg").setExecutor(new Msg(msgObj, vanishManager));
		getCommand("r").setExecutor(new R(msgObj));
		getCommand("spylist").setExecutor(new SpyList(msgObj));
		getCommand("modo").setExecutor(new Modo(this, vanishManager, scoreTeam));
		getCommand("broadcast").setExecutor(new Broadcast());
		getCommand("setstaff").setExecutor(new SetStaff(this, vanishManager, scoreTeam));
		getCommand("fly").setExecutor(new Fly());
		getCommand("gamemode").setExecutor(new Gamemode());
		getCommand("heal").setExecutor(new Heal());
		getCommand("speed").setExecutor(new Speed());
		getCommand("tps").setExecutor(new Tps(this));
		getCommand("tpb").setExecutor(new Tpb());
		getCommand("tppos").setExecutor(new Tppos());

	}

}
