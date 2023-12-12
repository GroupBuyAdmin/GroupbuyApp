package groupbuyapp.Client.Center.Content.ProductContainers;


import javax.swing.ImageIcon;

public class Product {
    private int id;
    private ImageIcon imageIcon;
    private String name;
    private String location;
    private String category;
    private String price;
    private String description;
    private int productStatus;
    private int creatorID;
    
    
    public int getProductStatus() {
        return productStatus;
    }

    public int getCreatorID() {
        return creatorID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product(ImageIcon imageIcon, String name, String price, String location, String category, String description, int creatorID, int productStatus) {
        this.id = 0;
        this.imageIcon = imageIcon;
        this.name = name;
        this.location = location;
        this.price = price;
        this.description = description;
        this.category = category;
        this.productStatus = productStatus;
        this.creatorID = creatorID;
    }

}