import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpReceiver {

    public static void main(String[] args) {

        try(DatagramSocket ds = new DatagramSocket(3333)) {
            DatagramPacket packet = new DatagramPacket(new byte[16], 16);
            ds.receive(packet);
            for(byte b:  packet.getData()) {
                System.out.println(Character.valueOf((char)b));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
