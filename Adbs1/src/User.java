

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class User {
	
	/*
	 * Here are the five attributes in table USER.
	 * We create a hashmap object to store user and avoid records duplication.
	 */
	private String UserId="";
	private String Location="";
	private String seller_rating="";
	private String bid_rating="";
	private String Country="";
	private static HashMap<String,User> userMap=new HashMap<String,User>();
	
	/*
	 * Basic methods for inserting data and get data.
	 */
	
	public void setUserId(String UserId){
		this.UserId=UserId;
		this.userCheck();
		if(MySAX.DEBUG)
			System.out.println("[User setUserId] setting UserId "+UserId);
	}
	public void setLocation(String Location){
		this.Location=Location;
		this.userCheck();
		if(MySAX.DEBUG)
			System.out.println("[User setLocation] setting Location "+Location);
	}
	public void setCountry(String Country){
		this.Country=Country;
		if(MySAX.DEBUG)
			System.out.println("[User setCountry] setting Country "+Country);
		this.userCheck();
		if(MySAX.DEBUG)
			System.out.println("[User setCountry] Checking user");
	}
	public void setbid_Rating(String rating){
		this.bid_rating=rating;
		this.userCheck();
		if(MySAX.DEBUG)
			System.out.println("[User setbid_Rating] setting rating "+rating);
		if(MySAX.DEBUG)
			System.out.println("[User setbid_Rating] Checking rating");
	}
	public void setseller_Rating(String rating){
		this.seller_rating=rating;
		this.userCheck();
		if(MySAX.DEBUG)
			System.out.println("[User setseller_Rating] setting rating "+rating);
	}
	public String getUserId(){
		if(MySAX.DEBUG)
			System.out.println("[User getUserId] getting UserId "+this.UserId);
		return this.UserId;
		
	}
	public String getLocation(){
		if(MySAX.DEBUG)
			System.out.println("[User getLocation] getting Location "+this.Location);
		return this.Location;
		
	}
	public String getCountry(){
		if(MySAX.DEBUG)
			System.out.println("[User getCountry] getting Country "+this.Country);
		return this.Country;	
	}
	public String getbid_Rating(){
		if(MySAX.DEBUG)
			System.out.println("[User getbid_Rating] getting getbid_Rating "+this.bid_rating);
		return this.bid_rating;
	}
	public String getseller_Rating(){
		if(MySAX.DEBUG)
			System.out.println("[User getseller_Rating] getting  seller_rating "+seller_rating);
		return this.seller_rating;
	}
	
	/*
	 * As we know, a user may have two rating: bid rating and sell rating. 
	 * Here we use hash map to ensure that if a user is seller and bidder, his information
	 * will not be record twice. 
	 */
	public void userCheck(){
		if(userMap.containsKey(this.UserId)){
			if(MySAX.DEBUG)
				System.out.println("[User check] setting UserId "+UserId);
			User userFromMap=userMap.get(this.UserId);
			if(MySAX.DEBUG)
				System.out.println("[User check] This user has been exist"+this.getUserId()+"  "+this.getbid_Rating()+"  "+this.getseller_Rating()+"  "+this.getLocation()+"  "+this.getCountry());
			if(userFromMap.getbid_Rating().equals("")&&!this.bid_rating.equals("")){
				userFromMap.setbid_Rating(this.getbid_Rating());
				if(MySAX.DEBUG)
					System.out.println("[User check] Modify bid_rating"+this.getUserId()+"  "+this.getbid_Rating()+"  "+this.getseller_Rating()+"  "+this.getLocation()+"  "+this.getCountry());
			}
			if(userFromMap.getseller_Rating().equals("")&&!this.seller_rating.equals("")){
				userFromMap.setseller_Rating(this.getseller_Rating());
				if(MySAX.DEBUG)
					System.out.println("[User check] Modify seller_rating"+this.getUserId()+"  "+this.getbid_Rating()+"  "+this.getseller_Rating()+"  "+this.getLocation()+"  "+this.getCountry());
			}
			if(userFromMap.getCountry().equals("")&&!this.Country.equals("")){
				userFromMap.setCountry(this.getCountry());
				if(MySAX.DEBUG)
					System.out.println("[User check] Modify contry"+this.getUserId()+"  "+this.getbid_Rating()+"  "+this.getseller_Rating()+"  "+this.getLocation()+"  "+this.getCountry());
			}
			if(userFromMap.getLocation().equals("")&&!this.Location.equals("")){
				userFromMap.setLocation(this.getLocation());
				if(MySAX.DEBUG)
					System.out.println("[User check] Modify localtion"+this.getUserId()+"  "+this.getbid_Rating()+"  "+this.getseller_Rating()+"  "+this.getLocation()+"  "+this.getCountry());
			}
//			userMap.remove(this.getUserId());
			userMap.put(this.UserId, userFromMap);
			if(MySAX.DEBUG)
				System.out.println("[User check] Putting old user to map "+this.UserId);
			//userMap.put(this.getUserId(), userFromMap);		
		}else{
			userMap.put(this.getUserId(), this);
			if(MySAX.DEBUG)
				System.out.println("[User check] Putting new user to map "+this.getUserId());
		}
	}
	
	/*
	 * Writing user information in csv files.
	 */
	public static void writeTocsv() throws IOException{
		FileWriter fwusers=new FileWriter("./users.csv",true);
//		System.out.println(userMap.size());
		Iterator iter=userMap.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry entry = (Map.Entry) iter.next();
			User u=(User) entry.getValue();
//			if(u.getUserId()!=null&&!u.getUserId().equals(" ")){
//				fwusers.write(entry.getKey()+Items.split);
			fwusers.write(u.getUserId()+Items.split);
			fwusers.write(u.getbid_Rating()+Items.split);
			fwusers.write(u.getseller_Rating()+Items.split);
			fwusers.write(u.getCountry()+Items.split);
			fwusers.write(u.getLocation());
			fwusers.write("\n");
			}
//		}
		fwusers.flush();
		fwusers.close();
	}
}
