package play.strictmc.ru.strictdonatemoney.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import static play.strictmc.ru.strictdonatemoney.StrictDonateMoney.inst;
import static play.strictmc.ru.strictdonatemoney.StrictDonateMoney.ppAPI;
import static play.strictmc.ru.strictdonatemoney.utils.messageUtils.getMessage;
import static play.strictmc.ru.strictdonatemoney.utils.messageUtils.getPermission;

public class balanceUtil {
    public static void handleBalance(CommandSender sender, String[] args) {
        OfflinePlayer target;
        if (args.length >=2) {
            target = Bukkit.getOfflinePlayer(args[1]);
            if (!target.hasPlayedBefore() && !target.isOnline()) {
                sender.sendMessage(getMessage("player-not-found",
                        "%player%", args[1]));
                return;
            }
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage(getMessage("console-not-player"));
                return;
            }
            target = (Player) sender;
        }

        int balance = ppAPI.look(target.getUniqueId());
        sender.sendMessage(getMessage("balance", "%player%", target.getName(),
                "%amount%",
                String.valueOf(balance)));

    }

    public static void handlePay(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(getMessage("i-dont-player"));
            return;
        }
        if (args.length < 3) {
            sender.sendMessage(getMessage("low-args-to-pay"));
            return;
        }
        Player from = (Player) sender;
        OfflinePlayer to = Bukkit.getOfflinePlayer(args[1]);
        if (!to.hasPlayedBefore() && !to.isOnline()) {
            sender.sendMessage(getMessage("player-not-found",
                    "%player%", args[1]));
            return;
        }
        int amount;
        try {
            amount = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(getMessage("invalid-number"));
            return;
        }

        if (amount <= 0) {
            sender.sendMessage(getMessage("invalid-amount"));
            return;
        }

        if (ppAPI.look(from.getUniqueId()) < amount) {
            sender.sendMessage(getMessage("not-rubins"));
            return;
        }

        boolean success = ppAPI.pay(from.getUniqueId(), to.getUniqueId(), amount);
        if (success) {
            sender.sendMessage(getMessage("pay-success", "%amount%",
                    String.valueOf(amount), "%target%", to.getName()));
            if (to.isOnline()) {
                ((Player) to).sendMessage(getMessage("pay-received",
                        "%sender%", from.getName(), "%amount%",
                        String.valueOf(amount)));
            }
        } else {
            sender.sendMessage(getMessage("pay-error"));
        }
    }

    public static void handleGive(CommandSender sender, String[] args) {
        if (!sender.hasPermission(getPermission("admin-perms"))) {
            sender.sendMessage(getMessage("no-permission"));
            return;
        }
        if (args.length < 3) {
            sender.sendMessage(getMessage("low-args-to-give"));
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
        if (!target.hasPlayedBefore() && !target.isOnline()) {
            sender.sendMessage(getMessage("player-not-found",
                    "%player%", args[1]));
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(getMessage("invalid-number"));
            return;
        }

        if (amount <=0) {
            sender.sendMessage(getMessage("invalid-amount"));
            return;
        }

        boolean success = ppAPI.give(target.getUniqueId(), amount);
        if (success) {
            sender.sendMessage(getMessage("give-success",
                    "%amount%", String.valueOf(amount),
                    "%target%", target.getName()));
            if (target.isOnline() && inst.getConfig()
                    .getBoolean("notify.target-on-give")) {
                ((Player) target).sendMessage(getMessage("give-received",
                        "%amount%", String.valueOf(amount)));

            }
        } else {
            sender.sendMessage(getMessage(""));
        }
    }

    public static void handleTake(CommandSender sender, String[] args) {
        if (!sender.hasPermission(getPermission("admin-perms"))) {
            sender.sendMessage(getMessage("no-permission"));
            return;
        }
        if (args.length < 3) {
            sender.sendMessage(getMessage("low-args-to-take"));
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
        if (!target.hasPlayedBefore() && !target.isOnline()) {
            sender.sendMessage(getMessage("player-not-found",
                    "%player%", args[1]));
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(getMessage("invalid-number"));
            return;
        }

        if (amount <=0) {
            sender.sendMessage(getMessage("invalid-amount"));
            return;
        }

        if (ppAPI.look(target.getUniqueId()) < amount) {
            sender.sendMessage(getMessage("take-rubin-malo",
                    "%player%", target.getName()));
            return;
        }

        boolean success = ppAPI.take(target.getUniqueId(), amount);
        if (success) {
            sender.sendMessage(getMessage("take-success", "%amount%",
                    String.valueOf(amount), "%target%", target.getName()));
            if (target.isOnline() && inst.getConfig().getBoolean(
                    "notify.target-on-take"
            )) {
                ((Player) target).sendMessage(getMessage("take-taken",
                        "%amount%", String.valueOf(amount)));

            }
        } else {
            sender.sendMessage(getMessage("take-error"));
        }
    }
    public static void handleSet(CommandSender sender, String[] args) {
        if (!sender.hasPermission(getPermission("admin-perms"))) {
            sender.sendMessage(getMessage("no-permission"));
            return;
        }
        if (args.length < 3) {
            sender.sendMessage(getMessage("low-args-to-take"));
            return;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
        if (!target.hasPlayedBefore() && !target.isOnline()) {
            sender.sendMessage(getMessage("player-not-found",
                    "%player%", args[1]));
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(getMessage("invalid-number"));
            return;
        }

        if (amount <=0) {
            sender.sendMessage(getMessage("invalid-amount"));
            return;
        }

        boolean success = ppAPI.set(target.getUniqueId(), amount);
        if (success) {
            sender.sendMessage(getMessage("set-success", "%amount%",
                    String.valueOf(amount), "%target%", target.getName()));
            if (target.isOnline() && inst.getConfig().getBoolean(
                    "notify.target-on-set"
            )) {
                ((Player) target).sendMessage(getMessage("set-seter",
                        "%amount%", String.valueOf(amount)));

            }
        } else {
            sender.sendMessage(getMessage("set-error"));
        }
    }

    public static void handleReload(CommandSender sender) {
        if (!sender.hasPermission(getPermission("admin-perms"))) {
            sender.sendMessage(getMessage("no-permission"));
            return;
        }
        inst.reloadConfig();
        inst.getCommandUtil().reloadCommands();
        FileConfiguration config = inst.getConfig();
        sender.sendMessage(getMessage("reload-success"));
    }
}
