/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package iwaiter.model;

import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author viktormagdych
 */
public class TableBeans {
    
    private SimpleIntegerProperty tableNumber;
    ArrayList<OrderBeans> ordersForTable=new ArrayList<>();
    
    public TableBeans() {}
    
    public TableBeans(int tableNumber) {
     this.tableNumber= new SimpleIntegerProperty(tableNumber);   
    }
    
      public SimpleIntegerProperty getTableNumberProperty() {
        return tableNumber;
    }

   
    public void setTableNumberProperty(SimpleIntegerProperty tableNumber) {
        this.tableNumber = tableNumber;
    }
    
    public int getTableNumber() {
        return tableNumber.getValue();
    }
    
    public void setTableNumber(int tableNumber) {
        this.tableNumber=new SimpleIntegerProperty(tableNumber); 
    }
}
