

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemCategory {
	
	/*
	 * The two attributes in table ItemCategory.
	 */
	
	private String ItemId;
	private String Category;
	
	/*
	 * Basic methods for inserting data and get data.
	 */
	
	public void setItemId(String string){
		this.ItemId=string;
	}
	public void setCategory(String category){
		this.Category=category;
	}
	public String getItemId(){
		return this.ItemId;
	}
	public String getCategory(){
		return this.Category;
	}
}
