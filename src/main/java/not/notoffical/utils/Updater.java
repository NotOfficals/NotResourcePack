package not.notoffical.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.paint.Color;
import not.notoffical.NotResourcePack;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Updater {
    private final String pluginName;
    private final String currentVersion;
    private String latestVersion;
    private String resourceURL;

    public Updater(NotResourcePack plugin) {
        this.pluginName = plugin.getDescription().getName();
        this.currentVersion = plugin.getDescription().getVersion();
        this.latestVersion = plugin.getDescription().getVersion();
    }

    private boolean checkForUpdates() {
        String githubRepo = "notofficals/" + this.pluginName;

        try {
            String url = "https://api.github.com/repos/" + githubRepo + "/releases";
            HttpURLConnection conn = (HttpURLConnection) (new URL(url)).openConnection();
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 404) {
                return false;
            } else {
                InputStreamReader input = new InputStreamReader(conn.getInputStream());
                BufferedReader reader = new BufferedReader(input);
                StringBuilder responseBuilder = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }

                reader.close();
                input.close();
                JsonElement jsonElement = (new JsonParser()).parse(responseBuilder.toString());
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                JsonObject latestRelease = jsonArray.get(0).getAsJsonObject();
                this.latestVersion = latestRelease.get("tag_name").getAsString();
                this.resourceURL = latestRelease.get("html_url").getAsString();
                String[] currentVersionParts = this.currentVersion.split("\\-");
                String[] latestVersionParts = this.latestVersion.split("\\-");
                String[] currentVersionNumbers = currentVersionParts[0].split("\\.");
                String[] latestVersionNumbers = latestVersionParts[0].split("\\.");

                for (int i = 0; i < Math.min(currentVersionNumbers.length, latestVersionNumbers.length); ++i) {
                    int currentPart = Integer.parseInt(currentVersionNumbers[i]);
                    int latestPart = Integer.parseInt(latestVersionNumbers[i]);
                    if (currentPart < latestPart) {
                        return true;
                    }

                    if (currentPart > latestPart) {
                        return false;
                    }
                }

                if (latestVersionParts.length > 1 && currentVersionParts.length == 1) {
                    return false;
                } else if (currentVersionParts.length > 1 && latestVersionParts.length == 1) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException error) {
            Bukkit.getConsoleSender().sendMessage(ColorUtil.color("&fПроизошла ошибка при проверке наличия обновлений: &6" + error.getMessage()));
            return false;
        } catch (Exception error) {
            Bukkit.getConsoleSender().sendMessage(ColorUtil.color("&fНе могу проверить наличие обновлений!"));
            error.printStackTrace();
            return false;
        }
    }

    public void start() {
        if (this.checkForUpdates()) {
            String newVersion = this.latestVersion;
            Bukkit.getConsoleSender().sendMessage(ColorUtil.color("&a"));
            Bukkit.getConsoleSender().sendMessage(ColorUtil.color("&a» &fНайдена новая версия плагина: &av" + newVersion));
            Bukkit.getConsoleSender().sendMessage(ColorUtil.color("&a» &fСкачайте ее по ссылке: &a" + this.resourceURL));
            Bukkit.getConsoleSender().sendMessage(ColorUtil.color("&a"));
        } else {
            Bukkit.getConsoleSender().sendMessage(ColorUtil.color("&a"));
            Bukkit.getConsoleSender().sendMessage(ColorUtil.color("&a» &fОбновления не найдены!"));
            Bukkit.getConsoleSender().sendMessage(ColorUtil.color("&a» &fВы используете последнюю версию плагина - &av" + this.latestVersion));
            Bukkit.getConsoleSender().sendMessage(ColorUtil.color("&a"));
        }

    }
}
