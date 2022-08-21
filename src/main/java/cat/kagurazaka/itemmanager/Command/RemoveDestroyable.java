package cat.kagurazaka.itemmanager.Command;

import com.destroystokyo.paper.Namespaced;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Set;

public class RemoveDestroyable implements SubCommandExecutor, SubTabCompleter {
    @Override
    public boolean invokeCommand(CommandSender commandSender, Command command, String label, String[] args) {
        Player p = (Player) commandSender;
        final ItemStack stack = p.getInventory().getItemInMainHand();
        if (stack == null || stack.getType() == Material.AIR) {
            p.sendMessage("You have to hold an item in main hand!");
            return true;
        }

        if (args.length == 2) {
            ItemMeta itemMeta = stack.getItemMeta();
            if (itemMeta != null) {
                try {
                    Set<Namespaced> collection = itemMeta.getDestroyableKeys();
                    if (collection.remove(NamespacedKey.minecraft(args[1])) == false) {
                        p.sendMessage("No available item found!");
                    }
                    itemMeta.setDestroyableKeys(collection);
                    stack.setItemMeta(itemMeta);
                } catch (Exception e) {
                    p.sendMessage("Please check again!");
                }
            }

            p.getInventory().setItemInMainHand(stack);
            return true;

        } else {
            commandSender.sendMessage("/im addenchant [enchantmentName] [level]");
            return true;
        }
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
