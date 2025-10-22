import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpSender {

    public static void main(String[] args) {
        try(DatagramSocket ds = new DatagramSocket()) {
            String msg = "Hello, this is UDP sender!";
            InetAddress address = InetAddress.getByName("127.0.0.1");
            DatagramPacket dp = new DatagramPacket(msg.getBytes(), msg.length(), address, 3333);
            ds.send(dp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
