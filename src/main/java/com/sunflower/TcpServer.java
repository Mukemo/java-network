import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {

    public static void main(String[] args) {
        try(Socket s = new ServerSocket(3333).accept()) {
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            while(true) {
                String msg = dis.readUTF();
                System.out.println("Client said: " + msg);
                if ("end".equals(msg)) {
                    break;
                }
                System.out.println("Say something : ");
                msg = console.readLine();
                dos.writeUTF(msg);
                dos.flush();
                if ("end".equals(msg)) {
                    break;
                }
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
