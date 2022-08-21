package cat.kagurazaka.itemmanager.Command;

import land.melon.lab.simplelanguageloader.components.Text;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.format.Style;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetLore implements SubCommandExecutor, SubTabCompleter {

    @Override
    public boolean invokeCommand(CommandSender commandSender, Command command, String label, String[] args) {
        Player p = (Player) commandSender;
        final ItemStack stack = p.getInventory().getItemInMainHand();
        if (stack == null || stack.getType() == Material.AIR) {
            p.sendMessage("You have to hold an item in main hand!");
            return true;
        }

        if (args.length < 2) {
            p.sendMessage("miss line number", getHelp());
            return true;
        }

        int lineInd;
        try {
            lineInd = Integer.parseInt(args[1]);
            if (lineInd >= 32) {
                p.sendMessage("invalid line number: too large");
                return true;
            }
        } catch (NumberFormatException e) {
            p.sendMessage("invalid line number");
            return true;
        }
        lineInd--; // index start from 0

        ItemMeta itemMeta = stack.getItemMeta();
        if (itemMeta != null) {
            List<String> lore;
            if (itemMeta.hasLore()) {
                lore = itemMeta.getLore();
            } else {
                lore = new ArrayList<>();
            }
            if (lore.size() <= lineInd) {
                for (int i = lore.size(); i < lineInd; ++i) {
                    lore.add("");
                }
                lore.add(null);
            }
            String rawLine = String.join(" ", Arrays.asList(args).subList(2, args.length));
            String line = Text.of(rawLine).produce();
            lore.set(lineInd, line);
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