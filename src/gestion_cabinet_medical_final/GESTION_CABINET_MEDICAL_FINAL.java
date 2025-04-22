/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion_cabinet_medical_final;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Roddier
 */
public class GESTION_CABINET_MEDICAL_FINAL {
    private ObservableList<String> ListConversationPatientMedecin;
    public GESTION_CABINET_MEDICAL_FINAL() {
        this.ListConversationPatientMedecin = FXCollections.observableArrayList();
        
    }
    
    public ObservableList<String> getListConversationPatientMedecin(){
        return this.ListConversationPatientMedecin;
    }
    
    public void addItem(String item) {
        this.ListConversationPatientMedecin.add(item);
    }
}
