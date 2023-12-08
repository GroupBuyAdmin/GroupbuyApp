package groupbuyapp.Client.Center.Content.BrowseGroupbuys;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.security.PublicKey;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import groupbuyapp.Misc.ColorPalette.GbuyColor;

public class BrowseGroupbuys extends JPanel{
    public BrowseGroupbuys(){
        setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
         setLayout(new BorderLayout());
 
          JScrollPane container = new JScrollPane(Container());  
          container.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
           container.setBorder(null);


          add(Header(),BorderLayout.NORTH);
          add(container,BorderLayout.CENTER);  


      
    }

    public JPanel Header(){
         JPanel panel = new JPanel();
         Dimension dim = getPreferredSize();
         dim.height=80;
         panel.setPreferredSize(dim);
         JLabel title = new JLabel("Browse GroupBuy");
        panel.setBackground(Color.decode("#FFFFFF"));


         panel.add(title);

            return panel;
    }


     public JPanel Container(){
          JPanel panel = new JPanel();
          panel.setBackground(Color.decode("#FFFFFF"));
          panel.setLayout(new GridBagLayout());
         
  
           GridBagConstraints gbc = new GridBagConstraints();
           gbc.gridx = 0;
           gbc.gridy =0;   
           gbc.insets = new Insets(20, 0, 30, 0);
           panel.add(CategoryContainer("Catgory1"),gbc);
           gbc.gridy++;
           panel.add(CategoryContainer("Catgory2"),gbc);
           gbc.gridy++;
           panel.add(CategoryContainer("Catgory3"),gbc);
           gbc.gridy++;
           panel.add(CategoryContainer("Catgory4"),gbc);


          return panel;
     }


       //for category and their products
     public JPanel CategoryContainer(String catname){
           JPanel panel = new JPanel();
           panel.setBackground(Color.decode("#FFFFFF"));
           Dimension dim = getPreferredSize();
           dim.width =1000;
           dim.height= 350;
           panel.setPreferredSize(dim);

           
           panel.setLayout(new BorderLayout());
           
           JScrollPane scrollPane = new JScrollPane(DisplayProducts());  
           scrollPane.setBorder(null);
           scrollPane.setViewportBorder(null);
           
           panel.add(containerheader(catname),BorderLayout.NORTH);
           panel.add(scrollPane,BorderLayout.CENTER);
           
           
           return panel;
     }

     public JPanel containerheader(String catname){
       JPanel panel = new JPanel();
        panel.setBackground(Color.decode("#ffffff"));
       JLabel catName = new JLabel(catname);
       catName.setFont(new Font(catName.getName(),Font.ROMAN_BASELINE,18));
       JLabel seAll = new JLabel("See All");
       seAll.setFont(new Font(catName.getName(),Font.BOLD,15));
       seAll.setForeground(Color.red);

       panel.setLayout(new GridBagLayout());

       GridBagConstraints gbc=  new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(catName, gbc);
        gbc.gridx = 1; 
        gbc.weightx = 1.0;  
        gbc.anchor = GridBagConstraints.EAST;   
        panel.add(seAll, gbc);


       return panel;
     }

     public JPanel DisplayProducts(){
          JPanel panel = new JPanel();
            panel.setBackground(Color.decode("#FFFFFF"));

          panel.setLayout(new GridBagLayout());

          GridBagConstraints gbc = new GridBagConstraints();
           gbc.gridx = 0;
           gbc.gridy =0;
           gbc.insets = new Insets(0, 10, 0, 20);
           panel.add(productPanel("Product"+1,999,"src/groupbuyapp/Client/Center/Content/BrowseGroupbuys/customImage/nft.jpg","Ongoing"),gbc);
           gbc.gridx++;
           for(int i=2; i<=20;i++){
          
              panel.add(productPanel("Product"+i,999,"src/groupbuyapp/Client/Center/Content/BrowseGroupbuys/customImage/nft.jpg","Ongoing"),gbc);
              gbc.gridx++;
           }
          return panel;
     }



     // products panel
     public  JPanel productPanel(String name , int price,String imagePath,String status){
           JPanel panel = new JPanel();
           panel.setBackground(Color.decode("#ffffff"));
             panel.setLayout(new GridBagLayout());
            
           Dimension dim = getPreferredSize();
           dim.width =230;
           dim.height= 280;
           panel.setPreferredSize(dim);

           JLabel image = new JLabel();
         
          

           ImageIcon imageIcon = new ImageIcon(imagePath);
           Image analyticsScaledImage = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
           ImageIcon setAnalyticsIcon = new ImageIcon(analyticsScaledImage);  
           image.setIcon(setAnalyticsIcon);  

           GridBagConstraints gbc = new GridBagConstraints();
           gbc.gridx= 0;
           gbc.gridy =0;
           panel.add(image,gbc);
           gbc.gridy++;
           panel.add(details(name,price,status),gbc);

           return panel;
     }


     public JPanel details(String  name, int price, String stutus){
          JPanel panel = new JPanel();
          panel.setBackground(Color.decode("#ffffff"));
          panel.setLayout(new GridBagLayout());

           JLabel prodName =  new JLabel(name);
           prodName.setForeground(Color.decode("#0A0A0A"));
           prodName.setFont(new Font(prodName.getName(),Font.TRUETYPE_FONT,18));
           JLabel prodPrice = new JLabel(price+"");    
           prodPrice.setFont(new Font(prodName.getName(),Font.ROMAN_BASELINE,15));
           JLabel status = new JLabel(stutus);
    

           GridBagConstraints gbc = new GridBagConstraints();
           gbc.gridx= 0;
           gbc.gridy =0;
           gbc.insets =  new Insets(10,20,0 ,0 );
           gbc.anchor = GridBagConstraints.FIRST_LINE_START; 
           gbc.ipadx=60;
           panel.add(prodName,gbc);
           gbc.gridy++;
           panel.add(prodPrice,gbc);
           gbc.gridx =3;
           gbc.anchor = GridBagConstraints.FIRST_LINE_END; 
           panel.add(status,gbc);


          return panel;
     }



}




   
