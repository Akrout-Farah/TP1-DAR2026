# TP1 - Applications client/serveur Java

## Étape 3 - Serveur

### Description

Le serveur attend qu'un client se connecte sur le port 1234.

### Code principal

La classe principale du serveur se trouve dans `src/serverPackage/Server.java`.

### Résultat attendu dans la console

```java
Je suis un serveur en attente la connexion d'un client
un client est connecté
```

### Interprétation

Le serveur se met en écoute, attend une connexion client, confirme la connexion et ferme ensuite la socket.

### Capture d'écran

Capture du lancement du serveur : à insérer ici.

---

## Étape 4 - Client

### Description

Le client Java essaie de se connecter au serveur sur le port 1234.

### Code principal

La classe principale du client se trouve dans `src/clientPackage/Client.java`.

### Code utilisé

```java
package clientPackage;

import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            System.out.println("Je suis un client pas encore connecté...");
            Socket socket = new Socket("127.0.0.1", 1234);
            System.out.println("je suis un client connecté");
            socket.close();
        } catch (IOException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

### Résultat affiché dans la console

```java
Je suis un client pas encore connecté...
je suis un client connecté
```

### Interprétation

Le client affiche d’abord qu’il n’est pas encore connecté, puis établit la connexion au serveur via une adresse IP (`127.0.0.1`) au lieu de `localhost`. Une fois la connexion réussie, il affiche un message de confirmation et ferme la socket.

### Changement de localhost vers adresse IP

Le code utilise désormais une adresse IP au lieu du nom `localhost` :

```java
Socket socket = new Socket("127.0.0.1", 1234);
```

### Captures d'écran

- Capture du lancement du client : à insérer ici.
- Capture du serveur avec la connexion client acceptée : à insérer ici.

---

## Conclusion

L’application serveur attend la connexion et le client se connecte avec succès sur l’IP locale 127.0.0.1. La communication est ensuite fermée proprement.
