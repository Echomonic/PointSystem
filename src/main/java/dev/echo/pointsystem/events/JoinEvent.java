package dev.echo.pointsystem.events;

import dev.echo.pointsystem.PointSystem;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();

        if(!player.hasPlayedBefore()){

            PointSystem.getInstance().getFileManager().setPoints(player, 0);

        }else{

            PointSystem.getInstance().getFileManager().getPoints(player);

            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou currently have &b&l" +
                    PointSystem.getInstance().getFileManager().getPoints(player)) + ChatColor.RESET + ChatColor.YELLOW  +" points!");
            return;
        }

    }

}
