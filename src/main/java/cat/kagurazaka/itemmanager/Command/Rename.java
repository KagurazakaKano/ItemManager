package cat.kagurazaka.itemmanager.Command;

import land.melon.lab.simplelanguageloader.components.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class Rename implements SubCommandExecutor, SubTabCompleter {
    @Override
    public boolean invokeCommand(CommandSender commandSender, Command command, String label, String[] args) {
        Player sender = (Player) commandSender;
        ItemStack itemInHand = sender.getInventory().getItemInMainHand();
        if (itemInHand.getType().isAir()) {
            sender.sendMessage("nothing in main hand!");
            return true;
        }
        String rawName = String.join(" ", Arrays.asList(args).subList(1, args.length));
        String name = Text.of(rawName).produce();
        ItemMeta itemMeta = itemInHand.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(name);
            itemInHand.setItemMeta(itemMeta);
        }

        sender.getInventory().setItemInMainHand(itemInHand);
        return true;
    }

    @Override
    public String getHelp() {
        return null;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

    @Override
    public boolean checkPermission(CommandSender commandSender) {
        return false;
    }
}
