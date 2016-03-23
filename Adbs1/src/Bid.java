
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Bid {
	
	/*
	 * The four attributes in table BID.
	 * Here we create a user object, cause we should record bidder's information in table USER.
	 */
	private String ItemId="";
	private String Time="";
	private String Amount="";
	private User binder=new User();
	
	/*
	 * Basic methods for inserting data and get data.
	 */
	public void setItemId(String ItemId){
		this.ItemId=ItemId;
		if(MySAX.DEBUG)
			System.out.println("[Bid setItemId] setting ItemId "+this.ItemId);
	}
	public void setTime(String Time){
		this.Time=Time;
		if(MySAX.DEBUG)
			System.out.println("[Bid setTime] setting Time "+this.Time);
	}
	public void setAmount(String Amount){
		this.Amount=Amount;
		if(MySAX.DEBUG)
			System.out.println("[Bid setAmount] setting Amount "+this.Amount);
	}
	public void setBiderId(String BiderId){
//		binder=new User();
		this.binder.setUserId(BiderId);
		if(MySAX.DEBUG)
			System.out.println("[Bid setBiderId] setting BiderId "+BiderId);
	}
	public void setbid_Rating(String bid_rating){
		binder=new User();
		this.binder.setbid_Rating(bid_rating);
		if(MySAX.DEBUG)
			System.out.println("[Bid setbid_Rating] setting bid_rating "+bid_rating);
	}
	public void setBiderCountry(String country){
//		binder=new User();
		this.binder.setCountry(country);
		if(MySAX.DEBUG)
			System.out.println("[Bid setBiderCountry] setting country "+country);
	}
	public void setBiderLocation(String location){
//		binder=new User();
		this.binder.setLocation(location);
		if(MySAX.DEBUG)
			System.out.println("[Bid setBiderLocation] setting location "+location);
	}
	public void setBiderUserid(String UserId){
//		binder=new User();
		this.binder.setUserId(UserId);
		if(MySAX.DEBUG)
			System.out.println("[Bid setBiderUserid] setting UserId "+UserId);
	}
	public void BideruserCheck(){
		this.binder.userCheck();
		if(MySAX.DEBUG)
			System.out.println("[Bid BideruserCheck] userCheck");
	}
	public User getUser(){
		return this.binder;
	}
	public String getItemId(){
		return this.ItemId;
	}
	public String getTime(){
		return this.Time;
	}
	public String getAmount(){
		return this.Amount;
	}
}
