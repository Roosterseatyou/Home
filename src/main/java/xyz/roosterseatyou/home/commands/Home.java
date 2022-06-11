package xyz.roosterseatyou.home.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.roosterseatyou.home.CombatListeners;

import java.io.File;
import java.util.Objects;

public class Home implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            String dim = null;
            if(args.length != 0) {
                if(args[0].equals("overworld")) {
                    dim = "";
                } else {
                    dim = args[0];
                }
            }
            if (checkPlayer(p, dim)) {
                try {
                    p.teleport(getPlayerHome(p));
                    p.sendMessage(Component.text("Successfully teleported home!"));
                } catch (NullPointerException e) {
                    p.sendMessage(Component.text("Internal error occurred!"));
                    return false;
                }
            } else {
                p.sendMessage(Component.text("We both know you cannot escape this danger. You are not allowed to teleport home!"));
            }
        }
        return true;
    }

    public static boolean checkPlayer(Player p, String dimension) {
        if(p.getFallDistance() >= 8) return false;
        if(p.getLocation().getBlock().getType().equals(Material.LAVA)) return false;
        if(CombatListeners.getTimeLeft(p) >= 1) return false;
        if(Objects.equals(dimension, "")) {
            return p.getWorld().getName().equals("world");
        }
        System.out.println(p.getWorld().getName());
        System.out.println("_"+dimension);
        System.out.println(p.getWorld().getName().endsWith("_"+dimension));

        if(!p.getWorld().getName().endsWith("_"+dimension)) return false;
        return true;
    }

    public static Location getPlayerHome(Player p) {
        YamlConfiguration data = YamlConfiguration.loadConfiguration(new File(xyz.roosterseatyou.home.Home.getInstance().getDataFolder().getPath(), "data.yml"));
        Location loc;
        try {
            loc = data.getLocation(p.getUniqueId() + ".home.coords");
        } catch (NullPointerException e) {
            xyz.roosterseatyou.home.Home.log().severe("Error getting coords for player " + p.getUniqueId());
            return null;
        }
        return loc;
    }
}
