/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package groupbuyapp;

import javax.swing.SwingUtilities;

import groupbuyapp.Client.MainFrame;

/**
 *
 * @author user
 */
public class GroupbuyApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new MainFrame();
            }
            
        });
    }
    
}
