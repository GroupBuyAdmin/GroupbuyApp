package groupbuyapp.SystemFiles.Database;

import java.io.File;
import java.sql.Timestamp;

public class SingleProductContainer{
    public int productID;
    public String productName;
    public String productCategory;
    public double productPrice;
    public String productDescription;
    public String productLocation;
    public byte[] byteImage;
    public File selectedFile;
    public int creatorID;
    public String productStatus;
    public int userCount;
    public int userLimit;
    public String deadlineString;
    public Timestamp deadlineStamp;
}