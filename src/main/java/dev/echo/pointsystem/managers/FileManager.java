package dev.echo.pointsystem.managers;

import dev.echo.pointsystem.PointSystem;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileManager implements Listener {

    private File file;
    private YamlConfiguration configuration;

    private YamlConfiguration configYaml;


    private File config;

    private int points;

    public FileManager(PointSystem core){

        file = new File(core.getDataFolder(), "pointData.yml");

        config = new File(core.getDataFolder(), "config.yml");

        if(!config.exists()){
            core.saveResource(config.getName(), false);
        }

        if(!file.exists()){

            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!core.getDataFolder().exists()){
            core.getDataFolder().mkdir();
        }

        configuration = YamlConfiguration.loadConfiguration(file);
        configYaml = YamlConfiguration.loadConfiguration(config);

    }

    public int getPoints(Player player) {
        return configuration.getInt(player.getUniqueId().toString());
    }

    public void setPoints(UUID uuid , int points) {
        configuration.set(uuid.toString(), points);
        save();
    }

    public void setPoints(Player player , int points) {
        configuration.set(player.getUniqueId().toString(), points);

        save();
    }
    private void save(){
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getConfig() {
        return config;
    }

    public YamlConfiguration getConfigYaml() {
        return configYaml;
    }
}
