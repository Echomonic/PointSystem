package dev.echo.pointsystem.commands;

import dev.echo.pointsystem.PointSystem;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandPoints implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){

            Player player = (Player) sender;

            if(player.hasPermission("points.use")){

                if(args.length == 3){

                    Player target = Bukkit.getPlayer(args[1]);

                    int points = Integer.parseInt(args[2]);

                    try{

                        switch (args[0].toLowerCase()) {


                            case "set":

                                PointSystem.getInstance().getFileManager().setPoints(target.getUniqueId(), points);

                                player.sendMessage(ChatColor.YELLOW + "You gave " + ChatColor.AQUA + ChatColor.BOLD + points + ChatColor.RESET + ChatColor.YELLOW + " to " + target.getName());


                                target.playSound(target.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, 2, 4);


                                target.sendMessage(ChatColor.YELLOW  + "Your points have been set to " + ChatColor.AQUA + ChatColor.BOLD +
                                        PointSystem.getInstance().getFileManager().getPoints(target));
                                spawnFirework(target.getLocation());
                                break;
                            case "add":
                                assert target != null;
                                PointSystem.getInstance().getFileManager().setPoints(target.getUniqueId(),
                                        PointSystem.getInstance().getFileManager().getPoints(target) + points);

                                player.sendMessage(ChatColor.YELLOW + "You gave " + ChatColor.AQUA + ChatColor.BOLD + points + ChatColor.RESET +  ChatColor.YELLOW + " to " + target.getName());

                                target.sendMessage(ChatColor.YELLOW + "You have received " + ChatColor.AQUA + ChatColor.BOLD + points + ChatColor.RESET + ChatColor.YELLOW + " from " + sender.getName());

                                target.playSound(target.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, 2, 4);

                                target.sendMessage(ChatColor.YELLOW  + "You now have " + ChatColor.AQUA + ChatColor.BOLD +
                                        PointSystem.getInstance().getFileManager().getPoints(target) + ChatColor.RESET + ChatColor.YELLOW + " points");
                                spawnFirework(target.getLocation());
                                break;
                        }

                    } catch (IllegalArgumentException ex) {
                        return true;
                    }

                }else if (args.length == 2){

                    Player target = Bukkit.getPlayer(args[1]);
                    switch (args[0].toLowerCase()){

                        case "get" :

                            player.sendMessage(ChatColor.YELLOW + target.getName() + " has " + ChatColor.AQUA + ChatColor.BOLD +
                                    PointSystem.getInstance().getFileManager().getPoints(target) + ChatColor.RESET + ChatColor.YELLOW + " points");

                            break;

                    }


                }



            }else{
                player.sendMessage(ChatColor.RED + "No Permission!");
                return true;
            }

        }else{
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        List<String> arguments = new ArrayList<>();

        if(command.getName().equals("points")){

            Player player = (Player) sender;

            if(player.hasPermission("points.use")) {
                if(args.length == 1) {
                    arguments.addAll(Arrays.asList("set", "add", "get"));
                }

                if(args.length == 2){
                    for(Player on : Bukkit.getOnlinePlayers()){
                        arguments.addAll(Arrays.asList(on.getName()));
                    }
                }
            }else{

                player.sendMessage(ChatColor.RED +  "No permission!");
            }

        }else{
        }
        return arguments;
    }
    public void spawnFirework(Location location) {

        Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();

        FireworkEffect.Type type = FireworkEffect.Type.BALL;

        FireworkEffect effect = FireworkEffect.builder().with(type).withColor(Color.AQUA).build();

        fireworkMeta.addEffect(effect);

        fireworkMeta.setPower(0);



        firework.setFireworkMeta(fireworkMeta);
        firework.setSilent(true);

        location.setY(location.getY() +1.5f);


        new BukkitRunnable(){


            @Override
            public void run() {

                firework.detonate();

            }
        }.runTaskLater(PointSystem.getInstance(), 4-5);
    }

}
