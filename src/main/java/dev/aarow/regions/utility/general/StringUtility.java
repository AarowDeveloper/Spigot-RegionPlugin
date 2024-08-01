package dev.aarow.regions.utility.general;

import dev.aarow.regions.plugin.RegionsPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtility {

    public static String LOADING_DOTS = "";

    public static String getNiceDate(long millis){
        Date date = new Date(millis);

        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date);
    }

    public static String getNiceEnumString(String enumName) {
        String replaced = enumName.replace("_", " ");

        String[] words = replaced.split(" ");

        StringBuilder formattedName = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                formattedName.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        return formattedName.toString().trim();
    }

    public static String getChatColorStatus(boolean status){
        return status ? "&a" : "&c";
    }

    public static String replaceSpaces(String input){
        return input.replace(" ", "_");
    }

    public static void startLoadingDotsTask(){
        new BukkitRunnable(){
            public void run(){
                switch(LOADING_DOTS){
                    case "":
                        LOADING_DOTS = ".";
                        break;
                    case ".":
                        LOADING_DOTS = "..";
                        break;
                    case "..":
                        LOADING_DOTS = "...";
                        break;
                    case "...":
                        LOADING_DOTS = "";
                        break;
                }
            }
        }.runTaskTimer(RegionsPlugin.getInstance(), 0, 20);
    }
}
