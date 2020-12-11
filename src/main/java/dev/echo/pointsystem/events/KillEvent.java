package dev.echo.pointsystem.events;

import dev.echo.pointsystem.PointSystem;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Random;

public class KillEvent implements Listener {

    @EventHandler
    public void onKill(PlayerDeathEvent event){

        Player player = event.getEntity().getPlayer();

        Player killer = event.getEntity().getKiller();


        int[] amount = new int[] {0,1,2,3,4,5};


        int random = new Random().nextInt(amount.length);

        player.spigot().respawn();

        PointSystem.getInstance().getFileManager().setPoints(killer, PointSystem.getInstance().getFileManager().getPoints(killer) + random);
        killer.sendMessage(ChatColor.YELLOW + "You have been given "
                + ChatColor.AQUA + ChatColor.BOLD + random + ChatColor.RESET + ChatColor.YELLOW + " points!");
        event.setKeepInventory(false);
        event.setDeathMessage( "");
    }

}
