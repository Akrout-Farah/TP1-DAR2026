package serverPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCalcul2 {
    public static void main(String[] args) {
        int port = (args.length > 1) ? Integer.parseInt(args[1]) : 5000;

        try {
            InetAddress ip = (args.length > 0)
                    ? InetAddress.getByName(args[0])
                    : InetAddress.getLocalHost();

            ServerSocket ss = new ServerSocket();
            ss.bind(new InetSocketAddress(ip, port));
            System.out.println("Serveur en écoute sur " + ip.getHostAddress() + ":" + port);
            System.out.println("Je suis un serveur en attente la connexion d'un client");

            Socket s = ss.accept();
            System.out.println("un client est connecté : " + s.getInetAddress().getHostAddress());

            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            while (true) {
                int choix = in.readInt();
                if (choix == 0) {
                    System.out.println("Le client a quitté : fin de la communication");
                    break;
                }
                double x = in.readDouble();
                double y = in.readDouble();
                System.out.println("Reçu : choix=" + choix + ", x=" + x + ", y=" + y);

                if (choix == 1) {
                    envoyer(out, x + y);
                } else if (choix == 2) {
                    envoyer(out, x - y);
                } else if (choix == 3) {
                    envoyer(out, x * y);
                } else if (choix == 4) {
                    if (y == 0) {
                        erreur(out, "Division par zéro impossible");
                    } else {
                        envoyer(out, x / y);
                    }
                } else {
                    erreur(out, "Choix invalide");
                }
            }

            in.close();
            out.close();
            s.close();
            ss.close();
            System.out.println("Sockets fermés côté serveur");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void envoyer(DataOutputStream out, double resultat) throws IOException {
        out.writeBoolean(true);
        out.writeDouble(resultat);
        out.flush();
        System.out.println("Résultat envoyé : " + resultat);
    }

    private static void erreur(DataOutputStream out, String message) throws IOException {
        out.writeBoolean(false);
        out.writeUTF(message);
        out.flush();
        System.out.println("Erreur envoyée : " + message);
    }
}