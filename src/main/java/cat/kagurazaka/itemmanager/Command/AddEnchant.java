package cat.kagurazaka.itemmanager.Command;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class AddEnchant implements SubCommandExecutor, SubTabCompleter {

    @Override
    public boolean invokeCommand(CommandSender commandSender, Command command, String label, String[] args) {
        Player p = (Player) commandSender;
        final ItemStack stack = p.getInventory().getItemInMainHand();
        if (stack == null || stack.getType() == Material.AIR) {
            p.sendMessage("You have to hold an item in main hand!");
            return true;
        }

        int encLevel = 1;

        if (args.length == 3) {
            ItemMeta itemMeta = stack.getItemMeta();
            if (itemMeta != null) {
                try {
                    encLevel = Integer.parseInt(args[2]);
                    itemMeta.addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(args[1])), encLevel, true);
                    stack.setItemMeta(itemMeta);
                } catch (Exception e) {
                    p.sendMessage("Error occurred when execute the command!");
                }
            }

            p.getInventory().setItemInMainHand(stack);
            return true;

        } else if (args.length == 2) {
            ItemMeta itemMeta = stack.getItemMeta();
            if (itemMeta != null) {
                try {
                    itemMeta.addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(args[1])), encLevel, true);
                    stack.setItemMeta(itemMeta);
                } catch (Exception e) {
                    p.sendMessage("Error occurred when execute the command!");
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