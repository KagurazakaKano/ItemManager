package cat.kagurazaka.itemmanager.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Commands implements CommandExecutor, TabExecutor {

    private AddEnchant addEnchant;
    private RemoveEnchant removeEnchant;
    private Rename rename;
    private AddDestroyable addDestroyable;
    private RemoveDestroyable removeDestroyable;
    private AddPlaceable addPlaceable;
    private RemovePlaceable removePlaceable;
    private AddLore addLore;
    private RemoveLore removeLore;
    private SetLore setLore;

    public Commands() {
        this.addEnchant = new AddEnchant();
        this.removeEnchant = new RemoveEnchant();
        this.rename = new Rename();
        this.addDestroyable = new AddDestroyable();
        this.removeDestroyable = new RemoveDestroyable();
        this.addPlaceable = new AddPlaceable();
        this.removePlaceable = new RemovePlaceable();
        this.addLore = new AddLore();
        this.removeLore = new RemoveLore();
        this.setLore = new SetLore();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length > 0) {
                var subCommand = SubCommands.valueOf(args[0].toUpperCase());
                switch (subCommand) {
                    case ADDENCHANT -> invokeCommand(addEnchant, sender, command, label, args);
                    case REMOVEENCHANT -> invokeCommand(removeEnchant, sender, command, label, args);
                    case RENAME -> invokeCommand(rename, sender, command, label, args);
                    case ADDDESTROYABLE -> invokeCommand(addDestroyable, sender, command, label, args);
                    case REMOVEDESTROYABLE -> invokeCommand(removeDestroyable, sender, command, label, args);
                    case ADDPLACEABLE -> invokeCommand(addPlaceable, sender, command, label, args);
                    case REMOVEPLACEABLE -> invokeCommand(removePlaceable, sender, command, label, args);
                    case ADDLORE -> invokeCommand(addLore, sender, command, label, args);
                    case REMOVELORE -> invokeCommand(removeLore, sender, command, label, args);
                    case SETLORE -> invokeCommand(setLore, sender, command, label, args);
                    default -> sender.sendMessage("Invalid subcommand: " + args[0]);
                }
            }
        } else {
            sender.sendMessage("You have to be player to execute that command!");
        }
        return true;
    }

    private void invokeCommand(SubCommandExecutor subCommandExecutor, CommandSender sender, Command command, String label, String[] args) {
        if (!subCommandExecutor.invokeCommand(sender, command, label, args)) {
            sender.sendMessage(subCommandExecutor.getHelp());
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        var subCommandList = Arrays.stream(SubCommands.values()).filter(t -> hasPermission(sender)).map((v) -> v.name().toLowerCase()).toList();
        List<String> completeList = new ArrayList<>();
        if (args.length < 2) {
            return subCommandList.stream().filter(t -> t.startsWith(args[0].toLowerCase())).toList();
        } else {
            try {
                var argsTruncated = Arrays.copyOfRange(args, 1, args.length);
                var subCommand = SubCommands.valueOf(args[0].toUpperCase());
                if (!hasPermission(sender)) return completeList;
                switch (subCommand) {
                    case ADDENCHANT -> completeList = addEnchant.tabComplete(sender, command, label, argsTruncated);
                    case REMOVEENCHANT ->
                            completeList = removeEnchant.tabComplete(sender, command, label, argsTruncated);
                    case RENAME -> completeList = rename.tabComplete(sender, command, label, argsTruncated);
                    case ADDDESTROYABLE ->
                            completeList = addDestroyable.tabComplete(sender, command, label, argsTruncated);
                    case REMOVEDESTROYABLE ->
                            completeList = removeDestroyable.tabComplete(sender, command, label, argsTruncated);
                    case ADDPLACEABLE -> completeList = addPlaceable.tabComplete(sender, command, label, argsTruncated);
                    case REMOVEPLACEABLE ->
                            completeList = removePlaceable.tabComplete(sender, command, label, argsTruncated);
                    case ADDLORE -> completeList = addLore.tabComplete(sender, command, label, argsTruncated);
                    case REMOVELORE -> completeList = removeLore.tabComplete(sender, command, label, argsTruncated);
                    case SETLORE -> completeList = setLore.tabComplete(sender, command, label, argsTruncated);
                }
            } catch (IllegalArgumentException ignore) {
            }
        }
        return completeList;
    }

    private boolean hasPermission(CommandSender sender) {
        return sender.hasPermission("  itemmanager.op");
    }

    enum SubCommands {
        ADDENCHANT,
        REMOVEENCHANT,
        ADDDESTROYABLE,
        RENAME,
        REMOVEDESTROYABLE,
        ADDPLACEABLE,
        REMOVEPLACEABLE,
        ADDLORE,
        REMOVELORE,
        SETLORE,
    }
}