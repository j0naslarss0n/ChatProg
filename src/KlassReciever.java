import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;

public class KlassReciever extends ChatMain implements Runnable{    // Klass/mottagare som lyssnar på medd.

    byte[] data = new byte[256];                                // Storlek på paket(meddelande) som tas emot.
    NetworkInterface netIf = NetworkInterface.getByName("wlo1");    // Address-link som den lyssnar på
    public KlassReciever() throws IOException {
        InetSocketAddress group = new InetSocketAddress(iadr, port);
        socket.joinGroup(group, netIf);


    }

    @Override
    public void run() {                                             // Kör trådat
        //String message;
        while(!Thread.interrupted()){                               // En loop som lyssnar på inkommande medd.
            //DatagramPacket packet = new DatagramPacket(data, data.length);
            try { DatagramPacket packet = new DatagramPacket(data, data.length); // Try-Catch; Paket m. data + längd
                socket.receive(packet);                             // Tar emot paket
                String message = new String(packet.getData(), 0, packet.getLength()); //Meddelande
                area.append(message);                                   // Applicera medd. i area
            } catch (IOException e) {                                   // Catch för exception.
                throw new RuntimeException(e);
            }
        }


    }
}
