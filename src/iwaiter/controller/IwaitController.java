/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package iwaiter.controller;

import iwaiter.model.ItemBeans;
import iwaiter.model.OrderBeans;
import iwaiter.model.iWaiterModel;
import iwaiter.main.Main;
import iwaiter.view.ScreenView;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author viktormagdych
 */
public class IwaitController implements Initializable,ControlledScreen {
    @FXML
    private ListView<String> list;
    @FXML
    private TableView<OrderBeans> table;
    @FXML
    private TableColumn<OrderBeans, Integer> colName;
    @FXML
    private TableColumn<OrderBeans, Integer> colTischNummer;
    @FXML
    private TableColumn<OrderBeans, Integer> colSummer;
    @FXML
    private TableColumn<OrderBeans, String> colAbgeshloss;
    @FXML
    private Button newOrder;
    @FXML
    private Button deleteOrder;
    @FXML
    private Button printBill;
    @FXML
    private Button printAll;
    @FXML
    private Button abschliessen;
    @FXML
    private ComboBox<Integer> changetisch;
    @FXML
    private AnchorPane details;
    @FXML
    private TableView<ItemBeans> itemsTable;
    @FXML
    private TableColumn<ItemBeans,String> colGetränk;
    @FXML
    private TableColumn<ItemBeans, Integer> colPreisGetränk;
    @FXML
    private Button newItem;
    @FXML
    private Button deleteItem;
    @FXML
    private TextField changeGetränkname;
    @FXML
    private TextField changeGetränkpreis;
    @FXML
    private Button importDate;
     @FXML
    private Button calcOrder;
    @FXML
    private Button saveItemButton;
    @FXML
    private Text  textName;
    /**
     * Initializes the controller class.
     */
    
    // The list of Order IDs relevant to the selected project. Can be null
    // if no project is selected. This list is obtained from the model.
    // This is a live list, and we will react to its changes by removing
    // and adding orders objects to/from our table widget.
     ScreenView mainScreenController;
    private ObservableList<Integer> displayedOrder; // list of  order that we show currently and that we listen for added or delete of order
    private ObservableList<Integer> displayedItems=null;//list of items that were showed and that we listen if we there add or delet the item
    ObservableList<ItemBeans> itemTableSelection=null;  //list of Items thet we recive from Itemstable .getSelectionModel()
     ObservableList<OrderBeans> tableSelection=null;  // list of orders that we recieve from table.getSelectionModel().
            
    ObservableList<Integer> tablesNumber; //list or tablesNumber make in configCOmbobox () method
    
    
      iWaiterModel beanModel;
    
   ObservableList<String> waiterView = FXCollections.observableArrayList(); // it will be fiiled  in connectToService()
    
    private TextField statusValue = new TextField();
    
    final ObservableList<OrderBeans> tableContent = FXCollections.observableArrayList();
    final ObservableList<ItemBeans> tableContentItems=FXCollections.observableArrayList();
    
   
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTextEffect();
        
       
        
        configureTable();
        
