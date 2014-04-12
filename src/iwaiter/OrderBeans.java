/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package iwaiter;

import java.util.ArrayList;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author viktormagdych
 */
public class OrderBeans {
    
    private SimpleStringProperty waitersName;
    private SimpleIntegerProperty orderNumber;
    private SimpleIntegerProperty tableNumber;
    private SimpleIntegerProperty billSum;
    private SimpleStringProperty finilised;
    private ArrayList<ItemBeans> itemFromThisOrder= new ArrayList<>(); 

     public OrderBeans(String w,int o, int t, int b, String f){
        
         this.waitersName = new SimpleStringProperty(w);
         this.orderNumber = new SimpleIntegerProperty(o);
         this.tableNumber = new SimpleIntegerProperty(t);
         this.billSum = new SimpleIntegerProperty(b);
         this.finilised = new SimpleStringProperty(f);
        
     }
     
    /**
     * @return the orderNumber
     */
    public SimpleIntegerProperty getOrderNumberProperty() {
        return orderNumber;
    }

    /**
     * @param orderNumber the orderNumber to set
     */
    public void setOrderNumberProperty(SimpleIntegerProperty orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * @return the tableNumber
     */
    public SimpleIntegerProperty getTableNumberProperty() {
        return tableNumber;
    }

    /**
     * @param tableNumber the tableNumber to set
     */
    public void setTableNumberProperty(SimpleIntegerProperty tableNumber) {
        this.tableNumber = tableNumber;
    }

    /**
     * @return the billSum
     */
    public SimpleIntegerProperty getBillSumProperty() {
        return billSum;
    }

    /**
     * @param billSum the billSum to set
     */
    public void setBillSumProperty(SimpleIntegerProperty billSum) {
        this.billSum = billSum;
    }

    /**
     * @return the finilised
     */
    public SimpleStringProperty getFinilisedProperty() {
        return finilised;
    }

    /**
     * @param finilised the finilised to set
     */
    public void setFinilisedproperty(SimpleStringProperty finilised) {
        this.finilised = finilised;
    }
    
    
     public SimpleStringProperty getWaitersNameProperty() {
        return waitersName;
    }

    /**
     * @param finilised the finilised to set
     */
    public void setWaitersNameproperty(SimpleStringProperty waitersName) {
        this.waitersName = waitersName;
    }
    
    
    public int getOrderNumber() {
        return orderNumber.get();
    }
    public int getTableNumber() {
        return tableNumber.get();
    }
    public int getBillSum() {
        return billSum.get();
    }
    public String getFinilised() {
        return finilised.get();
    }
    public String getWaitersName() {
        return waitersName.get();
    }
    
    public void setWaitersName(String w)
    {
        this.waitersName = new SimpleStringProperty(w);
    }
    public void setOrderNumber( int o) {
        this.orderNumber = new SimpleIntegerProperty(o);
    }
    public void setTableNumber( int t) {
        this.tableNumber = new SimpleIntegerProperty(t);
    }
    public void setBillSum( int b) {
        this.billSum = new SimpleIntegerProperty(b);
    }
    public void setFinilised( String f) {
        this.finilised = new SimpleStringProperty(f);
    }
    
  
    
}
