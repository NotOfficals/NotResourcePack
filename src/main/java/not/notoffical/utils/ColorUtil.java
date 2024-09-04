package not.notoffical.utils;

import net.md_5.bungee.api.ChatColor;

public class ColorUtil {

    public static String nameColor = ColorUtil.Color("4ab6fb");

    public static String white = ColorUtil.Color("ffffff");
    public static String green = ColorUtil.Color("66ff00");
    public static String red = ColorUtil.Color("ff2400");
    public static String darkred = ColorUtil.Color("8B0000");


    public static String Color(String hexColor) {
        if (hexColor.startsWith("#")) {
            hexColor = hexColor.substring(1);
        }
        return ChatColor.of("#" + hexColor) + "";
    }
}
