package not.notoffical;

import not.notoffical.command.*;
import not.notoffical.utils.ColorUtil;
import not.notoffical.utils.Updater;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.NotSerializableException;

public final class NotResourcePack extends JavaPlugin implements Listener {

    private boolean debug;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getCommand("notrpreload").setExecutor(new ReloadCommand(this));
        Bukkit.getConsoleSender().sendMessage(ColorUtil.color(""));
        Bukkit.getConsoleSender().sendMessage(ColorUtil.color("&a» &fПлагин &a" + getPlugin(NotResourcePack.class).getName() + " &aвключился&f!"));
        Bukkit.getConsoleSender().sendMessage(ColorUtil.color("&a» &fВерсия: &av" + getPlugin(NotResourcePack.class).getDescription().getVersion()));
        Bukkit.getConsoleSender().sendMessage(ColorUtil.color(""));
        Updater updater = new Updater(this);
        updater.start();
        debug = getConfig().getBoolean("debug");
        getServer().getPluginManager().registerEvents(this, this);
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        saveConfig();
        Bukkit.getConsoleSender().sendMessage(ColorUtil.color(""));
        Bukkit.getConsoleSender().sendMessage(ColorUtil.color("&a» &fПлагин &a" + (getPlugin(NotResourcePack.class)).getName() + " &fвыключился&f!"));
        Bukkit.getConsoleSender().sendMessage(ColorUtil.color("&a» &fВерсия: &av" + (getPlugin(NotResourcePack.class)).getDescription().getVersion()));
        Bukkit.getConsoleSender().sendMessage(ColorUtil.color(""));
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
            player.kickPlayer(ColorUtil.red + "Вы отказались загружать ресурс-пак сервера!");
        } else if (status == PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED) {
            if (debug) {
                getLogger().info(ColorUtil.white + player.getName() + ColorUtil.green + " загрузил ресурс пак!");
            }
        }
    }
}
