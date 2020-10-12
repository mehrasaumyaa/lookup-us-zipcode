/*Name: Saumyaa Mehra
 * File : Place.java
 * Description: Program to create a place object whenever the class is called.
 */

public class Place
{
	private String zipcode; //holds the value of the zipcode
	private String town;//holds the value of the name of the town 
	private String state;//holds the value of the name of the state 
	
	public Place(String zip, String t, String s)
	{
		zipcode=zip;
		town = t;
		state=s;
	}
	
	public String toString()
	{ 
		return zipcode+":"+town+":"+state;
	}
	
	//to access zipcode
	public String getZipCode()
	{ 
		return zipcode;
	}
	
	//to access name of the town
	public String getTown()
	{
		return town;
	}
	
	//to access name of the state
	public String getState()
	{
		return state;
	}
}
