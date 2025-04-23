/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package medecin;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Roddier
 */
/**
 * Modèle d'un utilisateur.
 */
class User {
    private final IntegerProperty numero;
    private final StringProperty nom;
    private final IntegerProperty age;
    private final StringProperty sexe;

    public User(int numero, String nom, int age, String sexe) {
        this.numero = new SimpleIntegerProperty(numero);
        this.nom = new SimpleStringProperty(nom);
        this.age = new SimpleIntegerProperty(age);
        this.sexe = new SimpleStringProperty(sexe);
    }

    public int getNumero() { return numero.get(); }
    public IntegerProperty numeroProperty() { return numero; }

    public String getNom() { return nom.get(); }
    public StringProperty nomProperty() { return nom; }

    public int getAge() { return age.get(); }
    public IntegerProperty ageProperty() { return age; }

    public String getSexe() { return sexe.get(); }
    public StringProperty sexeProperty() { return sexe; }
}
