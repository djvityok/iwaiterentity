package iwaiter.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
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
     
     private ObservableList <TableBeans> tables=FXCollections.observableArrayList(
                new TableBeans(1),
                new TableBeans(2),
                new TableBeans(3),
                new TableBeans(4),
                new TableBeans(5));

     ObservableList<String> waiterString= FXCollections.<String>observableArrayList(); // for showing in waiterList 
     {
        for (WaiterBeans w: waiters)
        {
            waiterString.add(w.toString());
        }
     }
     
     // FOR one Waiter many Orders
     
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
                
                //orderNumbersList.add(val.getOrderNumber()); // dont know if this i need
                
                
                itemsForOrderMap.put(val.getOrderNumber(), FXCollections.<Integer>observableArrayList());// make empty items list for new Order
                
            }
            if (change.wasRemoved()) {
                
                final OrderBeans val = change.getValueRemoved();
                
                waitersMap.get(val.getWaitersName()).removeAll(val.getOrderNumber());
                
               // orderNumbersList.remove(val.getOrderNumber());
                itemsMap.removeListener(itemsMapChangeListener);
                for (Integer st: itemsForOrderMap.get(val.getOrderNumber())) {
                    itemsMap.remove(st); // remove all items for deleted order
                }
                itemsForOrderMap.remove(val.getOrderNumber());// remove deleted order from this map
                itemsMap.addListener(itemsMapChangeListener);
                
            }
        }
    };
    
    /****
     * Map and LIstener for ORder with items 
     */
    
   
    // FOR one Order many Items (i.e. DRINKS)
     final  ObservableMap<Integer, ObservableList<Integer>>itemsForOrderMap=FXCollections.observableMap(new TreeMap<Integer, ObservableList<Integer>>());
    
    
    // listner for concrete items for order number
    final MapChangeListener<Integer, ItemBeans> itemsMapChangeListener = new MapChangeListener<Integer, ItemBeans>() {
        @Override
        public void onChanged(MapChangeListener.Change<? extends Integer, ? extends ItemBeans> change) {
            if (change.wasAdded()) {
                final ItemBeans val = change.getValueAdded();
               
                itemsForOrderMap.get(val.getOrderNumber()).add(val.getItemNumber());
                
            }
            if (change.wasRemoved()) {
                final ItemBeans val = change.getValueRemoved();
                
                itemsForOrderMap.get(val.getOrderNumber()).removeAll(val.getItemNumber());
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
    final AtomicInteger itemsCounter = new AtomicInteger(0);
    final ObservableMap<Integer,ItemBeans> itemsMap;
    {
        final Map<Integer,ItemBeans> map = new TreeMap<Integer,ItemBeans>();
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
        
       // int i =issueCounter.get();
        final OrderBeans order = new OrderBeans(waitersName,issueCounter.incrementAndGet(),0,0,"no");
       
        orderMap.put(order.getOrderNumber(), order);
        return order;
    }
    
    public ItemBeans createItemFor(int orderNumber) {
        //int j=itemsCounter.get();
        int j=itemsCounter.incrementAndGet();
        
        final ItemBeans item = new ItemBeans(orderNumber,"newDrink"+orderNumber,((3+i++)+i),j);
       //System.out.println(item);
        itemsMap.put(j, item);
        return item;
    }
    
    //help method for Waiters creation
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

    public ObservableList<Integer> getOrderIds(String newWaiter) {
        return waitersMap.get(newWaiter);
    
    }

   public  OrderBeans getOrder(Integer id) {
        return orderMap.get(id);
    }
    
    public ItemBeans getItem(int st) {
        return itemsMap.get(st);
    }
   

    public ItemBeans getItemForOrder(int itemsNumber) {
        
        return itemsMap.get(itemsNumber);
    }

    public ObservableList<Integer> getItemsNumbers(int orderNumber) {
        
        return itemsForOrderMap.get(orderNumber);
    }
    
   
    public void deleteOrder (int orderNumber) {
        assert orderMap.containsKey(orderNumber);
        orderMap.remove(orderNumber);
    }
    
     public void deleteItem(int itemsNumber) {
        
         itemsMap.remove(itemsNumber);
        
    }
    
    public void addItem(ItemBeans item) {
        int ordNumber=getCurrentOrderNumber();
        int itemNumber=itemsCounter.incrementAndGet();
        itemsMap.put(itemNumber, new ItemBeans(ordNumber,item.getItemsName(),item.getItemPrice(),itemNumber));
      /* ItemBeans iB=itemsMap.get(itemName+"_"+ordNumber);
       if (iB!=null) { // if once more the same item added then we sum up the prices
           int priceCurrent = iB.getItemPrice();
           
           itemsMap.remove(itemName+"_"+ordNumber);
           
           itemsMap.put(itemName+"_"+ordNumber, new ItemBeans(ordNumber,itemName+"_"+ordNumber,priceCurrent+price));
           
       }else {
           itemsMap.put(itemName+"_"+ordNumber, new ItemBeans(ordNumber,itemName+"_"+ordNumber,price));
       }*/
        
    }
    
    public void setOrderNumber(int or) {
        this.orNumber=or;
      
    }
    
    public int getCurrentOrderNumber() {
        return orNumber;
    }

    public ObservableList<ItemBeans> getNewItems() {
        
          ObservableList<ItemBeans> newItems
            = FXCollections.observableArrayList(
                    new ItemBeans("GoodDrink", 2),
                    new ItemBeans("VeryGoodDrink", 54),
                    new ItemBeans("NotGoodDrink", 24),
                    new ItemBeans("CollDrink", 27),
                    new ItemBeans("BestDrink", 221));
          return newItems;
    
    }

     public int getOrderSum(int oNum) {
        int sum=0;
         for (Integer st:itemsForOrderMap.get(oNum)) {
             sum+= itemsMap.get(st).getItemPrice();
         }
        return sum;
    }
     
    public void saveItem (ItemBeans item,String newItemsName,int itemsPrice) {
        int itemNumber=item.getItemNumber();
        itemsMap.remove(itemNumber);
        itemsMap.put(itemNumber, new ItemBeans(item.getOrderNumber(),newItemsName,itemsPrice,item.getItemNumber()));
        
       
    }

    public ArrayList<Integer> getTablesNumber() {
        ArrayList<Integer>tab=new ArrayList<>();
        tables.stream().forEach((tabl) -> {
            tab.add(tabl.getTableNumber());
        });
       
        return tab;
    }

   
    
    
    
}
