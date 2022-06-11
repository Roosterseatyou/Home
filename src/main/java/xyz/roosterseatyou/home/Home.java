package xyz.roosterseatyou.home;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.roosterseatyou.home.commands.SetHome;

import java.util.logging.Logger;

public final class Home extends JavaPlugin {
    private static Plugin instance;
    private static Logger logger;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        logger = this.getLogger();
        saveResource("data.yml", false);
        getCommand("home").setExecutor(new xyz.roosterseatyou.home.commands.Home());
        getCommand("sethome").setExecutor(new SetHome());
        getServer().getPluginManager().registerEvents(new CombatListeners(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Logger log(){
        return logger;
    }

    public static Plugin getInstance() {
        return instance;
    }
}
