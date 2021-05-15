package gg.maiko.modsuite;

import gg.maiko.modsuite.command.SuiteCommand;
import gg.maiko.modsuite.handler.ModSuiteHandler;
import gg.maiko.modsuite.listener.SuiteListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Maiko
 * Date: 5/14/2021
 */

@Getter
public class ModSuite extends JavaPlugin {

    @Getter private static ModSuite instance;
    private ModSuiteHandler suiteHandler;

    @Override
    public void onEnable() {
        instance = this;

        suiteHandler = new ModSuiteHandler();
        getCommand("staffmode").setExecutor(new SuiteCommand());
        Bukkit.getPluginManager().registerEvents(new SuiteListener(), this);
        getLogger().info("ModSuite has been enabled.");
    }

    @Override
    public void onDisable() {
        // TODO: Make this better lookin
        for(Player staff : Bukkit.getOnlinePlayers()) {
            if(this.getSuiteHandler().inModMode(staff)) {
                this.getSuiteHandler().toggleStaff(staff);
            }
        }
    }
}
