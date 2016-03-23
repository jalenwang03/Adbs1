import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Items {

	/*
	 * Because most of common delimiters have been occupied, we choose |||| as the new delimiter.
	 * Here we create all attributes in table Items
	 */
	public static String split="||||";
	private String itemId=null;
	private String name=null;
	private String Currently=null;
	private String BuyPrice = null;
	private String FirstBid=null;
	private String NumberOfBids=null;
	private String Country="";
	private String Location="";
	private String Latitude=null;
	private String Longitude=null;
	private String start=null;
	private String ends=null;
	private String SellerId=null;
	private String Seller_rating=null;
	private Bid bid;
	private Bid bidtmp;
	private String Description=null;
	private List catagroyList=new ArrayList();
	private List bids=new ArrayList();
	
	/*
	 * Basic methods for inserting and get data.
	 */
	public void setItemid(String itemId){
		this.itemId=itemId;
		if(MySAX.DEBUG)
			System.out.println("[Items] items Setting itemId "+itemId);
	}
	public void setName(String Name){
		this.name=Name;
		if(MySAX.DEBUG)
			System.out.println("[Items] items Setting Name "+Name);
	}
	public void setCurrently(String Currently){
		this.Currently=Currently;
		if(MySAX.DEBUG)
			System.out.println("[Items] items Setting Currently "+Currently);
	}
	public void setBuyPrice(String BuyPrice){
		this.BuyPrice=BuyPrice;
		if(MySAX.DEBUG)
			System.out.println("items Setting BuyPrice "+BuyPrice);
	}
	public void setFirstBid(String FirstBid){
		this.FirstBid=FirstBid;
		if(MySAX.DEBUG)
			System.out.println("items Setting FirstBid "+FirstBid);
	}
	public void setNumberOfBids(String NumberOfBids){
		this.NumberOfBids=NumberOfBids;
		if(MySAX.DEBUG)
			System.out.println("items Setting NumberOfBids "+NumberOfBids);
	}
	public void setCountry(String Country){
		this.Country=Country;
		if(MySAX.DEBUG)
			System.out.println("items Setting Country "+Country);
	}
	public void setLocation(String Location){
		this.Location=Location;
		if(MySAX.DEBUG)
			System.out.println("items Setting Location "+Location);
	}
	public void setLatitude(String Latitude){
		this.Latitude=Latitude;
		if(MySAX.DEBUG)
			System.out.println("items Setting Latitude "+Latitude);
	}
	public void setLongitude(String Longitude){
		this.Longitude=Longitude;
		if(MySAX.DEBUG)
			System.out.println("items Setting Longitude "+Longitude);
	}
	public void setstart(String start){
		this.start=start;
		if(MySAX.DEBUG)
			System.out.println("items Setting start "+start);
	}
	public void setends(String ends){
		this.ends=ends;
		if(MySAX.DEBUG)
			System.out.println("items Setting ends "+ends);
	}
	public void setSellerId(String SellerId){
		this.SellerId=SellerId;
		if(MySAX.DEBUG)
			System.out.println("items Setting SellerId "+SellerId);
	}
	public void setDescription(String Description){
		this.Description=Description;
		if(MySAX.DEBUG)
			System.out.println("items Setting Description "+Description);
	}
	
	/*
	 * Here we initialize object ItemCategory.
	 * Because an item may have a few categories, we use a list to record them.
	 */
	public void setcategory(String category){
		ItemCategory ic=new ItemCategory();
		if(MySAX.DEBUG)
			System.out.println("Starting create new category");
		ic.setCategory(category);
		if(MySAX.DEBUG)
			System.out.println("Setting category to "+category);
		ic.setItemId(this.itemId);
		if(MySAX.DEBUG)
			System.out.println("Setting category's itemid to "+this.itemId);
		catagroyList.add(ic);
		if(MySAX.DEBUG)
			System.out.println("Adding category");
	}
	
	/*
	 * When read bids, we record the itemId in table BID.
	 * Because an item may have a few bids, we use a list to record them.
	 */
	public void bidstart(){
		bid=new Bid();
		if(MySAX.DEBUG)
			System.out.println("[Items] Setting bid start");
		bid.setItemId(this.itemId);
		if(MySAX.DEBUG)
			System.out.println("[Items] Setting bid's itemID to "+this.itemId);
	}
	public void bidend(){
		bidtmp = new Bid();
		if(MySAX.DEBUG)
			System.out.println("[Items] creating new Bid");
		bidtmp.setItemId(bid.getItemId());
		bidtmp.setBiderId(bid.getUser().getUserId());
		bidtmp.setAmount(bid.getAmount());
		bidtmp.setTime(bid.getTime());
		bids.add(bidtmp);
		if(MySAX.DEBUG)
			System.out.println("[Items bidend] "+bidtmp.getItemId()+"-->"+bidtmp.getAmount()+"-->"+bidtmp.getTime()+"-->"+bidtmp.getUser().getUserId()+"-->"+bidtmp.getUser().getCountry()+"-->"+bidtmp.getUser().getbid_Rating());
//		bids.add(bidtmp);
	}
	
	/*
	 * Here we initialize table User, and insert user information.
	 * UserCheck can help us to avoid record users repeatedly.
	 */
	public void addseller(){
		User seller=new User();
		if(MySAX.DEBUG)
			System.out.println("[Items] Creating new seller");
		seller.setUserId(SellerId);
		if(MySAX.DEBUG)
			System.out.println("[Items] setting sellerid "+SellerId);
		seller.setCountry(Country);
		if(MySAX.DEBUG)
			System.out.println("[Items] Setting seller's Country "+Country);
		seller.setLocation(Location);
		if(MySAX.DEBUG)
			System.out.println("[Items] Setting seller's location "+Location);
		seller.setseller_Rating(Seller_rating);
		if(MySAX.DEBUG)
			System.out.println("[Items] Setting seller's Seller_rating "+Seller_rating);
		seller.userCheck();
		if(MySAX.DEBUG)
			System.out.println("[Items] Check seller's user");
		
	}
	
	public void setbidTime(String Time){
		bid.setTime(Time);
		if(MySAX.DEBUG)
			System.out.println("[Items] setting bid time "+Time);
	}
	public void setbidAmount(String Amount){
		bid.setAmount(Amount);
		if(MySAX.DEBUG)
			System.out.println("[Items] setting Amount "+Amount);
	}
	public void setbidCountry(String Country){
		bid.setBiderCountry(Country);
		if(MySAX.DEBUG)
			System.out.println("[Items] setting Country "+Country);
	}
	public void setbidBiderId(String BiderId){
		bid.setBiderId(BiderId);
		if(MySAX.DEBUG)
			System.out.println("[Items] setting BiderId "+BiderId);
	}
	public void setbid_rating(String bid_rating){
		bid.setbid_Rating(bid_rating);
		if(MySAX.DEBUG)
			System.out.println("[Items setbid_rating] setting bid_rating "+bid_rating);
	}
	public void setseller_rating(String seller_rating){
		this.Seller_rating=seller_rating;
		if(MySAX.DEBUG)
			System.out.println("[Items] setting seller_rating "+seller_rating);
	}
	public void setbidlocation(String location){
		bid.setBiderLocation(location);
		if(MySAX.DEBUG)
			System.out.println("[Items] setting location "+location);
	}
	public void setbiduserid(String UserId){
		bid.setBiderUserid(UserId);
		if(MySAX.DEBUG)
			System.out.println("[Items] setting UserId "+UserId);
	}
	
	/*
	 * Write data into csv files.
	 * The order of data is the the order of attributes in tables.
	 */
	
	public void writeTOcsv() throws IOException{
		FileWriter fwitem=new FileWriter("./item.csv",true);
		fwitem.write(this.itemId+split);
		fwitem.write(this.name+split);
		fwitem.write(this.Currently+split);
		fwitem.write(this.BuyPrice+split);
		fwitem.write(this.FirstBid+split);
		fwitem.write(this.NumberOfBids+split);
		fwitem.write(this.Country+split);
		fwitem.write(this.Location+split);
		fwitem.write(this.Latitude+split);
		fwitem.write(this.Longitude+split);
		fwitem.write(this.start+split);
		fwitem.write(this.ends+split);
		fwitem.write(this.SellerId+split);
		fwitem.write(this.Description);
		fwitem.write("\n");
		fwitem.flush();
		fwitem.close();
		
		FileWriter fwcategory=new FileWriter("./category.csv",true);
		for(int i=0;i<catagroyList.size();i++){
			ItemCategory ic=(ItemCategory) catagroyList.get(i);
			fwcategory.write(ic.getItemId()+split);
			fwcategory.write(ic.getCategory());
			fwcategory.write("\n");
		}
		fwcategory.flush();
		fwcategory.close();
		
		FileWriter fwbid=new FileWriter("./bid.csv",true);
		for(int i=0;i<bids.size();i++){
			Bid b=(Bid) bids.get(i);
			if(!b.getTime().equals("") && !b.getItemId().equals("")){
			fwbid.write(b.getItemId()+split);
			fwbid.write(b.getUser().getUserId()+split);
			fwbid.write(b.getTime()+split);
			fwbid.write(b.getAmount());
			fwbid.write("\n");
			}
		
		}
		
		fwbid.flush();
		fwbid.close();

	}
	
}
