package cat.kagurazaka.itemmanager.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface SubTabCompleter {

    List<String> tabComplete(CommandSender sender, Command command, String alias, String[] args);

    boolean checkPermission(CommandSender commandSender);
}