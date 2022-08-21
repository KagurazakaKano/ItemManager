package cat.kagurazaka.itemmanager;

import cat.kagurazaka.itemmanager.Command.Commands;
import org.bukkit.plugin.java.JavaPlugin;

public final class ItemManager extends JavaPlugin {

    public static ItemManager plugin;

    @Override
    public void onEnable() {
        plugin = this;
        plugin.getCommand("im").setExecutor(new Commands());
        plugin.getServer().getPluginCommand("im").setTabCompleter(new Commands());
        plugin.getServer().getLogger().info("Plugin has enabled successfully!");
    }

    @Override
    public void onDisable() {
        plugin.getServer().getLogger().info("Plugin has disabled successfully!");
    }

}
