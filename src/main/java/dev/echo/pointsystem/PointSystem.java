package dev.echo.pointsystem;

import dev.echo.pointsystem.commands.CommandPoints;
import dev.echo.pointsystem.events.JoinEvent;
import dev.echo.pointsystem.events.KillEvent;
import dev.echo.pointsystem.managers.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PointSystem extends JavaPlugin {

    private FileManager fileManager;

    @Override
    public void onEnable() {

        this.fileManager = new FileManager(this);

        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new KillEvent(), this);
        getCommand("points").setExecutor(new CommandPoints());
        getCommand("points").setTabCompleter(new CommandPoints());
    }

    @Override
    public void onDisable() {

    }

    public static PointSystem getInstance(){
        return PointSystem.getPlugin(PointSystem.class);
    }

    public FileManager getFileManager() {
        return fileManager;
    }
}
