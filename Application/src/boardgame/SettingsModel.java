package boardgame;

import java.util.HashMap;
import java.io.*;
import java.util.Map;

public class SettingsModel {
    HashMap<String, String> settingsMap = new HashMap<String, String>();

    private String IPAddress;
    private int gateway;

    public SettingsModel() {

    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
        settingsMap.put("IPAddress", IPAddress);
        System.out.println("Set IP address to " + IPAddress);
    }

    public void setGateway(int gateway) {
        this.gateway = gateway;
        settingsMap.put("Gateway", String.valueOf(gateway));
        System.out.println("Set gateway to " + gateway);
    }

    //Slaat de settings hashmap op in een .ser bestand.
    public void saveSettings(){
        File settings = new File("settings.ser");
        try {
            ObjectOutputStream settingsOut = new ObjectOutputStream(new FileOutputStream(settings));
            settingsOut.writeObject(settingsMap);
            settingsOut.close();
            //Staat hier tijdelijk om te laten zien dat het werkt.
            loadSettings();
        }catch(IOException ex){
            System.out.println(ex.toString());
            System.out.println("Bestand is niet opgeslagen");
        }
    }

    //Update de hashmap met de gegevens die in de .ser staan.
    public void loadSettings(){
        try
        {
            ObjectInputStream settingsIn = new ObjectInputStream(new FileInputStream("settings.ser"));
            settingsMap = (HashMap) settingsIn.readObject();
            settingsIn.close();
        }catch(IOException ioe)
        {
            System.out.println(ioe.toString());
            System.out.println("Bestand is niet gevonden");
        }catch(ClassNotFoundException c)
        {
            System.out.println(c.toString());
        }
        System.out.println("Opgeslagen instellingen zijn:");
        for(Map.Entry<String, String> entry : settingsMap.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + " = " + value);
        }
    }
}
