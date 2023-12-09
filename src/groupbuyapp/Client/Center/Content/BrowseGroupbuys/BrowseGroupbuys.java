package groupbuyapp.Client.Center.Content.BrowseGroupbuys;


import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.security.PublicKey;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import groupbuyapp.Misc.ColorPalette.GbuyColor;


public class BrowseGroupbuys extends JPanel{


  private Groupbuys groupbuys;
  private SeeAll seeAll;
  private CardLayout crd;  
    public BrowseGroupbuys(){
         groupbuys = new Groupbuys(this);
         seeAll = new SeeAll(this);

         crd = new CardLayout();
         setLayout(crd);
         add(groupbuys, "groupbuys");
          add(seeAll, "seeAll");
         crd.show(this, "groupbuys");
    }

    public void SeeAll(){
      crd.show(this, "seeAll");
    }
     public void back(){
       crd.show(this, "groupbuys");
    }


}

 class Groupbuys extends JPanel{
   private BrowseGroupbuys browseGroupbuys;
    public Groupbuys(BrowseGroupbuys browseGroupbuys){
        this.browseGroupbuys = browseGroupbuys;
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
           
           for(int i=2;i<=10;i++){
              panel.add(CategoryContainer("Catgory"+i),gbc);
               gbc.gridy++;
           }

            //   panel.addMouseListener(new MouseAdapter() {
               
            //     }
            // });
           

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

            panel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {     
                  System.out.println(catname); 
                 }
               });
           
           
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

        seAll.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {     
                    browseGroupbuys.SeeAll();
                 }
               });

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
           gbc.insets = new Insets(0, 10, 0, 20);//src\groupbuyapp\Client\Center\Content\BrowseGroupbuys\customImage\nike.png
           panel.add(productPanel("Product"+1,999,"src/groupbuyapp/Client/Center/Content/BrowseGroupbuys/customImage/Macbook.png","Manila","Ongoing"),gbc);
           gbc.gridx++;
           for(int i=2; i<=20;i++){
          
              panel.add(productPanel("Product"+i,999,"src/groupbuyapp/Client/Center/Content/BrowseGroupbuys/customImage/nike.png","Cebu","Ongoing"),gbc);
              gbc.gridx++;
           }
          return panel;
     }



     // products panel
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
           JLabel prodPrice = new JLabel("$"+price);    
           prodPrice.setFont(new Font(prodPrice.getName(),Font.ROMAN_BASELINE,15));
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
           gbc.ipadx=20;
           panel.add(location,gbc);
           gbc.insets =  new Insets(15,20,0 ,0 );
           gbc.anchor = GridBagConstraints.FIRST_LINE_START;      
           gbc.gridy++;
           panel.add(prodName,gbc);
           gbc.gridy++;
           panel.add(prodPrice,gbc);
           gbc.gridx =3;
           gbc.anchor = GridBagConstraints.FIRST_LINE_END; 
           gbc.insets =  new Insets(10,70,0 ,90 );
           panel.add(status,gbc);


          return panel;
     }



}
 

class SeeAll extends JPanel{
 
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
           JLabel prodPrice = new JLabel("$"+price);    
           prodPrice.setFont(new Font(prodPrice.getName(),Font.ROMAN_BASELINE,15));
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
           gbc.ipadx=20;
           panel.add(location,gbc);
           gbc.insets =  new Insets(15,20,0 ,0 );
           gbc.anchor = GridBagConstraints.FIRST_LINE_START;      
           gbc.gridy++;
           panel.add(prodName,gbc);
           gbc.gridy++;
           panel.add(prodPrice,gbc);
           gbc.gridx =3;
           gbc.anchor = GridBagConstraints.FIRST_LINE_END; 
           gbc.insets =  new Insets(10,70,0 ,90 );
           panel.add(status,gbc);


          return panel;
     }
}