package gg.maiko.modsuite;

import gg.maiko.modsuite.command.FreezeCommand;
import gg.maiko.modsuite.command.SuiteCommand;
import gg.maiko.modsuite.command.UnFreezeCommand;
import gg.maiko.modsuite.command.VanishCommand;
import gg.maiko.modsuite.handler.ModSuiteHandler;
import gg.maiko.modsuite.listener.FreezeListener;
import gg.maiko.modsuite.listener.SuiteListener;
import gg.maiko.modsuite.listener.VanishListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

/**
 * Created by Maiko
 * Date: 5/14/2021
 */

@Getter
public class ModSuite extends JavaPlugin {

    @Getter private static ModSuite instance;

    @Override
    public void onEnable() {
        instance = this;

        getCommand("staffmode").setExecutor(new SuiteCommand());
        getCommand("freeze").setExecutor(new FreezeCommand());
        getCommand("unfreeze").setExecutor(new UnFreezeCommand());
        getCommand("vanish").setExecutor(new VanishCommand());

        registerListener();

        getLogger().info("ModSuite has been enabled.");
    }


    @Override
    public void onDisable() {
        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
            if(ModSuiteHandler.inModMode(online))
                ModSuiteHandler.getStaffMode().get(online).disableStaff(online);
        }
    }

    private void registerListener() {
        Arrays.asList(
                new SuiteListener(),
                new FreezeListener(),
                new VanishListener()
        ).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }
}
