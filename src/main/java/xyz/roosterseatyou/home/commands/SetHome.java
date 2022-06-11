package xyz.roosterseatyou.home.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class SetHome implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            setHome(p);
        }
        return true;
    }

    public static void setHome(Player p) {
        YamlConfiguration data = YamlConfiguration.loadConfiguration(new File(xyz.roosterseatyou.home.Home.getInstance().getDataFolder().getPath(), "data.yml"));
        data.set(p.getUniqueId() + ".home.coords", p.getLocation());
        String[] worldNameArr = p.getWorld().getName().split("_");
        if(worldNameArr.length == 1) data.set(p.getUniqueId() + ".home.dimension", "overworld");
        else data.set(p.getUniqueId() + ".home.dimension", worldNameArr[worldNameArr.length-1]);
        try {
            data.save(new File(xyz.roosterseatyou.home.Home.getInstance().getDataFolder().getPath(), "data.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.sendMessage(Component.text("Successfully updated home!"));
    }
}