        if (list != null) {
            list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            list.getSelectionModel().selectedItemProperty().addListener(waiterSelected);
           
           
            configureButtons();
            configureItemsButton();
        }
    }    
    
    public void setTextEffect() {
        Blend blend = new Blend();
        blend.setMode(BlendMode.MULTIPLY);
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
        textName.setFill(Color.RED);
        textName.setFont(Font.font(null, FontWeight.BOLD, 30));
        Reflection r = new Reflection();
        r.setFraction(0.7f);
        blend.setBottomInput(r);
        blend.setTopInput(ds);
        textName.setEffect(blend);
        textName.setCache(true);
    }
    
    @Override
    public void setScreenParent(ScreenView msc) {
        mainScreenController = msc;
        
    }
    
    private void configureCombobox() {
      
       ArrayList<Integer>tables=beanModel.getTablesNumber();
        tablesNumber=FXCollections.observableArrayList(tables);
        changetisch.setItems(tablesNumber);
        changetisch.setDisable(true);
    }
    
    
    private void configureTable() {
        colName.setCellValueFactory(new PropertyValueFactory<OrderBeans, Integer>("orderNumber"));
        colTischNummer.setCellValueFactory(new PropertyValueFactory<OrderBeans, Integer>("tableNumber"));
        colSummer.setCellValueFactory(new PropertyValueFactory<OrderBeans, Integer>("billSum"));
        colAbgeshloss.setCellValueFactory(new PropertyValueFactory<OrderBeans, String>("finilised"));
        
        //items table
        colGetränk.setCellValueFactory(new PropertyValueFactory<ItemBeans,String>("itemsName"));
        colPreisGetränk.setCellValueFactory(new PropertyValueFactory<ItemBeans,Integer>("itemPrice"));
        
       itemsTable.setItems(tableContentItems);
        table.setItems(tableContent);
        
         tableSelection = table.getSelectionModel().getSelectedItems();
        tableSelection.addListener(tableSelectionChanged);
        
        itemTableSelection = itemsTable.getSelectionModel().getSelectedItems();
        itemTableSelection.addListener(itemsTableSelectionChanged);
    }
    
    
    // This listener listen to changes in the table widget selection and
    // update the  button state accordingly.
    private final ListChangeListener<OrderBeans> tableSelectionChanged =
            new ListChangeListener<OrderBeans>() {

                @Override
                public void onChanged(ListChangeListener.Change<? extends OrderBeans> c) {
                    
                    changeGetränkname.setText("");
                        changeGetränkpreis.setText("");
                        if((table.getSelectionModel().getSelectedItem()!=null)&&
                                (table.getSelectionModel().getSelectedItem().getFinilised()=="yes")) {
                   configureButtonOfFinilisedOrder();
                    if (!table.getSelectionModel().isEmpty()){
                        if(displayedItems!=null)
                        displayedItems.removeListener(itemsListener);
                        
                        showItemsForOrder();
                        newItem.setDisable(true);
                        itemsTable.setDisable(true);
                        
                    }
                        } else {// if not finilised
                            
                            changetisch.setDisable(false);
                        newOrder.setDisable(false);
                        deleteOrder.setDisable(false);
                        printBill.setDisable(false);
                        printAll.setDisable(false);
                        abschliessen.setDisable(false);
                        calcOrder.setDisable(true);
                         deleteItem.setDisable(true);
                        saveItemButton.setDisable(true);
                        itemsTable.setDisable(false);

                          if (!table.getSelectionModel().isEmpty()){
                             if(displayedItems!=null)
                            displayedItems.removeListener(itemsListener);                        
                            showItemsForOrder();
                             }
                        }
                
                }
                
            };
    
    public void showItemsForOrder() {
    final OrderBeans selectedBean = getSelectedOrder();
      
     itemsTable.getItems().clear();
        if (selectedBean!=null) {
            
            displayedItems = beanModel.getItemsNumbers(selectedBean.getOrderNumber());
            //System.out.println("displayed "+displayedItems);
            for (Integer id : displayedItems) {
                final ItemBeans item = beanModel.getItemForOrder(id);
                
                itemsTable.getItems().add(item);
            }
            newItem.setDisable(false);            
            displayedItems.addListener(itemsListener); // like for displaydOrder listener from beanmodel
            
        }
    }
    
    
    // This listener listen to changes in the Itemstable widget selection and
    // update the DeleteIssue button state accordingly.
    private final ListChangeListener<ItemBeans> itemsTableSelectionChanged =
            new ListChangeListener<ItemBeans>() {

                @Override
                public void onChanged(ListChangeListener.Change<? extends ItemBeans> c) {
                    
                    
                   deleteItem.setDisable(false);
                   saveItemButton.setDisable(false);
                   
                          changeGetränkname.setText("");
                        changeGetränkpreis.setText("");
                    if (itemsTable.getSelectionModel().getSelectedItem()!=null){
                        
                    changeGetränkname.setText(itemsTable.getSelectionModel().getSelectedItem().getItemsName());
                    changeGetränkpreis.setText("0");
                    }
                   
                }
                
            };
    
    private final ListChangeListener<Integer> itemsListener = new ListChangeListener<Integer>() {

        @Override
        public void onChanged(ListChangeListener.Change<? extends Integer> c) {
          
            while (c.next()) {
                if (c.wasAdded() || c.wasReplaced()) {
                    changeGetränkname.setText("");
                    changeGetränkpreis.setText("");
                     
                    if(displayedItems!=null)
                        displayedItems.removeListener(itemsListener); // because of many times listener problems
                    showItemsForOrder();
                     break;
                }
                if (c.wasRemoved() || c.wasReplaced()) {
                    for (Integer p : c.getRemoved()) {
                        
                        ItemBeans removed = null;
                        
                        // Order already removed:
                        // we can't use model.getOrder(orderNumber) to get it.
                        // we need to loop over the table content instead.
                        // Then we need to remove it - but outside of the for loop
                        // to avoid ConcurrentModificationExceptions.
                        for (ItemBeans t : itemsTable.getItems()) {
                            if (t.getItemNumber()==p) {
                                removed = t;
                                break;
                            }
                        }
                        if (removed != null) {
                            
                            itemsTable.getItems().remove(removed);
                        }
                    }
                }
            }
        }
    };
    
    public ItemBeans getSelectedItem () {
        if (beanModel != null && itemsTable != null) {
            List<ItemBeans> selectedItems = itemsTable.getSelectionModel().getSelectedItems();
            if (selectedItems.size() == 1) {
                final ItemBeans selectedItem = selectedItems.get(0);
                return selectedItem;
            }
        }
        
        return null;
    }
    
    public OrderBeans getSelectedOrder() {
        if (beanModel != null && table != null) {
            List<OrderBeans> selectedOrders = table.getSelectionModel().getSelectedItems();
            if (selectedOrders.size() == 1) {
                final OrderBeans selectedOrder = selectedOrders.get(0);
                return selectedOrder;
            }
        }
        
        return null;
    }
    
    public String getSelectedWaiter() {
        if (beanModel != null && list != null) {
            final ObservableList<String> selectedWaiterItem = list.getSelectionModel().getSelectedItems();
            final String selectedProject = selectedWaiterItem.get(0);
            return selectedProject;
        }
        return null;
    }
    
     public void connectToService(  iWaiterModel beanModel) {
        
            this.beanModel = beanModel;
            
            waiterView= beanModel.getWaitersString();
          
        List<String> sortedProjects = new ArrayList<>(waiterView);
        Collections.sort(sortedProjects);
        waiterView.clear();
        waiterView.addAll(sortedProjects);
        list.setItems(waiterView);
        configureCombobox();
    }
    
     /**
     * Listen to changes in the list selection, and updates the table widget and
     * DeleteOrder and NewOrder buttons accordingly.
     */
     
     private final ChangeListener<String> waiterSelected = (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
         itemsTable.getItems().clear();
         waiterUnselected(oldValue);
         waiterSelected(newValue);
         configureButtons();
         configureItemsButton();
         newOrder.setDisable(false);
         
         changeGetränkname.setText("");
         changeGetränkpreis.setText("");
    };
    
     
    // Called when a waiter is unselected.
   private void waiterUnselected(String oldWaiter) {
        if (oldWaiter != null) {
            
            displayedOrder.removeListener(orderListener);
            displayedOrder = null;
            //table.getSelectionModel().clearSelection();
            table.getItems().clear();
            changetisch.setDisable(true);
            
        }
    }

    // Called when a waiter is selected.
    private void waiterSelected(String newWaiter) {
       int price=0;
        if (newWaiter != null) {
            table.getItems().clear();
            displayedOrder = beanModel.getOrderIds(newWaiter);
            for (Integer id : displayedOrder) {
                final OrderBeans order = beanModel.getOrder(id);
               
                for (int item:beanModel.getItemsNumbers(id)) {// calculate price from all item in this order
                    
                    price+=beanModel.getItemForOrder(item).getItemPrice();
                }
                order.setBillSum(price);
                table.getItems().add(order);
                price=0;
            }
            displayedOrder.addListener(orderListener); // in fact we added listener to our Observable list that in waiterMap in model as value
            if (newOrder != null) {
                newOrder.setDisable(false);
            }
            
        }
    }
    
    // This listener will listen to changes in the displayed orders list, 
    //that we recieve from model, that in waitersMap as value, and when we 
    //add or remove somthing in model class, than this listner will reacted
    // and update our table widget in consequence.
    private final ListChangeListener<Integer> orderListener = new ListChangeListener<Integer>() {

        @Override
        public void onChanged(ListChangeListener.Change<? extends Integer> c) {
           
            changetisch.setDisable(true);
            
            if (table == null) {
                return;
            }
            while (c.next()) {
                if (c.wasAdded() || c.wasReplaced()) {
                    for (int p : c.getAddedSubList()) {
                        
                        changeGetränkname.setText("");
                         changeGetränkpreis.setText("");
                        table.getItems().add(beanModel.getOrder(p));
                    }
                }
                if (c.wasRemoved() || c.wasReplaced()) {
                    for (int p : c.getRemoved()) {
                        
                        OrderBeans removed = null;
                        
                        // Order already removed:
                        // we can't use model.getOrder(orderNumber) to get it.
                        // we need to loop over the table content instead.
                        // Then we need to remove it - but outside of the for loop
                        // to avoid ConcurrentModificationExceptions.
                        for (OrderBeans t : table.getItems()) {
                            if (t.getOrderNumber()==p) {
                                removed = t;
                                break;
                            }
                        }
                        if (removed != null) {
                            changeGetränkname.setText("");
                            changeGetränkpreis.setText("");
                            table.getItems().remove(removed);
                        }
                    }
                }
            }
        }
    };
    
    
    private void configureButtons() {
       
     newOrder.setDisable(true);
     deleteOrder.setDisable(true);
     printBill.setDisable(true);
     printAll.setDisable(true);
     abschliessen.setDisable(true);
     calcOrder.setDisable(true);
     itemsTable.setDisable(false);
    }

    private void configureItemsButton() {
   
    newItem.setDisable(true);
    deleteItem.setDisable(true);
    saveItemButton.setDisable(true);
    itemsTable.setDisable(false);
    

}
    private void configureButtonOfFinilisedOrder() {
        newOrder.setDisable(false);
     deleteOrder.setDisable(true);
     printBill.setDisable(false);
     printAll.setDisable(false);
     abschliessen.setDisable(true);
     calcOrder.setDisable(true);
     newItem.setDisable(true);
    deleteItem.setDisable(true);
    saveItemButton.setDisable(true);
    changetisch.setDisable(true);
    }
    

    @FXML
    private void newOrderFired(ActionEvent event) {
        
        final String selectedProject = getSelectedWaiter();
        if (beanModel != null && selectedProject != null) {
            OrderBeans newOrder = beanModel.createOrderFor(selectedProject);
            if (table != null) {
                // Select the newly created issue.
                table.getSelectionModel().clearSelection();
                table.getSelectionModel().select(newOrder);
            }
        }
    }

    @FXML
    private void deleteOrderFired(ActionEvent event) {
        
        tableSelection.removeListener(tableSelectionChanged);
         final OrderBeans selectedOrder = getSelectedOrder();
        if (beanModel != null && selectedOrder != null && table != null) {
            // We create a copy of the current selection: we can't delete
            //    order while looping over the live selection, since
            //    deleting selected order will modify the selection.
            itemsTable.getItems().clear();
            final List<?> selectedOrderList = new ArrayList<Object>(table.getSelectionModel().getSelectedItems());
            for (Object o : selectedOrderList) {
                
                if (o instanceof OrderBeans) {
                    beanModel.deleteOrder(((OrderBeans) o).getOrderNumber());
                    
                }
            }
            
            table.getSelectionModel().clearSelection();
            itemsTable.getItems().clear();
            changetisch.setDisable(true);
            tableSelection.addListener(tableSelectionChanged);
        }
        
    }

    @FXML
    //calculate the price of order
    private void calculateOrderFired(ActionEvent event) {
        OrderBeans selectedBean = getSelectedOrder();
           if (selectedBean!=null){
               int newSum= beanModel.getOrderSum(getSelectedOrder().getOrderNumber());
               selectedBean.setBillSum(newSum);
           }
           
            int selectedRowIndex = table.getSelectionModel().getSelectedIndex();
         itemsTable.getItems().clear();
           
         displayedOrder = beanModel.getOrderIds(getSelectedOrder().getWaitersName());
         table.getItems().clear();
            for (Integer id : displayedOrder) {
                final OrderBeans order = beanModel.getOrder(id);
                table.getItems().add(order);
            }  
         if (table!=null)
        table.getSelectionModel().select(selectedRowIndex);
         
        
        
    }

    @FXML
    private void printOrderFired(ActionEvent event) {
    }

    @FXML
    private void printAllFired(ActionEvent event) {
    }

    @FXML
    private void finiliseOrderFired(ActionEvent event) {
        OrderBeans selectedBean = getSelectedOrder();
           if (selectedBean!=null){
               selectedBean.setFinilised("yes");
           }
           
          //  int selectedRowIndex = table.getSelectionModel().getSelectedIndex();
         itemsTable.getItems().clear();
           
         displayedOrder = beanModel.getOrderIds(getSelectedOrder().getWaitersName());
         table.getItems().clear();
         itemsTable.getItems().clear();
            for (Integer id : displayedOrder) {
                final OrderBeans order = beanModel.getOrder(id);
                table.getItems().add(order);
            }  
         
         configureButtons();
         configureItemsButton();
         changetisch.setDisable(true);
        
    }

    @FXML
    private void changeTischNumber(ActionEvent event) {
          OrderBeans selectedBean = getSelectedOrder();
           if (selectedBean!=null){
               selectedBean.setTableNumber(changetisch.getSelectionModel().getSelectedItem());
           }
           
            int selectedRowIndex = table.getSelectionModel().getSelectedIndex();
         itemsTable.getItems().clear();
           
         displayedOrder = beanModel.getOrderIds(getSelectedOrder().getWaitersName());
         table.getItems().clear();
         itemsTable.getItems().clear();
            for (Integer id : displayedOrder) {
                final OrderBeans order = beanModel.getOrder(id);
                table.getItems().add(order);
            }  
         if (table!=null)
        table.getSelectionModel().select(selectedRowIndex);
         changetisch.setValue(null);
        
    }

    @FXML
    private void newItemFired(ActionEvent event) {
        changeGetränkname.setText("");
    changeGetränkpreis.setText("");
        if((getSelectedOrder().getOrderNumber())>0){
        beanModel.setOrderNumber(getSelectedOrder().getOrderNumber());
        mainScreenController.setScreen(Main.screen2ID);
        calcOrder.setDisable(false);
        }
        
    
    }

    @FXML
    private void deleteItemFired(ActionEvent event) {
        changeGetränkname.setText("");
    changeGetränkpreis.setText("");
        itemTableSelection.removeListener(itemsTableSelectionChanged);
         final ItemBeans selectedItem = getSelectedItem();
        if (beanModel != null && selectedItem != null && itemsTable != null) {
            // We create a copy of the current selection: we can't delete
            //    item while looping over the live selection, since
            //    deleting selected item will modify the selection.
            //itemsTable.getItems().clear();
            final List<?> selectedOrderList = new ArrayList<Object>(itemsTable.getSelectionModel().getSelectedItems());
            for (Object o : selectedOrderList) {
                
                if (o instanceof ItemBeans) {
                    beanModel.deleteItem(((ItemBeans) o).getItemNumber());
                    
                }
            }
            itemsTable.getSelectionModel().clearSelection();
            itemTableSelection.addListener(itemsTableSelectionChanged);
            calcOrder.setDisable(false);
        }
    }


    @FXML
    private void improtData(ActionEvent event) {
       changeGetränkname.setText("");
    changeGetränkpreis.setText("");
          
        
    }

    @FXML
    private void saveItemFired(ActionEvent event) {
        beanModel.saveItem(itemsTable.getSelectionModel().getSelectedItem(), 
                 
                changeGetränkname.getText(), Integer.parseInt(changeGetränkpreis.getText()));
        
        changeGetränkname.setText("");
    changeGetränkpreis.setText("");
    calcOrder.setDisable(false);
    
    }

}
