import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ChatMain extends JFrame implements ActionListener{         // Huvudklassen
                                                                        // Med Swing-frame och aktivator för knappar etc
    JButton quitButton = new JButton("Avsluta");                    // Skapar Swing-moduler
    JPanel panel = new JPanel();
    JTextArea area = new JTextArea(20, 50);
    JTextField text = new JTextField("Skriv..");
    String name = JOptionPane.showInputDialog(null, "Ditt namn"); // Initial prompt
    String message;
    String ip = "234.235.236.237";                      // Variabel sträng ip-nummer
    InetAddress iadr = InetAddress.getByName(ip);       // Initierar o omvandlar IP-sträng till Inetaddress
    int port = 12540;                                   // var. int port
    MulticastSocket socket = new MulticastSocket(port); // Initierar o sätter portnummer

    public ChatMain() throws IOException {
        this.add(panel);                                // Sätter och bygger grafiska komponenter
        panel.setLayout(new BorderLayout());            //          - " -  etc
        panel.add(quitButton, BorderLayout.NORTH);

        quitButton.addActionListener(this);             // Lyssnare till knapp avsluta.
        panel.add(area, BorderLayout.CENTER);
        text.addActionListener(this);
        panel.add(text, BorderLayout.SOUTH);

        this.pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(300, 300);


    }

    public static void main(String[] args) throws IOException {

        //ChatMain cm = new ChatMain();
        KlassReciever kr = new KlassReciever();         // Initierar Receivern
        Thread t = new Thread(kr);                      // Initierar Threading
        t.start();                                      // Startar Threading
    }

    @Override
    public void actionPerformed(ActionEvent e) {        // Lyssnar på aktioner från knappar etc.
        if (e.getSource() == quitButton){               // Avlsuta knappens aktion
            System.out.println("Avsluta knapp");
            System.exit(0);
        }if (e.getSource() == text){                    // Skicka text från textfält till textarea.
            //area.append(name + ": " + text.getText() + "\n");
            message = name + ": " + text.getText() + "\n";
            text.setText("");
            System.out.println(message);
            byte[] data = message.getBytes();           // Stlk meddelande, i ett datagrampacket.
            DatagramPacket packet = new DatagramPacket(data, data.length, iadr, port);
            //System.out.println(packet);
            try {
                socket.send(packet);                    // Skicka datapaket med try-catch
            } catch (IOException ex) {
                System.out.println("Error meddelande skickat");
                throw new RuntimeException(ex);

            }
        }
    }
}