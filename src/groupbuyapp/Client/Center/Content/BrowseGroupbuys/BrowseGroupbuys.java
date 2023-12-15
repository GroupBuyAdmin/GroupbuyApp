package groupbuyapp.Client.Center.Content.BrowseGroupbuys;



import java.awt.CardLayout;
import javax.swing.JPanel;

import groupbuyapp.Client.Center.Content.Content;
import groupbuyapp.Client.Center.Content.BrowseGroupbuys.Components.*;
import groupbuyapp.Client.Center.Content.ProductContainers.ProductPanel;

public class BrowseGroupbuys extends JPanel{
  private Groupbuys groupbuys;
  private SeeAll seeAll;
  private CardLayout crd;  
  private ProductPanel productPanelSmall;

  public BrowseGroupbuys(){
    groupbuys = new Groupbuys(this, productPanelSmall);
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
