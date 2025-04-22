/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package medecin;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**
 *
 * @author Roddier
 */
public class EcouteurFichierMedecin {
    public static void main(String[] args) throws Exception {
        Path filePath = Paths.get("D:\\Workspace\\cabinetmedical"+"\\special\\send_by_doctor_expert.txt");
        WatchService watchService = FileSystems.getDefault().newWatchService();
        
        filePath.getParent().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
        
        System.out.println("Surveillance du fichier : "+ filePath);
        
        while (true) {
            WatchKey key = watchService.take();
            
            for (WatchEvent<?> event : key.pollEvents()) {
                if(even.context().toString().equals(filePath.getFileName().toString())){
                    System.out.println("ecouteur fichier : ficier modifie = true !");
                    Medecin_Agent.fichierModifierMedecin = true;
                }
            }
            
            boolean valid = key.reset();
            
            if(!valid) {
                break;
            }
        }
        
        watchService.close();
    }
}
