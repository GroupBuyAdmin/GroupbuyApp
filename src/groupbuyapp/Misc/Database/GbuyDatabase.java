package groupbuyapp.Misc.Database;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;

import groupbuyapp.Client.Center.Content.ProductContainers.Product;
import groupbuyapp.Client.LogIn.User;
import groupbuyapp.Client.LogIn.UserLoginData;

/**
 * The {@code GbuyProductDatabase} class is responsible for managing the database operations related to products in the Gbuy application.
 * It provides methods for inserting, retrieving, updating, and deleting products from the database.
 *   
 * @author BSCS 2A group 5
 */
public class GbuyDatabase {

    private static volatile GbuyDatabase instance;

    private String driver = "com.mysql.cj.jdbc.Driver";
    private String databaseName = "Gbuy";                                      //modify this
    private String url = "jdbc:mysql://localhost:3306/" + databaseName;
    private String username = "root";
    private String password = "";

    private String query = null;
    private Connection connection = null;

    private GbuyDatabase(){
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    } //for singleton pattern

    /**
     * Returns an instance of the GbuyProductDatabase class.
     * This method follows the Singleton design pattern, ensuring that only one instance of the class is created
     * and providing a global point of access to it.
     *
     * @return An instance of the GbuyProductDatabase class.
     */

    public static GbuyDatabase getInstance(){
        GbuyDatabase thisInstance = instance;
        if(thisInstance == null){
            synchronized (GbuyDatabase.class){
                thisInstance = instance;
                if(instance == null){
                    instance = thisInstance = new GbuyDatabase();

                }
            }
        }
        return thisInstance;
    }


    /**
     * Inserts a new product into the database.
     *
     * @param spc The SingleProductContainer object that contains the details of the product to be inserted.
     */

