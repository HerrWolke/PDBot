package de.mrcloud.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

public class Settings {
    private LinkedHashMap<String, String> settings;

    public LinkedHashMap<String, String> getSettings() {
        return settings;
    }

    public void setSettings(LinkedHashMap<String, String> settings) {
        this.settings = settings;
    }

    public void updateSetting(String settingName, String valueNew) {
        settings.replace(settingName, valueNew);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


        File file = new File("settings.json");

        try {
            objectMapper.writeValue(file, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
