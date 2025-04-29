/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion_cabinet_medical_final;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author dell
 */
public   class  GESTION_CABINET_MEDICAL_FINAL {

    // Zone de Centalisation des ressources. 
    
    //centralisation de l'observableList de  la conversation patient medecin
    // ce liste est afficher de part et d'autre de l'interface patient et médécin. 
    //Temporaire
            //public volatile static ObservableList<String> ListConversationPatientMedecin=FXCollections.observableArrayList();
           
           
           private ObservableList<String> ListConversationPatientMedecin;

    

           public GESTION_CABINET_MEDICAL_FINAL() {
            ListConversationPatientMedecin=FXCollections.observableArrayList();
                }
           
           //get List
           public ObservableList<String> getListConversationPatientMedecin() {
        return ListConversationPatientMedecin; }
        // addItem
         public void addItem(String item) {
        ListConversationPatientMedecin.add(item);
   
           
           }

}
    
    

    
