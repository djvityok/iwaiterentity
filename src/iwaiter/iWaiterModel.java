package iwaiter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author viktormagdych
 */
public class iWaiterModel {
    int i=1;
      int orNumber; // for make the new Items
    
    private ArrayList<Integer> orderNumbersList= new ArrayList();
    
     private  ObservableList<WaiterBeans> waiters
            = FXCollections.observableArrayList(
                    new WaiterBeans("Jacob", "Smith"),
                    new WaiterBeans("Isabella", "Johnson"),
                    new WaiterBeans("Ethan", "Williams"),
                    new WaiterBeans("Emma", "Jones"),
                    new WaiterBeans("Michael", "Brown"));

     ObservableList<String> waiterString= FXCollections.<String>observableArrayList();
     {
        for (WaiterBeans w: waiters)
        {
            waiterString.add(w.toString());
        }
     }
     // map with waiters names and List of Orders
     
     final ObservableMap<String, ObservableList<Integer>> waitersMap;
    {
        
        final Map<String, ObservableList<Integer>> map = new TreeMap<String, ObservableList<Integer>>();
        waitersMap = FXCollections.observableMap(map);
        for (String s : newList("Jacob Smith", "Isabella Johnson", "Ethan Williams", "Michael Brown","Emma Jones")) {
            waitersMap.put(s, FXCollections.<Integer>observableArrayList());
        }
    }

    // You create new order by adding a OrderBean instance to the waitersMap.
    // the new id will be automatically added to the corresponding list in
    // the projectsMap.
    //
    final MapChangeListener<Integer, OrderBeans> orderMapChangeListener = new MapChangeListener<Integer, OrderBeans>() {
        @Override
        public void onChanged(MapChangeListener.Change<? extends Integer, ? extends OrderBeans> change) {
            if (change.wasAdded()) {
                final OrderBeans val = change.getValueAdded();
                waitersMap.get(val.getWaitersName()).add(val.getOrderNumber());
                
                orderNumbersList.add(val.getOrderNumber()); // dont know if this i need
                
                
                itemsForOrderMap.put(val.getOrderNumber(), FXCollections.<String>observableArrayList());
                
            }
            if (change.wasRemoved()) {
                
                final OrderBeans val = change.getValueRemoved();
                
                waitersMap.get(val.getWaitersName()).removeAll(val.getOrderNumber());
                
               // orderNumbersList.remove(val.getOrderNumber());
                itemsMap.removeListener(itemsMapChangeListener);
                for (String st: itemsForOrderMap.get(val.getOrderNumber())) {
                    itemsMap.remove(st);
                }
                itemsForOrderMap.remove(val.getOrderNumber());
                itemsMap.addListener(itemsMapChangeListener);
                
            }
        }
    };
    
    /****
     * Map and LIstener for ORder with items 
     */
    
   
    // map with number of order and list of items (names String List) from this order
     final  ObservableMap<Integer, ObservableList<String>>itemsForOrderMap=FXCollections.observableMap(new TreeMap<Integer, ObservableList<String>>());
    
        
         /*static Map<Integer, ObservableList<String>> map = new TreeMap<Integer, ObservableList<String>>();
        itemsForOrderMap = FXCollections.observableMap(map);*/
       
    

    
    
    // listner for concrete items for order number
    final MapChangeListener<String, ItemBeans> itemsMapChangeListener = new MapChangeListener<String, ItemBeans>() {
        @Override
        public void onChanged(MapChangeListener.Change<? extends String, ? extends ItemBeans> change) {
            if (change.wasAdded()) {
                final ItemBeans val = change.getValueAdded();
                //System.out.println("fff "+ itemsForOrderMap);
               // System.out.println("ww "+ val.getOrderNumber());
                System.out.println("in Itemmaplistener "+ val.getItemsName());
                itemsForOrderMap.get(val.getOrderNumber()).add(val.getItemsName());
                //System.out.println("fff nachher "+ itemsForOrderMap);
                
            }
            if (change.wasRemoved()) {
                final ItemBeans val = change.getValueRemoved();
                System.out.println(" itemsbeans "+ val);
                itemsForOrderMap.get(val.getOrderNumber()).remove(val.getItemsName());
            }
        }
    };
    
    public ArrayList<Integer> getOrderNumbers(){
        return orderNumbersList;
    }
    
      
    final AtomicInteger issueCounter = new AtomicInteger(0);
    final ObservableMap<Integer, OrderBeans> orderMap;
    
    {
        final Map<Integer, OrderBeans> map = new TreeMap<Integer, OrderBeans>();
        orderMap = FXCollections.observableMap(map);
        orderMap.addListener(orderMapChangeListener);
        OrderBeans ob;
        ob = createOrderFor("Jacob Smith");
        ob = createOrderFor("Isabella Johnson");
        ob = createOrderFor("Ethan Williams");
        ob = createOrderFor("Michael Brown");
        ob = createOrderFor("Emma Jones");
        ob = createOrderFor("Ethan Williams");
        ob = createOrderFor("Michael Brown");
        ob = createOrderFor("Emma Jones");
        
      
    }
    
