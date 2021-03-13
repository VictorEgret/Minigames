package fr.greentor.dev.listeners;

import fr.greentor.dev.managers.GameManager;
import fr.greentor.dev.objects.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Objects;

public class PlayerListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        Player p = (Player) e.getEntity();
        if (e.getEntity() instanceof Player && GameManager.findGameByPlayer(p) != null){
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL){
                if (Objects.requireNonNull(GameManager.findGameByPlayer(p)).isFallDamage()){
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPvP(EntityDamageByEntityEvent e){
        Player damaged = (Player) e.getEntity();
        Player damager = (Player) e.getDamager();

        Game damagedGame = GameManager.findGameByPlayer(damaged);
        Game damagerGame = GameManager.findGameByPlayer(damager);

        if (damagedGame != null || damagerGame != null){
            if (!damagedGame.isPvp() || !damagerGame.isPvp()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player killed = e.getEntity();
        Player killer = e.getEntity().getKiller();

        Game killedGame = GameManager.findGameByPlayer(killed);
        Game killerGame = GameManager.findGameByPlayer(killer);
        if (killer != null) {
            if (killedGame != null || killerGame != null){
                if (!killedGame.isPvp() || !killerGame.isPvp()){
                    if (killerGame.getKillRegen() > 0){
                        killer.setHealth(killer.getHealth() + killerGame.getKillRegen());
                    }
                }
            }
        }
    }
}
