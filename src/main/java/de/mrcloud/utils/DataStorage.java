package de.mrcloud.utils;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.mrcloud.Cops;
import de.mrcloud.command.Command;
import de.mrcloud.duty.OnDuty;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DataStorage {

    //If the list of ranks ever changes it is easier to edit
    public static List<Command> commands = new ArrayList<>();
    public static String dateLogName = "";

    public static void registerCommands(Command... commands) {
        DataStorage.commands.addAll(Arrays.asList(commands));
    }

    public static List<Command> getCommands() {
        return new ArrayList<>(commands);
    }

    public static OnDuty onDuty;


    public static Cops cops;
    public static Settings settings;

    public static boolean loadCops() {
        System.out.println("Loading cop list");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            File file = new File("cops.json");
            if (file.exists()) {
                cops = objectMapper.readValue(file, Cops.class);
            } else {
                file.createNewFile();
                System.out.println(file.getAbsolutePath());
                InputStream resourceAsStream = Cops.class.getResourceAsStream("/cops.json");
                cops = objectMapper.readValue(resourceAsStream, Cops.class);
                objectMapper.writeValue(file, cops);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean loadSettings() {
        System.out.println("Loading settings");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            File file = new File("settings.json");
            if (file.exists()) {
                settings = objectMapper.readValue(file, Settings.class);
            } else {
                file.createNewFile();
                System.out.println(file.getAbsolutePath());
                InputStream resourceAsStream = Cops.class.getResourceAsStream("/settings.json");
                settings = objectMapper.readValue(resourceAsStream, Settings.class);
                objectMapper.writeValue(file, settings);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
