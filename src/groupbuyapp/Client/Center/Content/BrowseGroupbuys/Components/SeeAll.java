package groupbuyapp.Client.Center.Content.BrowseGroupbuys.Components;


import java.awt.BorderLayout;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import groupbuyapp.Client.Center.Content.BrowseGroupbuys.BrowseGroupbuys;

public class SeeAll extends JPanel{
 
  private BrowseGroupbuys browseGroupbuys;
  public SeeAll(BrowseGroupbuys browseGroupbuys){
          this.browseGroupbuys = browseGroupbuys;
          setLayout(new BorderLayout());
           
           JScrollPane scrollPane = new JScrollPane(DisplayProducts());  
           scrollPane.setBorder(null);
           scrollPane.setViewportBorder(null);
           
            
          
          
          add(header(),BorderLayout.NORTH);
          add(scrollPane,BorderLayout.CENTER);


  }

   public JPanel header(){
    
    JButton back = new JButton("Back" );
    JPanel panel = new JPanel();

     back.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {     
                    browseGroupbuys.back();
                 }
               });

    panel.setLayout(new BorderLayout());
    panel.add(back,BorderLayout.WEST);

    return panel;
   }



   
    public JPanel DisplayProducts(){
          JPanel panel = new JPanel();
            panel.setBackground(Color.decode("#FFFFFF"));

          panel.setLayout(new GridBagLayout());

          GridBagConstraints gbc = new GridBagConstraints();
           gbc.gridx = 0;
           gbc.gridy =0;
           gbc.insets = new Insets(50, 10, 0, 20);//src\groupbuyapp\Client\Center\Content\BrowseGroupbuys\customImage\nike.png
           panel.add(productPanel("Product"+1,999,"src/groupbuyapp/Client/Center/Content/BrowseGroupbuys/customImage/Macbook.png","Manila","Ongoing"),gbc);
           gbc.gridx++;
           for(int i=2; i<=20;i++){   
              panel.add(productPanel("Product"+i,999,"src/groupbuyapp/Client/Center/Content/BrowseGroupbuys/customImage/nike.png","Cebu","Ongoing"),gbc);
              gbc.gridx++;
                if (gbc.gridx > 2) {
                    gbc.gridx = 0;
                    gbc.gridy++;
                } 
           }
          return panel;
     }



   public  JPanel productPanel(String name , int price,String imagePath,String location,String status){
           JPanel panel = new JPanel();
           panel.setBackground(Color.decode("#ffffff"));
             panel.setLayout(new GridBagLayout());
            
           Dimension dim = getPreferredSize();
           dim.width =250;
           dim.height= 280;
           panel.setPreferredSize(dim);

           JLabel image = new JLabel();
         
          

           ImageIcon imageIcon = new ImageIcon(imagePath);
           Image analyticsScaledImage = imageIcon.getImage().getScaledInstance(250, 150, Image.SCALE_SMOOTH);
           ImageIcon setAnalyticsIcon = new ImageIcon(analyticsScaledImage);  
           image.setIcon(setAnalyticsIcon);  

           GridBagConstraints gbc = new GridBagConstraints();
           gbc.gridx= 0;
           gbc.gridy =0;
           panel.add(image,gbc);
           gbc.gridy++;
           panel.add(details(name,price,location,status),gbc);


           panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                 panel.setBorder(BorderFactory.createCompoundBorder(
                 new LineBorder(Color.decode("#ffffff")),
                        new CompoundBorder(
                        BorderFactory.createEmptyBorder(5, 5, 10, 10), 
                        BorderFactory.createLineBorder(Color.decode("#f0f0f0"), 5) 
            )
          ));
            }

          @Override
            public void mouseExited(MouseEvent e) {        
                 panel.setBorder(BorderFactory.createCompoundBorder(
                 new LineBorder(Color.decode("#ffffff")), 
                        new CompoundBorder(
                        BorderFactory.createEmptyBorder(5, 5, 5, 5),
                        BorderFactory.createLineBorder(Color.decode("#ffffff"), 3) 
            )
           ));
            }

           
       });
   
       

           return panel;
     }
     
     

  public JPanel details(String  name, int price, String locationn,String stutus){
          JPanel panel = new JPanel();
          panel.setBackground(Color.decode("#ffffff"));
          panel.setLayout(new GridBagLayout());
           JLabel location =  new JLabel(locationn);
           location.setForeground(Color.gray);


           JLabel prodName =  new JLabel(name);    
           prodName.setForeground(Color.decode("#0A0A0A"));
           prodName.setFont(new Font(prodName.getName(),Font.TRUETYPE_FONT,18));
         
           panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    prodName.setForeground(Color.decode("#F20530"));  
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    prodName.setForeground(Color.BLACK);  
                }
           });

           JLabel prodPrice = new JLabel("$"+price);    
           prodPrice.setFont(new Font(prodPrice.getName(),Font.ROMAN_BASELINE,15));
             prodPrice.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    prodPrice.setForeground(Color.decode("#F20530"));  
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    prodPrice.setForeground(Color.BLACK);  
                }
           });


           JLabel status = new JLabel(stutus);
           status.setFont(new Font(status.getName(),Font.ROMAN_BASELINE,15));
           status.setBorder(BorderFactory.createEtchedBorder());
           status.setHorizontalAlignment(SwingConstants.CENTER); 
           status.setOpaque(true);
          //  status.setBackground(Color.decode("#F20530"));
          status.setBackground(Color.green);
          status.setForeground(Color.WHITE);
    

           GridBagConstraints gbc = new GridBagConstraints();
           gbc.gridx= 0;
           gbc.gridy =0;
        
           gbc.insets =  new Insets(15,0,0 ,110 );
           panel.add(location,gbc);
           gbc.insets =  new Insets(15,0,0 ,90 );
           gbc.anchor = GridBagConstraints.FIRST_LINE_START;      
           gbc.gridy++;
           panel.add(prodName,gbc);
           gbc.gridy++;
           panel.add(prodPrice,gbc);
           gbc.gridx =3;

           gbc.anchor = GridBagConstraints.FIRST_LINE_END; 
           gbc.insets =  new Insets(10,0,0 ,0 );
           panel.add(status,gbc);


          return panel;
     }
}