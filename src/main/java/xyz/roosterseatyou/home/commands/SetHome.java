package xyz.roosterseatyou.home.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class SetHome implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            setHome(p);

        }
        return false;
    }

    public static void setHome(Player p) {
        YamlConfiguration data = YamlConfiguration.loadConfiguration(new File(xyz.roosterseatyou.home.Home.getInstance().getDataFolder().getPath(), "data.yml"));
        if(data.get(p.getUniqueId().toString()) == null) {
            //player doesn't have a section in that data
            data.createSection(p.getUniqueId().toString() + ".home.coords");
            data.createSection(p.getUniqueId().toString() + ".home.dimension");
        }

        data.set(p.getUniqueId() + ".home.coords", p.getLocation());
        String[] worldNameArr = p.getWorld().getName().split("_");
        if(worldNameArr.length == 1) data.set(p.getUniqueId() + ".home.dimension", "overworld");
        else data.set(p.getUniqueId() + ".home.dimension", worldNameArr[worldNameArr.length-1]);
        p.sendMessage(Component.text("Successfully updated home!"));
    }
}
