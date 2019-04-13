package client.handlers;

import client.ClientModel;

import java.util.HashMap;

public class SettingsHandler {

    private ClientModel model;
    private HashMap<String, String> settings;

    public SettingsHandler(ClientModel model) {
        this.model = model;
        settings = getSettings();
    }

    public HashMap<String, String> getSettings() {
        // TODO: Read from file
        // If file doesn't exist return defaultSettings;

        if (settings == null) {
            defaultSettings();
        }
        return settings;
    }

    public void defaultSettings() {
        settings = new HashMap<>();
        settings.put("name", "Naam");
        settings.put("host", "localhost");
        settings.put("port", "7789");
    }

    public void updateSettings(HashMap<String, String> newSettings) {
        System.out.println("Updating settings\n Current settings = " + settings.toString());

        for (String key : newSettings.keySet()) {
            System.out.println("Setting at key \"" + key + "\" value: \"" + newSettings.get(key));
            settings.put(key, newSettings.get(key));
        }

        System.out.println("New Settings = " + settings.toString());
        // TODO: Write settings to file
    }
}
