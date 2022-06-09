package xyz.roosterseatyou.home;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Home extends JavaPlugin {
    private static Plugin instance;
    private static Logger logger;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        logger = this.getLogger();

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
