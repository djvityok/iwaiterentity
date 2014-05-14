/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package iwaiter.model;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



/**
 *
 * @author viktormagdych
 */

public class WaiterBeans {
    
     private SimpleStringProperty name;
     private SimpleStringProperty lastName;
     ArrayList<OrderBeans> orderItems= new ArrayList<>();
     
     
    public WaiterBeans() {}
    
    public WaiterBeans(String name, String lastName) 
    {
        this.name= new SimpleStringProperty(name);
        this.lastName= new SimpleStringProperty(lastName);
    
    }

   

    public final SimpleStringProperty nameProperty() {
        if (name == null) {
            name = new SimpleStringProperty();
        }
        return name;
    }
     public final SimpleStringProperty lastNameProperty() {
        if (lastName == null) {
            lastName = new SimpleStringProperty();
        }
        return lastName;
    }

    /**
     * @return the name
     */
    public String getName() {
        if (name != null)
            return name.get();
        return null;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        if (lastName != null)
            return lastName.get();
        return null;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    
    public String toString()
    {
        return name.getValue()+" "+lastName.getValue();
    }

    
} 
   
