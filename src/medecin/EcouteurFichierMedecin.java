/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medecin;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**
 *
 * @author dell
 */
public class EcouteurFichierMedecin {
    
  
    public static void main(String[] args) throws Exception {
        // Obtenez le chemin complet du fichier que vous souhaitez surveiller
        Path filePath = Paths.get("D:\\Workspace\\cabinetmedical\\Health Bot avec les fichiers\\send_by_doctor_expert.txt");
        
        
        // Créez un objet WatchService
        WatchService watchService = FileSystems.getDefault().newWatchService();
        
        // Enregistrez le chemin du fichier auprès du WatchService pour surveiller les modifications
        filePath.getParent().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
        
        System.out.println("Surveillance du fichier : " + filePath);
        
        // Boucle infinie pour attendre les événements de modification du fichier
        while (true) {
            // Récupérez le prochain événement de modification du WatchService
            WatchKey key = watchService.take();
            
            // Parcourez les événements associés à la clé
            for (WatchEvent<?> event : key.pollEvents()) {
                // Vérifiez si l'événement est lié à la modification du fichier surveillé
                if (event.context().toString().equals(filePath.getFileName().toString())) {
                    System.out.println("ecouteur fichier fichier modifier = true !");
                    Medecin_Agent.fichierModifierMedecin=true;
                }
            }
            
            // Réinitialisez la clé pour recevoir de nouveaux événements
            boolean valid = key.reset();
            
            // Sortez de la boucle si la clé n'est plus valide
            if (!valid) {
                break;
            }
        }
        
        // Fermez le WatchService
        watchService.close();
    }
    
}
