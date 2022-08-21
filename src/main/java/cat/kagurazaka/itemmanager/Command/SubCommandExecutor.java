package cat.kagurazaka.itemmanager.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface SubCommandExecutor {
    boolean invokeCommand(CommandSender commandSender, Command command, String label, String[] args);

    String getHelp();
}
