package boardgame;

public class SettingsModel {

    private String IPAddress;
    private int gateway;

    public SettingsModel() {

    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
        System.out.println("Set IP address to " + IPAddress);
    }

    public void setGateway(int gateway) {
        this.gateway = gateway;
        System.out.println("Set gateway to " + gateway);
    }
}
