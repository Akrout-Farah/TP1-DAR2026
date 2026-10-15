package clientPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientCalcul2 {
    public static void main(String[] args) {
        // Usage : java clientPackage.ClientCalcul2 [IP du serveur] [port]
        String hote = (args.length > 0) ? args[0] : "127.0.0.1";
        int port = (args.length > 1) ? Integer.parseInt(args[1]) : 5000;

        Scanner sc = new Scanner(System.in);
        System.out.println("Je suis un client pas encore connecté...");

        try (Socket s = new Socket()) {
            s.connect(new InetSocketAddress(InetAddress.getByName(hote), port), 5000);
            System.out.println("je suis un client connecté à " + hote + ":" + port);

            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            DataInputStream in = new DataInputStream(s.getInputStream());

            int choix;
            do {
                System.out.println("\n===== CALCULATRICE =====");
                System.out.println("1. Addition");
                System.out.println("2. Soustraction");
                System.out.println("3. Multiplication");
                System.out.println("4. Division");
                System.out.println("0. Quitter");
                System.out.print("Votre choix : ");
                choix = lireEntier(sc);

                if (choix < 0 || choix > 4) {
                    System.out.println("Choix invalide, entrez un nombre de 0 à 4.");
                    continue;
                }

                out.writeInt(choix);

                if (choix != 0) {
                    System.out.print("x = ");
                    double x = lireDouble(sc);
                    System.out.print("y = ");
                    double y = lireDouble(sc);

                    out.writeDouble(x);
                    out.writeDouble(y);
                    out.flush();

                    if (in.readBoolean()) {
                        System.out.println("Résultat : " + in.readDouble());
                    } else {
                        System.out.println("Erreur du serveur : " + in.readUTF());
                    }
                } else {
                    out.flush();
                }
            } while (choix != 0);

            System.out.println("Fin de la communication, fermeture des sockets");
        } catch (IOException e) {
            System.err.println("Erreur client : " + e);
        }
    }

    private static int lireEntier(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Ce n'est pas un entier, recommencez : ");
            sc.next();
        }
        return sc.nextInt();
    }

    private static double lireDouble(Scanner sc) {
        while (!sc.hasNextDouble()) {
            System.out.print("Ce n'est pas un nombre, recommencez : ");
            sc.next();
        }
        return sc.nextDouble();
    }
}