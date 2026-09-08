package play.strictmc.ru.strictdonatemoney;

import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import play.strictmc.ru.strictdonatemoney.utils.commandUtil;

public final class StrictDonateMoney extends JavaPlugin {

    private commandUtil commandUtil;
    public static PlayerPointsAPI ppAPI;
    public static StrictDonateMoney inst;

    public static StrictDonateMoney getInstance() {
        return inst;
    }

    public commandUtil getCommandUtil() {
        return commandUtil;
    }

    @Override
    public void onEnable() {
        inst = this;
        saveDefaultConfig();

        if (Bukkit.getPluginManager().isPluginEnabled("PlayerPoints")) {
            ppAPI = PlayerPoints.getInstance().getAPI();
            getLogger().info("PlayerPoints API успешно получен!");
        } else {
            getLogger().severe("PlayerPoints не найден!!! Плагин отключается. :-(");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        commandUtil = new commandUtil(this);
        commandUtil.registerCommands();
    }

    @Override
    public void onDisable() {
        if (commandUtil != null) {
            commandUtil.unregisterCommands();
        }
    }
}