

/* Parser skeleton for processing item-???.xml files. Must be compiled in
 * JDK 1.5 or above.
 *
 * Instructions:
 *
 * This program processes all files passed on the command line (to parse
 * an entire diectory, type "java MyParser myFiles/*.xml" at the shell).
 *
 * ***********************************************************************
 * Summary：
 * 
 * In this programming, we user a main class MySax and other four table class to parse and store data.
 * In MySAX, we overwrite class handler and use stackflow to read each record.
 * We use tag and currentNode to record the current position of parser, i.e. when we process some content, we should know which table
 * should be insert.
 * Some data come from the attributes of an element, we read them when start reading the element.
 * Most data come from the content of an element, we read them and insert them into tables, and output them after reading the elements.
 * How to solve the duplicate records? We use hashmap to store data in memory. Maybe we occupy some memory, it is great useful to 
 * avoid records duplication in final outputs.
 * Some tables can be record in csv files directly, and some should be waiting until all xml files completing because we should eliminate
 * duplicate records in memory.
 * More details can be found in java files and annotations.
 */

import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MySAX extends DefaultHandler
{
//	static int user_num=0;
	static boolean DEBUG=true;
	/*
	 * According to DTD, we initialize two tables Bid and Items which used ATTLIST structure.. 
	 */
	Bid bid;
	Items items;
	
	/*
	 * Here we use "tag" and "currentNode" to mark the position of parser.
	 * "Tag" records current element. i.e. the attribute of table.Tag should be clear after finishing reading an element.
	 * "currentNode" recodes current node. i.e. the table.
	 */
	private String tag = null;
	private String currentNode;

    public static void main (String args[])
	throws Exception
    {
    	
	/*
	 * Here we use class:handler to parse each file on command line.
	 */
    
		XMLReader xr = XMLReaderFactory.createXMLReader();
		MySAX handler = new MySAX();
		xr.setContentHandler(handler);
		xr.setErrorHandler(handler);
		for (int i = 0; i < args.length; i++) {
		    FileReader r = new FileReader(args[i]);
		    xr.parse(new InputSource(r));
		}
		User.writeTocsv();
//		System.out.println(user_num);
    }


    public MySAX ()
    {
    	super();
    }

    /* Returns the amount (in XXXXX.xx format) denoted by a money-string
     * like $3,453.23. Returns the input if the input is an empty string.
     * I.e, remove the $ from money type input.
     */
    static String strip(String money) {
        if (money.equals(""))
            return money;
        else {
            double am = 0.0;
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
            try { am = nf.parse(money).doubleValue(); }
            catch (ParseException e) {
                System.out.println("This method should work for all " +
                                   "money values you find in our data.");
                System.exit(20);
            }
            nf.setGroupingUsed(false);
            return nf.format(am).substring(1);
        }
    }
    
    /*
     * convert date type to timestamp type.
     */

    public static String dateFormater(String datestamp){
		SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yy HH:mm:ss", Locale.US);
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = null;
		try {
			Date d=sdf.parse(datestamp);
			s=sdf2.format(d).toString();
			
		}catch (ParseException ex) {
        	ex.printStackTrace();  
        }
		return s;
		
	}
    

    public void startDocument ()
    {
    /*
     * parse each document from Item element.
     */
    	currentNode="Item";
    }


    public void endDocument ()
    {
    }

    public void startElement (String uri, String name,
			      String qName, Attributes atts)
    {	
    	/*
    	 * qName and name can store current the name of element.
    	 */
		if("".equals(name))
			tag = qName;
		else
			tag=name;
       	 
		/*
		 * If current element is bid, call method bidstart() (from Items.java) to create a new bid object.
		 * We will give explicit details in Items.java.
		 * Bids is also a node, we should recode it for some elements in its list.
		 */
		if("Bids".equals(name)||"Bids".equals(qName)){
			items.bidstart();
			if(DEBUG)
				System.out.println("[MySAX] Setting bids start");
		}
		if("Bids".equals(qName)||"Bids".equals(qName)){
			currentNode="Bids";
			if(DEBUG)
				System.out.println("[MySAX] Set current node to qName");
		}
		
		
		/*
		 * If current element is item, we create a new item object. 
		 * Items is also a node, we should recode it for some elements in its list.
		 */
	
		if("Item".equals(qName)||"Item".equals(name)){
			if(DEBUG)
				System.out.println("[MySAX] Reading new Item");
			items=new Items();
		}
		if("Item".equals(qName)||"Item".equals(name)){
			currentNode="Item";
			if(DEBUG)
				System.out.println("[MySAX] Set current node to item");
		}
		
	/*
	 * Through reading the tag, we can insert data in different objects.
	 * Following switch can insert ID or Longitude & Latitude.
	 * These data can be read through attributes of elements.
	 */
	 switch (tag) {
   	    case "Item":
   	    	items.setItemid(atts.getValue("ItemID"));
   	    	if(DEBUG)
				System.out.println("[MySAX] Setting Itemid to "+atts.getValue("ItemID"));
   	    	break;
   	   
   	    case "Bidder":
   	    	items.setbid_rating(atts.getValue("Rating"));
   	    	items.setbiduserid(atts.getValue("UserID"));
   	    	if(DEBUG)
				System.out.println("[MySAX] Setting bidder Rating to "+atts.getValue("Rating"));
   	    	if(DEBUG)
				System.out.println("[MySAX] Setting bidder UserID to "+atts.getValue("UserID"));
//   	    	user_num++;
   	    	break;
   	    case "Location":
   	    	if("Item".equals(currentNode)){
   	    		items.setLatitude(atts.getValue("Latitude"));
   	    		items.setLongitude(atts.getValue("Longitude"));
   	    		if(DEBUG)
   					System.out.println("[MySAX] Setting Latitude to "+atts.getValue("Latitude"));
   	    		if(DEBUG)
   					System.out.println("[MySAX] Setting Longitude to "+atts.getValue("Longitude"));
   	    		
   	    	}
   	    	break;
   	    
   	    case "Seller":
   	    	items.setSellerId(atts.getValue("UserID"));
//   	    	user_num++;
   	    	items.setseller_rating(atts.getValue("Rating"));
   	    	items.addseller();
   	    	if(DEBUG)
					System.out.println("[MySAX] Setting seller UserID to "+atts.getValue("UserID"));
   	    	if(DEBUG)
				System.out.println("[MySAX] Setting seller Rating to "+atts.getValue("Rating"));
   	    	break;
   	   
       	}
    }
    	


    public void endElement (String uri, String name, String qName)
    {
    	/*
    	 * If complete reading Bids, call method: bidend().
    	 * We can give some explicit details in items.java.
    	 */
    	if("Bid".equals(name)||"Bid".equals(qName)){
			items.bidend();
			if(DEBUG)
				System.out.println("[MySAX] Setting bid end");
		}
    	
    	/*
    	 * If complete reading item, call method writeTOcsv to create the table.
    	 */
    	if("Item".equals(qName)||"Item".equals(name)){
    		try {
				items.writeTOcsv();
				if(DEBUG)
					System.out.println("[MySAX] Writting to csv file");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    	/*
    	 * After reading Bids, we should come back node Item.
    	 */
		if("Bids".equals(qName)||"Bids".equals(name)){
			currentNode="Item";
			currentNode="Item";
			if(DEBUG)
				System.out.println("[MySAX] Set current node to item");
		}
    }


    public void characters (char ch[], int start, int length)
    {
    	/*
    	 * 	This method can read the content from each element and insert them in objects depend on current tag.
    	 *  After reading the content, the tag should be clear for next reading.
    	 */
    	String content = String.copyValueOf(ch, start, length).replaceAll("\n", "");
//    		
    	if(tag!=null){
	    switch (tag) {
	    
	    case "Name":
	    	items.setName(content.replaceAll("\"", ""));
	    	tag = null;
	    	if(DEBUG)
				System.out.println("[MySAX] Setting Name to "+content);
	    	break;
	    case "Category":
	    	items.setcategory(content);
	    	tag = null;
	    	if(DEBUG)
				System.out.println("[MySAX] Setting Category to "+content);
	    	break;
	    case "Currently":
	    	items.setCurrently(strip(content));
	    	tag = null;	
	    	if(DEBUG)
				System.out.println("[MySAX] Setting Currently to "+content);
	    	break;
	    case "Buy_Price":
	    	items.setBuyPrice(strip(content));
	    	tag = null;	
	    	if(DEBUG)
				System.out.println("[MySAX] Setting Buy_Price to "+content);
	    	break;
	    case "First_Bid":
	    	items.setFirstBid(strip(content));
	    	tag = null;
	    	if(DEBUG)
				System.out.println("[MySAX] Setting First_Bid to "+content);
	    	break;
	    case "Number_of_Bids":
	    	items.setNumberOfBids(content);
	    	tag = null;
	    	if(DEBUG)
				System.out.println("[MySAX] Setting Number_of_Bids to "+content);
	    	break;
	    	
       /*
        * If Location belongs to Item, we should insert the data in table item.
        * If Location belongs to Bid, we should insert the data in table bids.
        */
	    case "Location":
	    	if("Item".equals(currentNode)){
	    		items.setLocation(content);
	    		if(DEBUG)
					System.out.println("[MySAX] Setting item Location to "+content);
	    	}else if("Bids".equals(currentNode)){
	    		items.setbidlocation(content);
	    		if(DEBUG)
					System.out.println("[MySAX] Setting Bids Location to "+content);
	    	}
	    	tag=null;
	    	break;
	    
    	/*
         * If Country belongs to Item, we should insert the data in table item.
         * If Country belongs to Bid, we should insert the data in table bids.
         */
	    case "Country":
	    	if("Item".equals(currentNode)){
	    		items.setCountry(content);
	    		if(DEBUG)
					System.out.println("[MySAX] Setting item Country to "+content);
	    	}else if("Bids".equals(currentNode)){
	    		items.setbidCountry(content);
	    		if(DEBUG)
					System.out.println("[MySAX] Setting bidder Country to "+content);
	    	}
	    	tag=null;
	    	break;
	    case "Time":
	    	items.setbidTime(dateFormater(content));
	    	tag=null;
	    	if(DEBUG)
				System.out.println("[MySAX] Setting Time to "+content);
	    	break;
	    case "Amount":
	    	items.setbidAmount(strip(content));
	    	tag=null;
	    	if(DEBUG)
				System.out.println("[MySAX] Setting Amount to "+content);
	    	break;
	    case "Started":
	    	items.setstart(dateFormater(content));
	    	tag=null;
	    	if(DEBUG)
				System.out.println("[MySAX] Setting Started to "+content);
	    	break;
	    case "Ends":
	    	items.setends(dateFormater(content));
	    	tag=null;
	    	if(DEBUG)
				System.out.println("[MySAX] Setting Ends to "+content);
	    	break;

	    case "Description":
	    	items.setDescription(content.replaceAll("\"", "").replaceAll("\'", ""));
	    	tag = null;
	    	if(DEBUG)
				System.out.println("[MySAX] Setting Description to "+content);
	    	break;   	
	    }
    	}
    }
}
	    	
    	
	    	
	    	
	
