/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package iwaiter.controller;

import iwaiter.controller.*;
import iwaiter.model.iWaiterModel;
import iwaiter.view.ScreenView;

/**
 *
 * @author viktormagdych
 */
public interface ControlledScreen {
    
    public void setScreenParent(ScreenView msc);
    public void connectToService(iWaiterModel mod); // for one beanmodel object for every sceen
}
