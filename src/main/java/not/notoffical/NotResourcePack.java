package not.notoffical;

import not.notoffical.command.*;
import not.notoffical.utils.ColorUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class NotResourcePack extends JavaPlugin {

    private boolean debug;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getCommand("notrp").setExecutor(new ReloadCommand(this));
        this.getLogger().info("§7| ");
        this.getLogger().info("§7| §bNotResourcePack: §aEnable");
        this.getLogger().info("§7| §fDeveloper: §bNotOffical");
        this.getLogger().info("§7| §fVersion: §b1.0");
        this.getLogger().info("§7| ");
        debug = getConfig().getBoolean("debug");
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        saveConfig();
        this.getLogger().info("§7| ");
        this.getLogger().info("§7| §bNotResourcePack: §cDisable");
        this.getLogger().info("§7| §fDeveloper: §bNotOffical");
        this.getLogger().info("§7| §fVersion: §b1.0");
        this.getLogger().info("§7| ");
        // Plugin disable logic
    }
    @EventHandler
    public void onResourcePackStatus(PlayerResourcePackStatusEvent event) {
        Player player = event.getPlayer();
        PlayerResourcePackStatusEvent.Status status = event.getStatus();
        if (status == PlayerResourcePackStatusEvent.Status.DECLINED ||
                status == PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD) {
            if (debug) {
                getLogger().info(ColorUtil.white + player.getName() + ColorUtil.red + " не загрузил ресурс пак!");
            }
        } else if (status == PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED) {
            if (debug) {
                getLogger().info(ColorUtil.white + player.getName() + ColorUtil.green + " загрузил ресурс пак!");
            }
        }
    }
}
