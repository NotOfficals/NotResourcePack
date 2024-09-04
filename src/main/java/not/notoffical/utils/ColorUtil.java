package not.notoffical.utils;

import net.md_5.bungee.api.ChatColor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    public static String color(String text) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

        for(Matcher matcher = pattern.matcher(text); matcher.find(); matcher = pattern.matcher(text)) {
            String hexCode = text.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');
            StringBuilder builder = new StringBuilder();
            replaceSharp.chars().forEach((c) -> {
                builder.append("&").append((char)c);
            });
            text = text.replace(hexCode, builder.toString());
        }

        return ChatColor.translateAlternateColorCodes('&', text).replace("&", "");
    }

    public static List<String> color(List<String> text) {
        return (List)text.stream().map(ColorUtil::color).collect(Collectors.toList());
    }
}
