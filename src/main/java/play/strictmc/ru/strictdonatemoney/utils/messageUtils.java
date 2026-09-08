package play.strictmc.ru.strictdonatemoney.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.regex.Pattern;

import static play.strictmc.ru.strictdonatemoney.StrictDonateMoney.inst;

public class messageUtils {
    private static final Pattern HEX_PATTERN =
            Pattern.compile("&#([A-Fa-f0-9]{6})");


    public static String getMessage(String s, Object... o){
        String pref = inst.getConfig().getString("prefix");
        String msg = pref + inst.getConfig().getString("message." + s,
                inst.getConfig().getString("message.not-message-config")
                        .replace("&", "§") + "message." + s);
        msg = ChatColor.translateAlternateColorCodes('&', msg);
        for (int i = 0; i < o.length; i +=2) {
            if (i + 1 < o.length) {
                msg = msg.replace(o[i].toString(), o[i+1].toString());
            }
        }
        return msg;
    }

    public static String getPermission(String path) {
        return inst.getConfig()
                .getString("permissions." + path);
    }

    public static void sendHelp(CommandSender sender) {
        sender.sendMessage(getMessage("help-header"));
        sender.sendMessage(getMessage("help-balance"));
        sender.sendMessage(getMessage("help-pay"));
        if (sender.hasPermission("strict.rubin.admin")) {
            sender.sendMessage(getMessage("help-give"));
            sender.sendMessage(getMessage("help-take"));
            sender.sendMessage(getMessage("help-set"));
            sender.sendMessage(getMessage("help-reload"));
        }
    }
}
