package play.strictmc.ru.strictdonatemoney.cmdsTC;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static play.strictmc.ru.strictdonatemoney.utils.messageUtils.getPermission;

public class rubinTC implements TabCompleter {

    private final List<String> subCommands = Arrays.asList("balance",
            "pay",
            "give",
            "take",
            "reload",
            "set");
    private final List<String> scPlayer = Arrays.asList("balance", "pay");

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender,
                                                @NotNull Command command,
                                                @NotNull String alias,
                                                @NotNull String[] args) {

        if (args.length == 1) {
            if (!sender.hasPermission(getPermission("admin-perms"))) {
                return scPlayer.stream()
                        .filter(cmd -> cmd.startsWith(args[0].toLowerCase()))
                        .collect(Collectors.toList());
            } else  {
                return subCommands.stream()
                        .filter(cmd -> cmd.startsWith(args[0].toLowerCase()))
                        .collect(Collectors.toList());
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("reload")) {
                return null;
            }
            String sub = args[0].toLowerCase();
            if (subCommands.contains(sub)) {
                return Bukkit.getOnlinePlayers().stream()
                        .map(Player::getName)
                        .filter(name -> name.toLowerCase().startsWith(
                                args[1].toLowerCase()))
                        .collect(Collectors.toList());
            }
        } else if (args.length == 3) {
            String sub = args[0].toLowerCase();
            if (sub.equals("pay") || sub.equals("give") ||
                    sub.equals("take") || sub.equals("set")) {
                List<String> list = new ArrayList<>();
                list.add("<сумма>");
                return list;
            }
        }

        return new ArrayList<>();
    }
}
