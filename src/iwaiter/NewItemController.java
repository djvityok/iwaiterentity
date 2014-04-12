/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package iwaiter;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author viktormagdych
 */
public class NewItemController implements Initializable,ControlledScreen {
    @FXML
    private TableView<ItemBeans> itemTable;
    @FXML
    private TableColumn<ItemBeans, String> itemName;
    @FXML
    private TableColumn<ItemBeans, Integer> itemPrice;

    ScreensController mainScreenController;
    iWaiterModel beanModel;
     
    ObservableList<ItemBeans> tableContentItems=FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        
        
    }    

    @Override
    public void setScreenParent(ScreensController msc) {

        mainScreenController = msc;
        
    
    }
    public void fillTable() {
        itemName.setCellValueFactory(new PropertyValueFactory<ItemBeans,String>("itemsName"));
        itemPrice.setCellValueFactory(new PropertyValueFactory<ItemBeans,Integer>("itemPrice"));
        tableContentItems = beanModel.getNewItems();
       itemTable.setItems(tableContentItems);
       
    }
    
    public void connectToService(iWaiterModel beanModel1) {
       System.out.println("item "+ beanModel1);
           
           beanModel=beanModel1;
           System.out.println("item2 "+ beanModel);
           // itemController.connectToService(beanModel);
            fillTable();
        
    }

    @FXML
    private void klik(MouseEvent event) {
        beanModel.addItem(itemTable.getSelectionModel().getSelectedItem().getItemsName(), itemTable.getSelectionModel().getSelectedItem().getItemPrice());
       mainScreenController.setScreen(Main.screen1ID);
    }
    
}
