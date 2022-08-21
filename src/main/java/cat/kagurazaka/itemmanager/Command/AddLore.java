package cat.kagurazaka.itemmanager.Command;

import land.melon.lab.simplelanguageloader.components.Text;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddLore implements SubCommandExecutor, SubTabCompleter {

    @Override
    public boolean invokeCommand(CommandSender commandSender, Command command, String label, String[] args) {
        Player p = (Player) commandSender;
        final ItemStack stack = p.getInventory().getItemInMainHand();
        if (stack == null || stack.getType() == Material.AIR) {
            p.sendMessage("You have to hold an item in main hand!");
            return true;
        }

        ItemMeta itemMeta = stack.getItemMeta();
        if (itemMeta != null) {
            List<String> lore;
            if (itemMeta.hasLore()) {
                lore = itemMeta.getLore();
            } else {
                lore = new ArrayList<>();
            }
            String rawLine = String.join(" ", Arrays.asList(args).subList(1, args.length));
            String line = Text.of(rawLine).produce();
            lore.add(line);
            itemMeta.setLore(lore);
        }

        return true;
    }

    @Override
    public String getHelp() {
        return "";
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
