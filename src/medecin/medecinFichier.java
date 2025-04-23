/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package medecin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Roddier
 */
public class medecinFichier {
    public String read(String fileName) {
        StringBuilder contenu = new StringBuilder();
        
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String ligne;
            
            while((ligne = reader.readLine()) != null) {
                contenu.append(ligne).append("\n");
                
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : "+e.getMessage());
        }
        
        return contenu.toString();
    }
    
    public void write (String fileName, String contenu){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(contenu);
            
            System.out.println("Le contenu a ete ecrit dans le fichier avec succes");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'ecriture dans le fichier : "+e.getMessage());
        }
    }
}