    public void insertProduct(SingleProductContainer spc){
        try {
            // Validate the input
            if (spc == null || spc.productName == null || spc.productCategory == null || spc.productDescription == null || spc.productLocation == null || spc.selectedFile == null || spc.productStatus == null || spc.deadlineString == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            query = "INSERT INTO products (productName, productCategory, productPrice, productDescription, productLocation, productImage, productStatus, productCreatorId, productUserLimit, productDeadline) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, spc.productName);
                preparedStatement.setString(2, spc.productCategory);
                preparedStatement.setDouble(3, spc.productPrice);
                preparedStatement.setString(4, spc.productDescription);
                preparedStatement.setString(5, spc.productLocation);
                try (FileInputStream inputStream = new FileInputStream(spc.selectedFile)) {
                    byte[] imageData = new byte[(int) spc.selectedFile.length()];
                    inputStream.read(imageData);
                    preparedStatement.setBytes(6, imageData);
                }
                preparedStatement.setString(7, spc.productStatus);
                preparedStatement.setInt(8, spc.creatorID);
                preparedStatement.setInt(9, spc.userLimit);
                Timestamp timeStamp = convertDateToTimestamp(spc.deadlineString);
                preparedStatement.setTimestamp(10, timeStamp);

                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println(rowsAffected + " row(s) affected");
                System.out.println(spc.productName + " was product added");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Timestamp convertDateToTimestamp(String dateTimeString){
        Timestamp dateTimeStamp = null;
        Date parsedDate = null;
        // Parse the string into a Date object
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            parsedDate = dateFormat.parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateTimeStamp = new Timestamp(parsedDate.getTime());
        return dateTimeStamp;
    }

    /**
     * Retrieves all products from the database and returns a list of Product objects.
     *
     * @return A list of Product objects containing the data retrieved from the database.
     */
    
    public List<Product> getProducts(){
        List<Product> allproducts = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `products`");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while(resultSet.next()){
                int productID = resultSet.getInt("productID");
                String productName = resultSet.getString("productName");
                String productCategory = resultSet.getString("productCategory");
                double productPrice = resultSet.getDouble("productPrice");
                String productDescription = resultSet.getString("productDescription");
                String productLocation = resultSet.getString("productLocation");
                byte[] byteImage = resultSet.getBytes("productImage");
            
                //convert image data to image object
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(byteImage).getImage());
                
                String productStatus = resultSet.getString("productStatus");
                int creatorId = resultSet.getInt("productCreatorID");

                int userLimit = resultSet.getInt("productUserLimit");

                Timestamp deadlineStamp = resultSet.getTimestamp("productDeadline");

                Product p = new Product(imageIcon, productName, "$" + String.valueOf(productPrice), productLocation, productCategory, productDescription, creatorId, productStatus, userLimit, deadlineStamp);
                p.setId(productID);
                allproducts.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
        return allproducts;
    }

    public List<Product> getMyListings(User user){
        List<Product> allListings = new ArrayList<>();
        try {
            query = "SELECT * FROM `products` WHERE `productCreatorID` = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, user.getUserID());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while(resultSet.next()){
                        int productID = resultSet.getInt("productID");
                        String productName = resultSet.getString("productName");
                        String productCategory = resultSet.getString("productCategory");
                        double productPrice = resultSet.getDouble("productPrice");
                        String productDescription = resultSet.getString("productDescription");
                        String productLocation = resultSet.getString("productLocation");
                        byte[] byteImage = resultSet.getBytes("productImage");
                    
                        //convert image data to image object
                        ImageIcon imageIcon = new ImageIcon(new ImageIcon(byteImage).getImage());
                        
                        String productStatus = resultSet.getString("productStatus");
                        int creatorId = resultSet.getInt("productCreatorID");
                        
                        int userLimit = resultSet.getInt("productUserLimit");
                        
                        Timestamp deadlineStamp = resultSet.getTimestamp("productDeadline");
                        
                        Product p = new Product(imageIcon, productName, "$" + String.valueOf(productPrice), productLocation, productCategory, productDescription, creatorId, productStatus, userLimit, deadlineStamp);
                        p.setId(productID);
                        allListings.add(p);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return allListings;
    }

    public List<Product> getCategorizedProducts(String category, int creatorID){
        List<Product> allProducts = new ArrayList<>();
        try {
            query = "SELECT * FROM `products` WHERE `productCategory` = ? AND `productCreatorID` != ? AND `productUserCount` < `productUserLimit` AND `productStatus` != \"expired\"";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, category);
                preparedStatement.setInt(2, creatorID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while(resultSet.next()){
                        int productID = resultSet.getInt("productID");
                        String productName = resultSet.getString("productName");
                        String productCategory = resultSet.getString("productCategory");
                        double productPrice = resultSet.getDouble("productPrice");
                        String productDescription = resultSet.getString("productDescription");
                        String productLocation = resultSet.getString("productLocation");
                        byte[] byteImage = resultSet.getBytes("productImage");
                    
                        //convert image data to image object
                        ImageIcon imageIcon = new ImageIcon(new ImageIcon(byteImage).getImage());
                        
                        String productStatus = resultSet.getString("productStatus");
                        int creatorId = resultSet.getInt("productCreatorID");

                        int userLimit = resultSet.getInt("productUserLimit");
                        Timestamp deadlineStamp = resultSet.getTimestamp("productDeadline");
                        
                        Product p = new Product(imageIcon, productName, "$" + String.valueOf(productPrice), productLocation, productCategory, productDescription, creatorId, productStatus, userLimit, deadlineStamp);
                        p.setId(productID);
                        allProducts.add(p);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return allProducts;
    }

    public boolean checkForCategory(String category, int creatorID){
        List<Product> categorizedProduct = new ArrayList<>();
        try{
            query = "SELECT * FROM products WHERE productCategory = ? AND productCreatorID != ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, category);
                preparedStatement.setInt(2, creatorID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if(resultSet.next()){       
                        System.out.println("category found");
                        categorizedProduct.add(new Product());
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        if(categorizedProduct.isEmpty()){
            return false;
        } else {
            return true;
        }
    }

    public List<Product> getMyGroupbuys(User user){
        List<Integer> allproductIDs = getProductIDFromMyGroupbuys(user.getUserID());
        List<Product> allListings = new ArrayList<>();
        if(allproductIDs.isEmpty()){
            return allListings;
        }

        try {
            //build query
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM products WHERE productID IN (");
            for (int i = 0; i < allproductIDs.size(); i++) {
                queryBuilder.append("?");
                if (i < allproductIDs.size() - 1) {
                    queryBuilder.append(",");
                }
            }
            queryBuilder.append(")");

            query = queryBuilder.toString();
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                for(int i = 0; i < allproductIDs.size(); i++){
                    preparedStatement.setInt(i + 1, allproductIDs.get(i));
                }
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while(resultSet.next()){
                        int productID = resultSet.getInt("productID");
                        String productName = resultSet.getString("productName");
                        String productCategory = resultSet.getString("productCategory");
                        double productPrice = resultSet.getDouble("productPrice");
                        String productDescription = resultSet.getString("productDescription");
                        String productLocation = resultSet.getString("productLocation");
                        byte[] byteImage = resultSet.getBytes("productImage");
                    
                        //convert image data to image object
                        ImageIcon imageIcon = new ImageIcon(new ImageIcon(byteImage).getImage());
                        
                        String productStatus = resultSet.getString("productStatus");
                        int creatorId = resultSet.getInt("productCreatorID");
                        
                        int userLimit = resultSet.getInt("productUserLimit");
                        
                        Timestamp deadlineStamp = resultSet.getTimestamp("productDeadline");
                        
                        Product p = new Product(imageIcon, productName, "$" + String.valueOf(productPrice), productLocation, productCategory, productDescription, creatorId, productStatus, userLimit, deadlineStamp);
                        p.setId(productID);
                        allListings.add(p);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return allListings;
    }

    private List<Integer> getProductIDFromMyGroupbuys(int userID){
        List<Integer> allProductIDs = new ArrayList<>();
        try{
            query = "SELECT productID FROM groupbuys WHERE userID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while(resultSet.next()){
                        allProductIDs.add(resultSet.getInt("productID"));
                    }
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return allProductIDs;
    }

    public List<Product> getSearchedItem(String searchItem, int useriD){
        List<Product> allProducts = new ArrayList<>();

        try {
            query = "SELECT * FROM products WHERE (productName LIKE ? OR productDescription LIKE ?) AND productCreatorID != ? AND productStatus != \"completed\"";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, "%" + searchItem + "%");
                preparedStatement.setString(2, "%" + searchItem + "%");
                preparedStatement.setInt(3, useriD);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while(resultSet.next()){
                        int productID = resultSet.getInt("productID");
                        String productName = resultSet.getString("productName");
                        String productCategory = resultSet.getString("productCategory");
                        double productPrice = resultSet.getDouble("productPrice");
                        String productDescription = resultSet.getString("productDescription");
                        String productLocation = resultSet.getString("productLocation");
                        byte[] byteImage = resultSet.getBytes("productImage");
                        ImageIcon imageIcon = new ImageIcon(new ImageIcon(byteImage).getImage());
                        String productStatus = resultSet.getString("productStatus");
                        int creatorId = resultSet.getInt("productCreatorID");
                        int userLimit = resultSet.getInt("productUserLimit");                
                        Timestamp deadlineStamp = resultSet.getTimestamp("productDeadline");
                        
                        Product p = new Product(imageIcon, productName, "$" + String.valueOf(productPrice), productLocation, productCategory, productDescription, creatorId, productStatus, userLimit, deadlineStamp);
                        p.setId(productID);
                        allProducts.add(p);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return allProducts;
    }

    /**
     * Deletes a product from the database based on the provided product ID.
     *
     * @param productIdToDelete The ID of the product to be deleted from the database.
     */
    
    public void deleteProduct(int productIdToDelete){
        try {
            query = "DELETE FROM `products` WHERE `productId` = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, productIdToDelete);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Row with ID " + productIdToDelete + " deleted successfully.");
                } else {
                    System.out.println("No rows deleted. ID " + productIdToDelete + " not found.");
                }
            }
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates a product in the database with new information provided by the user.
     *
     * @param spc The SingleProductContainer object that contains the new information for the product.
     * @param productIdtoEdit An integer representing the ID of the product to be edited.
     */

    public void editProduct(SingleProductContainer spc, int productIdtoEdit){
        try {
            query = "UPDATE products SET productName = ?, productCategory = ?, productPrice = ?, productDescription = ?, productLocation = ?, productImage = ?, productUserLimit = ?, productDeadline = ? WHERE productId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, spc.productName);
                preparedStatement.setString(2, spc.productCategory);
                preparedStatement.setDouble(3, spc.productPrice);  
                preparedStatement.setString(4, spc.productDescription);
                preparedStatement.setString(5, spc.productLocation);  
                try (FileInputStream inputStream = new FileInputStream(spc.selectedFile)) {
                    byte[] imageData = new byte[(int) spc.selectedFile.length()];
                    inputStream.read(imageData);
                    preparedStatement.setBytes(6, imageData);
                }

                preparedStatement.setInt(7, spc.userLimit);

                Timestamp timestamp = convertDateToTimestamp(spc.deadlineString);

                preparedStatement.setTimestamp(8, timestamp);

                preparedStatement.setInt(9, productIdtoEdit);  
                int rowsAffected = preparedStatement.executeUpdate();


                if (rowsAffected > 0) {
                    System.out.println("Update successful");
                } else {
                    System.out.println("No rows updated. Check if the productId exists.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Retrieves a single product from the database based on the provided product ID.
     *
     * @param productID The ID of the product to be retrieved from the database.
     * @return SingleProductContainer object containing the details of the retrieved product from the database.
     */
    
    public SingleProductContainer getSingleProduct(int productID){
        SingleProductContainer spc = new SingleProductContainer();
        try {
            query = "SELECT * FROM products WHERE productId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, productID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if(resultSet.next()){
                        spc.productName = resultSet.getString("productName");
                        spc.productCategory = resultSet.getString("productCategory");
                        spc.productPrice = resultSet.getDouble("productPrice");
                        spc.productDescription = resultSet.getString("productDescription");
                        spc.productLocation = resultSet.getString("productLocation");
                        spc.byteImage = resultSet.getBytes("productImage");
                        spc.userLimit = resultSet.getInt("productUserLimit");
                        spc.deadlineStamp = resultSet.getTimestamp("productDeadline");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return spc;
    }


    public void uploadUser(User user){
        try {
            query = "INSERT INTO users (userName, userPassword, firstName, lastName, email) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, user.getUserName());
                preparedStatement.setString(2, user.getUserPassword());
                preparedStatement.setString(3, user.getFirstName());
                preparedStatement.setString(4, user.getLastName());
                preparedStatement.setString(5, user.getLastName());
                
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("uploaded user");
                } else {
                    System.out.println("No rows updated.");
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public User getUser(UserLoginData uld){
        try{
            query = "SELECT * FROM users WHERE userName = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, uld.username);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if(resultSet.next()){       //if username is found
                        if(resultSet.getString("userPassword").equals(uld.password)){   //if password matches
                            System.out.println("password match");
                            int userID = resultSet.getInt("userID");
                            String userName = resultSet.getString("userName");
                            String userPassword = resultSet.getString("userPassword");
                            String firstName = resultSet.getString("firstName");
                            String lastName = resultSet.getString("lastName");
                            String email = resultSet.getString("email");
                            User user = new User(userName, userPassword, firstName, lastName, email);
                            user.setUserID(userID);
                            return user;
                        }
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        
        return null;
    }

    public boolean userExists(User user){
        try{
            query = "SELECT * FROM users WHERE userName = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, user.getUserName());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if(resultSet.next()){
                        return true;
                    } else {
                        return false;
                    }
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public String getUserName(int userId){
        try{
            query = "SELECT userName FROM users WHERE userId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if(resultSet.next()){
                        return resultSet.getString("userName");
                    } else {
                        return null;
                    }
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
    
    public int getUserID(User user){
        try{
            query = "SELECT * FROM users WHERE userName = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, user.getUserName());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if(resultSet.next()){       //if username is found
                        System.out.println("user id found");
                        int userID = resultSet.getInt("userID");
                        return userID;
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        
        return -1;
    }

    public SingleProductContainer getProductUserCountAndLimit(int productID){
        SingleProductContainer spc = new SingleProductContainer();
        try{
            
            query = "SELECT productUserCount, productUserLimit, productStatus FROM products WHERE productID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, productID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if(resultSet.next()){      
                        spc.userCount = resultSet.getInt("productUserCount");
                        spc.userLimit = resultSet.getInt("productUserLimit");
                        spc.productStatus = resultSet.getString("productStatus");
                        return spc;
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        
        return spc;
    }

    public void createGroupbuy(int productID, int userID){
        try{
            query = "INSERT INTO groupbuys(productID, userID) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, productID);
                preparedStatement.setInt(2, userID);

                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println(rowsAffected + " row(s) affected");
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        incrementUserCount(productID);
    }

    private void incrementUserCount(int productID){
        try {
            query = "UPDATE products SET productUserCount = productUserCount + 1 WHERE productID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, productID);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("incremented");
                } else {
                    System.out.println("No rows updated. Check if the productId exists.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean alreadyJoined(int productID, int userID){
        try{

            query = "SELECT * FROM groupbuys WHERE productID = ? AND userID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, productID);
                preparedStatement.setInt(2, userID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if(resultSet.next()){
                        System.out.println("already joined");
                        return true;
                    }
                }
            }

        } catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("haven't joined");
        return false;
    }

    public void deleteGroupbuy(int productID, int userID){
        try {
            query = "DELETE FROM groupbuys WHERE productID = ? AND userID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, productID);
                preparedStatement.setInt(2, userID);

                preparedStatement.executeUpdate();
                System.out.println("groupbuy deleted");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        decrementUserCount(productID);
    }

    private void decrementUserCount(int productID){
        try {
            query = "UPDATE products SET productUserCount = productUserCount - 1 WHERE productID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, productID);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("decremented");
                } else {
                    System.out.println("No rows updated. Check if the productId exists.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}