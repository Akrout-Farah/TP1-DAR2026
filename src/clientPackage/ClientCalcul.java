package clientPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientCalcul {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Je suis un client pas encore connecté...");

        try (Socket s = new Socket("127.0.0.1", 1234)) {
            System.out.println("je suis un client connecté");

            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            DataInputStream in = new DataInputStream(s.getInputStream());

            int x;
            do {
                System.out.print("Entrez un entier (0 pour quitter) : ");
                while (!sc.hasNextInt()) {
                    System.out.print("Ce n'est pas un entier, recommencez : ");
                    sc.next();
                }
                x = sc.nextInt();

                out.writeInt(x);          // b) envoi (y compris le 0)
                out.flush();

                if (x != 0) {
                    int resultat = in.readInt();
                    System.out.println("Résultat (x * 5) : " + resultat);
                }
            } while (x != 0);              // c) continue tant que x != 0

            System.out.println("Fin de la communication, fermeture des sockets");
        } catch (IOException e) {
            System.err.println("Erreur client : " + e.getMessage());
        }
    }
}