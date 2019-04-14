package client.handlers;

import client.ClientModel;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SettingsHandler {

    private ClientModel model;
    private HashMap<String, String> settings;

    public SettingsHandler(ClientModel model) {
        this.model = model;
        settings = getSettings();
    }

    public HashMap<String, String> getSettings() {
        loadSettings();
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
        saveSettings();
    }

    public void updateSettings(HashMap<String, String> newSettings) {
        loadSettings();
        System.out.println("Updating settings\n Current settings = " + settings.toString());

        for (String key : newSettings.keySet()) {
            System.out.println("Setting at key \"" + key + "\" value: \"" + newSettings.get(key));
            settings.put(key, newSettings.get(key));
        }

        System.out.println("New Settings = " + settings.toString());
        saveSettings();
    }

    public void saveSettings(){
        File settingsFile = new File("settings.ser");
        try {
            ObjectOutputStream settingsOut = new ObjectOutputStream(new FileOutputStream(settingsFile));
            settingsOut.writeObject(settings);
            settingsOut.close();
        }catch(IOException ex){
            System.out.println(ex.toString());
            System.out.println("Bestand is niet opgeslagen");
        }
    }

    public void loadSettings(){
        try
        {
            ObjectInputStream settingsIn = new ObjectInputStream(new FileInputStream("settings.ser"));
            settings = (HashMap) settingsIn.readObject();
            settingsIn.close();
        }catch(IOException ioe)
        {
            System.out.println(ioe.toString());
            System.out.println("Bestand is niet gevonden");
        }catch(ClassNotFoundException c)
        {
            System.out.println(c.toString());
        }
    }
}
