package serverPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCalcul {
    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(1234)) {
            System.out.println("Je suis un serveur en attente la connexion d'un client");
            Socket s = ss.accept();
            System.out.println("un client est connecté");

            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            while (true) {
                int x = in.readInt();
                if (x == 0) {
                    System.out.println("Le client a envoyé 0 : fin de la communication");
                    break;
                }
                System.out.println("Reçu : " + x + " -> envoi de " + (x * 5));
                out.writeInt(x * 5);
                out.flush();
            }

            in.close();
            out.close();
            s.close();
            System.out.println("Sockets fermés côté serveur");
        } catch (IOException e) {
            System.err.println("Erreur serveur : " + e.getMessage());
        }
    }
}