/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package iwaiter;

/**
 *
 * @author viktormagdych
 */
public interface ControlledScreen {
    
    public void setScreenParent(ScreensController msc);
    public void connectToService(iWaiterModel mod);
}
