package play.strictmc.ru.strictdonatemoney.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import play.strictmc.ru.strictdonatemoney.CMDS.rubin;
import play.strictmc.ru.strictdonatemoney.cmdsTC.rubinTC;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class commandUtil {

    private final JavaPlugin plugin;
    private final CommandMap commandMap;
    private final List<Command> registeredCommands = new ArrayList<>();

    public commandUtil(JavaPlugin plugin) {
        this.plugin = plugin;
        this.commandMap = getCommandMap();
    }

    private CommandMap getCommandMap() {
        try {
            Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            return (CommandMap) f.get(Bukkit.getServer());
        } catch (Exception e) {
            plugin.getLogger().severe("Не удалось получить CommandMap! " +
                    "Плагин будет работать некорректно.");
            e.printStackTrace();
            return null;
        }
    }
    public void registerCommands() {
        unregisterCommands();

        if (commandMap == null) {
            plugin.getLogger().severe("CommandMap не инициализирован, " +
                    "регистрация невозможна.");
            return;
        }

        List<String> commandNames = plugin.getConfig().getStringList("commands");
        if (commandNames == null || commandNames.isEmpty()) {
            plugin.getLogger().warning("Список команд пуст, " +
                    "регистрируем 'rubin' по умолчанию.");
            commandNames = List.of("rubin");
        }

        rubin executor = new rubin();
        rubinTC tabCompleter = new rubinTC();

        for (String cmdName : commandNames) {
            String lower = cmdName.toLowerCase().trim();
            if (lower.isEmpty()) continue;

            Command cmd = new Command(lower) {
                @Override
                public boolean execute(CommandSender sender, String commandLabel,
                                       String[] args) {
                    return executor.onCommand(sender, this, commandLabel, args);
                }

                @Override
                public List<String> tabComplete(CommandSender sender, String alias,
                                                String[] args) {
                    return tabCompleter.onTabComplete(sender, this, alias, args);
                }
            };

            commandMap.register(plugin.getName().toLowerCase(), cmd);
            registeredCommands.add(cmd);
        }
    }

    public void unregisterCommands() {
        if (commandMap == null) return;
        for (Command cmd : registeredCommands) {
            cmd.unregister(commandMap);
        }
        registeredCommands.clear();
    }

    public void reloadCommands() {
        plugin.reloadConfig();
        registerCommands();
    }
}