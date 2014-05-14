/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package iwaiter.controller;

import iwaiter.model.ItemBeans;
import iwaiter.model.iWaiterModel;
import iwaiter.main.Main;
import iwaiter.view.ScreenView;
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

    ScreenView mainScreenController;
    iWaiterModel beanModel;
     
    ObservableList<ItemBeans> tableContentItems=FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        
        
    }    

    @Override
    public void setScreenParent(ScreenView msc) {

        mainScreenController = msc;
        
    
    }
    public void fillTable() {
        itemName.setCellValueFactory(new PropertyValueFactory<ItemBeans,String>("itemsName"));
        itemPrice.setCellValueFactory(new PropertyValueFactory<ItemBeans,Integer>("itemPrice"));
        tableContentItems = beanModel.getNewItems();
       itemTable.setItems(tableContentItems);
       
    }
    
    public void connectToService(iWaiterModel beanModel1) {
      
           
           beanModel=beanModel1;
          
            fillTable();
        
    }

    @FXML
    private void klik(MouseEvent event) {
        beanModel.addItem(itemTable.getSelectionModel().getSelectedItem());
       mainScreenController.setScreen(Main.screen1ID);
    }
    
}
