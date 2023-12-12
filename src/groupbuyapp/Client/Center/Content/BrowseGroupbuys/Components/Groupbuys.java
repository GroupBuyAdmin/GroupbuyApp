package groupbuyapp.Client.Center.Content.BrowseGroupbuys.Components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import groupbuyapp.Client.Center.Content.BrowseGroupbuys.BrowseGroupbuys;
import groupbuyapp.Misc.ColorPalette.GbuyColor;
import groupbuyapp.Misc.Database.GbuyProductDatabase;
import groupbuyapp.Client.Center.Content.ProductContainers.Product;
import groupbuyapp.Client.Center.Content.ProductContainers.ProductPanelSmall;

 public class Groupbuys extends JPanel{
   
  private BrowseGroupbuys browseGroupbuys;
   private GbuyProductDatabase gbuyProductDatabase;
   private ProductPanelSmall productPanelSmall;

   List<Product> products;
   
  
   
    public  Groupbuys(BrowseGroupbuys browseGroupbuys, GbuyProductDatabase gbuyProductDatabase,  ProductPanelSmall productPanelSmall){
        this.browseGroupbuys = browseGroupbuys;
        this.gbuyProductDatabase = gbuyProductDatabase;
        this.productPanelSmall = productPanelSmall;
        
        products = new ArrayList<>();
        products = gbuyProductDatabase.getInstance().getProducts();

      

       

       
      
        setBackground(GbuyColor.PANEL_BACKGROUND_COLOR);
         setLayout(new BorderLayout());
   
       
      setYeah();

      
    }


    public void setYeah(){
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
           gbc.gridy++;
           
          String categories[] = new String[5];
          categories[0]= "Electronics";
          categories[1]= "Clothing";
          categories[2]= "Books";
          categories[3]= "Home and Kitchen";
          categories[4]= "Sports";
          

           products = gbuyProductDatabase.getInstance().getProducts();
           
           for(int i=0;i<categories.length;i++){
              panel.add(CategoryContainer(categories[i]),gbc);
               gbc.gridy++;
           }

           //  for(Product p : products){
          //     panel.add(CategoryContainer(p.getCategory()),gbc);
          //      gbc.gridy++;
          //  }

       

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
           
           JScrollPane scrollPane = new JScrollPane(DisplayProducts(catname));  
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



     public JPanel DisplayProducts(String catname){
          JPanel panel = new JPanel();
            panel.setBackground(Color.decode("#FFFFFF"));

          panel.setLayout(new GridBagLayout());

          GridBagConstraints gbc = new GridBagConstraints();
           gbc.gridx = 0;
           gbc.gridy =0;
           gbc.insets = new Insets(0, 10, 0, 20);//src\groupbuyapp\Client\Center\Content\BrowseGroupbuys\customImage\nike.png
          // panel.add(productPanel("Product"+1,999.0,"src/groupbuyapp/Client/Center/Content/BrowseGroupbuys/customImage/Macbook.png","Manila","Ongoing"),gbc);
           gbc.gridx++;

          //  for(int i=2; i<=20;i++){
          
          //     panel.add(productPanel("Product"+i,999.0,"src/groupbuyapp/Client/Center/Content/BrowseGroupbuys/customImage/nike.png","Cebu","Ongoing"),gbc);
          //     gbc.gridx++;
          //  }

           for(Product p : products){
               
              if(p.getCategory().equals(catname) )
               panel.add(productPanel(p.getName(),p.getPrice(),p.getImageIcon(),p.getLocation(),"Ongoing"),gbc);          
               gbc.gridx++;
           }
          return panel;
     }



     // products panel
     public  JPanel productPanel(String name , String price,ImageIcon imageIcon,String location,String status){
           JPanel panel = new JPanel();
           panel.setBackground(Color.decode("#ffffff"));
             panel.setLayout(new GridBagLayout());
            
           Dimension dim = getPreferredSize();
           dim.width =250;
           dim.height= 280;
           panel.setPreferredSize(dim);

           JLabel image = new JLabel();
         
          

          //  ImageIcon imageIcon = new ImageIcon(imagePath);
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

     public void refreshGroupbuys() {
      products = gbuyProductDatabase.getInstance().getProducts();
      Container().removeAll();
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.insets = new Insets(20, 0, 30, 0);

      for (Product p : products) {
          Container().add(CategoryContainer(p.getCategory()), gbc);
          gbc.gridy++;
      }

      Container().revalidate();
      Container().repaint();
  }


     public JPanel details(String  name, String price, String locationn,String stutus){
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
 
 
