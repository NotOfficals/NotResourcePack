package not.notoffical.command;

import not.notoffical.NotResourcePack;
import not.notoffical.utils.ColorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    private final NotResourcePack plugin;

    public ReloadCommand(NotResourcePack plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Эту команду можно использовать только в игре.");
            return true;
        }

        Player player = (Player) sender;
        if (player.hasPermission("notrp.reload")) {
            plugin.reloadConfig();
            player.sendMessage(ColorUtil.green + "Конфигурация плагина успешно перезагружена.");
        } else {
            player.sendMessage(ColorUtil.darkred + "У вас нет прав на использование этой команды.");
        }

        return true;
    }
}
