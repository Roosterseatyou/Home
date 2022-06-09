package xyz.roosterseatyou.home;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CombatListeners implements Listener {
    private static final Map<UUID, Integer> map = new HashMap<>();

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            map.put(p.getUniqueId(), 20);
            Bukkit.getScheduler().scheduleSyncRepeatingTask(Home.getInstance(), () -> {
                if(map.get(p.getUniqueId()) == null || map.get(p.getUniqueId()) <= 0) return;
                int timeLeft = map.get(p.getUniqueId());
                timeLeft--;
                map.put(p.getUniqueId(), timeLeft);
            }, 20, 20);
        }
    }

    public static int getTimeLeft(Player p) {
        if(map.get(p.getUniqueId()) == null) return 0;
        int timeLeft = map.get(p.getUniqueId());
        return timeLeft;
    }

    public static Map<UUID, Integer> getMap() {
        return map;
    }
}
