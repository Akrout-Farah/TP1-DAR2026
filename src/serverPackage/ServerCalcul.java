package serverPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCalcul {
    public static void main(String[] args) {
        // Usage : java serverPackage.ServerCalcul2 [adresseIP] [port]
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

                switch (choix) {
                    case 1: envoyer(out, x + y); break;
                    case 2: envoyer(out, x - y); break;
                    case 3: envoyer(out, x * y); break;
                    case 4:
                        if (y == 0) erreur(out, "Division par zéro impossible");
                        else envoyer(out, x / y);
                        break;
                    default: erreur(out, "Choix invalide");
                }
            }

            in.close();
            out.close();
            s.close();
            ss.close();
            System.out.println("Sockets fermés côté serveur");
        } catch (IOException e) {
            System.err.println("Erreur serveur : " + e.getMessage());
        }
    }

    private static void envoyer(DataOutputStream out, double r) throws IOException {
        out.writeBoolean(true);
        out.writeDouble(r);
        out.flush();
        System.out.println("Résultat envoyé : " + r);
    }

    private static void erreur(DataOutputStream out, String msg) throws IOException {
        out.writeBoolean(false);
        out.writeUTF(msg);
        out.flush();
        System.out.println("Erreur envoyée : " + msg);
    }
}