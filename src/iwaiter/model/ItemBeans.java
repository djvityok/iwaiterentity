/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package iwaiter.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author viktormagdych
 */
public class ItemBeans {
    
    private SimpleIntegerProperty orderNumber;
    private SimpleStringProperty itemsName;
    private SimpleIntegerProperty itemPrice;
    private SimpleIntegerProperty itemNumber; // for primary schlüßel
           
    public ItemBeans() {}
    
    public ItemBeans(String n, int p) {// constructor for new items table
        this (0,n,p,0);
    }
    public ItemBeans(int o,String n, int p,int i)
    {
        this.orderNumber=new SimpleIntegerProperty(o);
        this.itemPrice= new SimpleIntegerProperty(p);
        this.itemsName = new SimpleStringProperty(n);
        this.itemNumber=new SimpleIntegerProperty(i);
    }
    
     public SimpleStringProperty getItemsNameProperty() {
        return itemsName;
    }

    
    public void setItemsNameProperty(SimpleStringProperty itemsName) {
        this.itemsName = itemsName;
    }
    
     public SimpleIntegerProperty getItemPriceProperty() {
        return itemPrice;
    }

   
    public void setItemPriceProperty(SimpleIntegerProperty itemPrice) {
        this.itemPrice = itemPrice;
    }
    
    public SimpleIntegerProperty getOrderNumberProperty() {
        return orderNumber;
    }

   
    public void setOrderNumberProperty(SimpleIntegerProperty orderNumber) {
        this.orderNumber = orderNumber;
    }
    
    public SimpleIntegerProperty getItemNumberProperty() {
        return itemNumber;
    }

   
    public void setItemNumberProperty(SimpleIntegerProperty itemNumber) {
        this.itemNumber = itemNumber;
    }
    
    public String getItemsName() {
        return itemsName.get();
    }
    public void setItemsName (String p)
    {
        this.itemsName= new SimpleStringProperty(p);
    }
    public int getItemPrice() {
        return itemPrice.get();
    }
    public void setItemPrice (int p)
    {
        this.itemPrice= new SimpleIntegerProperty(p);
    }
     public int getOrderNumber() {
        return orderNumber.get();
    }
    public void setOrderNumber (int p)
    {
        this.orderNumber= new SimpleIntegerProperty(p);
    }
    public int getItemNumber() {
        return itemNumber.get();
    }
    public void setItemNumber (int i)
    {
        this.itemNumber= new SimpleIntegerProperty(i);
    }
    
   public String toString() {
       return "name "+itemsName.get()+"numberOrder. "+ orderNumber.get()+ "itemNumber "+ itemNumber.get()+ "price "+itemPrice.get();
   }
    
}