    final ObservableMap<String,ItemBeans> itemsMap;
    {
        final Map<String,ItemBeans> map = new TreeMap<String,ItemBeans>();
        itemsMap = FXCollections.observableMap(map);
        itemsMap.addListener(itemsMapChangeListener);
        ItemBeans ib;
        ib= createItemFor(2);
        ib= createItemFor(1);
        
        ib= createItemFor(3);
        ib= createItemFor(4);
        ib= createItemFor(2);
        ib= createItemFor(1);
        
        ib= createItemFor(3);
        ib= createItemFor(4);
        ib= createItemFor(5);
        ib= createItemFor(5);
        
        ib= createItemFor(6);
        ib= createItemFor(7);
    }
    

    
    public OrderBeans createOrderFor(String waitersName) {
        
        int i =issueCounter.get();
        final OrderBeans order = new OrderBeans(waitersName,issueCounter.incrementAndGet(),0,0,"no");
       
        orderMap.put(order.getOrderNumber(), order);
        return order;
    }
    
    public ItemBeans createItemFor(int orderNumber) {
        
        
        final ItemBeans item = new ItemBeans(orderNumber,"Bear"+orderNumber+"_"+i++,(3+i));
       
        itemsMap.put(item.getItemsName(), item);
        return item;
    }
    
    
    private static <T> List<T> newList(T... items) {
    return Arrays.asList(items);
    }
        
    public ObservableList<WaiterBeans> getWaiters() {
        return waiters;
    }
    
    //list for ListView
    public ObservableList<String> getWaitersString() {
        
        return waiterString;
        
    }

    ObservableList<Integer> getOrderIds(String newWaiter) {
        return waitersMap.get(newWaiter);
    
    }

    OrderBeans getOrder(Integer id) {
        return orderMap.get(id);
    }
    
    ItemBeans getItem(String st) {
        return itemsMap.get(st);
    }
   

    ItemBeans getItemForOrder(String itemsName) {
        
        return itemsMap.get(itemsName);
    }

    ObservableList<String> getItemsNames(int orderNumber) {
        
        return itemsForOrderMap.get(orderNumber);
    }
    
   
    public void deleteOrder (int orderNumber) {
        assert orderMap.containsKey(orderNumber);
        orderMap.remove(orderNumber);
    }
    
     void deleteItem(String itemsName) {
         System.out.println("name " + itemsName);
         System.out.println("mapitem "+ itemsMap);
         itemsMap.remove(itemsName);
        
    }
    
    public void addItem(String itemName, int price) {
        int ordNumber=getCurrentOrderNumber();
       ItemBeans iB=itemsMap.get(itemName+"_"+ordNumber);
       if (iB!=null) { // if once more the same item added then we sum up the prices
           int priceCurrent = iB.getItemPrice();
           
           itemsMap.remove(itemName+"_"+ordNumber);
           
           itemsMap.put(itemName+"_"+ordNumber, new ItemBeans(getCurrentOrderNumber(),itemName+"_"+ordNumber,priceCurrent+price));
           
       }else {
           itemsMap.put(itemName+"_"+ordNumber, new ItemBeans(getCurrentOrderNumber(),itemName+"_"+ordNumber,price));
       }
        
    }
    
    public void setOrderNumber(int or) {
        this.orNumber=or;
        System.out.println("ordeeer " +getCurrentOrderNumber());
    }
    
    public int getCurrentOrderNumber() {
        return orNumber;
    }

    public ObservableList<ItemBeans> getNewItems() {
        
          ObservableList<ItemBeans> newItems
            = FXCollections.observableArrayList(
                    new ItemBeans("Jacob", 2),
                    new ItemBeans("Jacob1", 54),
                    new ItemBeans("Jacob2", 24),
                    new ItemBeans("Jacob3", 27),
                    new ItemBeans("Jacob4", 221));
          return newItems;
    
    }

     public int getOrderSum(int oNum) {
        int sum=0;
         for (String st:itemsForOrderMap.get(oNum)) {
             sum+= itemsMap.get(st).getItemPrice();
         }
        return sum;
    }
    public void saveItem (int orderNumber,String oldItemsNAme,String newItemsName,int itemsPrice) {
        
        System.out.println(orderNumber+" "+oldItemsNAme+" "+newItemsName+" "+itemsPrice);
         ItemBeans iB=itemsMap.get(oldItemsNAme);
         System.out.println(" staryyitem" + iB);
       if (iB!=null) { // if once more the same item added then we sum up the prices
           
           
           itemsMap.remove(oldItemsNAme);
           //iB.setItemPrice(itemsPrice);
           itemsMap.put(newItemsName, new ItemBeans(orderNumber,newItemsName,itemsPrice));
           
       }else {
           itemsMap.put(newItemsName+orderNumber, new ItemBeans(orderNumber,newItemsName+orderNumber,itemsPrice));
       }
        
        /*ItemBeans item= getItem(oldItemsNAme);
        item.setItemsName(newItemsName);
        item.setItemPrice(itemsPrice);*/
    }

   
    
    
    
}
