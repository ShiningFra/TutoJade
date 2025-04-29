
package medecin;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class medecinFichier {
    public  String read(String fileName) {
        StringBuilder contenu = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String ligne;

            // Lit chaque ligne du fichier jusqu'à la fin
            while ((ligne = reader.readLine()) != null) {
                contenu.append(ligne).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }

        return contenu.toString();
    }

    public  void write(String fileName, String contenu) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(contenu);
            // Écriture dans le fichier
        

            System.out.println("Le contenu a été écrit dans le fichier avec succès.");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier : " + e.getMessage());
        }
    }

    
    
}
