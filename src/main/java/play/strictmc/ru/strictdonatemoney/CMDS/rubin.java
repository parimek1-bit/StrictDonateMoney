package play.strictmc.ru.strictdonatemoney.CMDS;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static play.strictmc.ru.strictdonatemoney.utils.balanceUtil.*;
import static play.strictmc.ru.strictdonatemoney.utils.messageUtils.sendHelp;

public class rubin implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "balance":
                handleBalance(sender, args);
                break;

            case "pay":
                handlePay(sender, args);
                break;

            case "give":
                handleGive(sender, args);
                break;

            case "take":
                handleTake(sender, args);
                break;

            case "set":
                handleSet(sender, args);
                break;

            case "reload":
                handleReload(sender);
                break;

            default:
                sendHelp(sender);
                break;
        }

        return true;
    }
}