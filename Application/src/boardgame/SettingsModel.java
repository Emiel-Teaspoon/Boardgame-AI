package boardgame;

public class SettingsModel {

    //private String IPAddress = "145.33.225.170";
    private String IPAddress = "127.0.0.1";
    private int gateway = 7789;

    public SettingsModel() {
    }

    public void setIPAddress(String IPAddress) {this.IPAddress = IPAddress;}

    public void setGateway(int gateway) {this.gateway = gateway;}

    public String getIPAddress() {return IPAddress;}

    public int getGateway(){return gateway;}
}
