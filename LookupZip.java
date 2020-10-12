/*Name: Saumyaa Mehra
 * File : LookupZip.java
 * Description: Program to ask the user for a zipcode and return the town and state the zipcode belongs to.
 */
import java.util.*;
import java.io.*;

import java.util.*;
import java.io.*;

public class LookupZip
{ 
	public static void main(String[] args) throws ZipParseException, FileNotFoundException
	{
		
		String search = "";
		Scanner scan = new Scanner(System.in);
		
		//set a String to the filename of the csv file
		String filenames = new String("src/uszipcodes.txt");
		
		//set a String to store the zip code entered by the user
		String zip = "";
		
		//initialize a boolean to check if the user wants to check another zip code
		boolean repeat = true;
		
		//create a do while loop to repeat and allow user to enter multiple zip codes
		while(repeat == true)
		{
			//ask the user for a zipCode input and store the zipCode
			System.out.print("What zip code should I look up? ");
			zip = scan.nextLine();
			
			//go through the zipcodes csv file and print out the information
			//correlating to the zip code entered by the user
			try(Scanner file = new Scanner(new FileReader(filenames)))
			{
				//create an array that will be sorted through to find the user entered zip code
				Place[] array = readZipCodes(filenames);
				//String location = town + ", " + state;
				
				//create a Place for the 
				Place correct = lookupZip(array, zip);
				
				//print out the zip code and location correlating to it
				System.out.println("The zip code " + zip + " belongs to " + correct.getTown()+", "+correct.getState());//lookupZip(array, town + state))
				
				//could not figure out how to bring to lineNumber value up from the 
				//readZipCodes method but I assumed the ZipParseException worked similar to this
				/*int lineNumber = 10000;
if(lookupZip(array, zip) == null)
{
throw new ZipParseException(lineNumber, correct.toString());
}*/
			}
			//if file is not found, print this message
			catch (FileNotFoundException e)
			{
				System.out.println("File not found : " + e.getMessage());
			}
			//if the zipcode entered by the user doesn't exist, print this message
			catch(NullPointerException p)
			{
				System.out.println("The zipcode " + zip + " does not exist");
			}
			
			//ask the user if they want to enter another zip code
			System.out.print("Do you want me to search again? ");
			search = scan.nextLine();
			if(search.equalsIgnoreCase("no"))
			{
				repeat = false;
			}
		}
		
		//once the user enters "no" exit loop and print this message
		System.out.println("Goodbye!");
	}
	
	/** Parses one line of input by creating a Place that
	 * denotes the information in the given line
	 * @param lineNumber The line number of this line
	 * @param line One line from the zipcodes file
	 * @return A Place that contains the relevant information
	 * (zip code, town, state) from that line
	 * @throws FileNotFoundException 
	 */
	public static Place parseLine(int lineNumber, String line)
			throws ZipParseException, FileNotFoundException
	{ 
		//separate the entire line of code by commas to save the individual parts to form a place
		String[] location = line.split(",");
		String zipCode = location[0];
		String town = location[1];
		String state = location[2];
		
		//store the variables into a new Place
		Place place = new Place(zipCode, town, state);
		
		//return the Place
		return place;
	}
	
	/** Reads a zipcodes file, parsing every line
	 * @param filename The name of the zipcodes file
	 * @return The array of Places representing all the
	 * data in the file.
	 */
	public static Place[] readZipCodes(String filename)
			throws FileNotFoundException, ZipParseException
	{
		//will be used to increment array values to store in places array
		int i = 0;
		//will be used to increment the line number 
		int lineNum = 0;
		
		//set filename equal to the csv file
		
		//find the length of the csv file and set the size as that length
		File file = new File(filename);
		//Places array that will be returned and has a size set to the length of the csv file
		Place[] places = null;
		
		//used to skip the first line of the csv file and represents the line of code in the places Array
		String lines = "";
		
		//sort through the csv file to save all the locations in a places array
		try(Scanner files = new Scanner(new FileReader(file)))
		{
			Place p = parseLine(0, files.nextLine());
			int sizes = Integer.parseInt(p.getZipCode());
			
			//skip the first line
			//files.nextLine();
			
			places = new Place[sizes];
			
			//sorts through all lines of the csv file
			while(files.hasNextLine())
			{ 
				//set String lines equal to the line of information in the csv file
				lines = files.nextLine();
				
				//set each place/location into the places array
				places[i++] = parseLine(lineNum, lines);
				
				//increment the line number of the csv file
				lineNum += 1;
				//increment the array value
				//i++;
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File not found : " + e.getMessage());
		}
		//return the array
		return places;
	}
	
	/** Find a Place with a given zip code
	 * @param zip The zip code (as a String) to look up
	 * @return A place that matches the given zip code,
	 * or null if no such place exists.
	 */
	public static Place lookupZip(Place[] places, String zip)
	{
		//set a new Place to null as default unless a zip code is found
		int low = 0;
		int high = places.length;
		int mid = 0;
		
		while(low <high)
		{
			mid = (low + high) / 2;
			
			String zipToCheck = places[mid].getZipCode();
			int cmp = zip.compareTo(zipToCheck);
			
			if(cmp > 0)
			{
				low = mid + 1;
			}
			else if(cmp < 0)
			{
				high = mid;
			}
			else if(cmp==0)
			{
				return places[mid];
			}
		}
		return null;
	}
}

/*public class LookupZip
{
	public static Place parseLine(int lineNumber, String line) throws ZipParseException
	{	
		//stores the different parts of the line
		String[] pieces = line.split(",");
		String zip = pieces[0];  	//stores the zipcode given in the line
		String town= pieces[1]; 	//stores the town given in the line
		String state=pieces[2];		//stores the state given in the line
		
		//if there is an error in the line passed to the method, a ZipParseException is thrown
		if((pieces[0]==null)||(pieces[1]==null)||(pieces[1]==null)||(pieces[2]==null))
		{
			throw new ZipParseException( lineNumber, line);
		}
		
		//creating a new object Place using the contents of the line read from the file
		Place p = new Place(zip,town,state);
		return p;		
	}
	
	public static Place[] readZipCodes(String filename) throws FileNotFoundException, ZipParseException
	{
		//to store the size of the file  
		File file1 = new File(filename);
		double size = file1.length();					//stores the size of the file
		
		//creating an array to store all the contents of the file in the form of place objects
		//Also, converting a double to an integer for the appropriate size of the array
		Place[] allPlaces = new Place[(int)size] ; 
		
		try(Scanner file = new Scanner(new FileReader(filename)))
		{
			int lineNumber =1;
			String lineString; 									//stores each line of the file one by one
			
			int i=0; 						//represents the index of the array allPlaces
		
			//the loop will work till the file has contents
			while(file.hasNextLine())
			{ 
				lineString = file.nextLine();
				//calling parseLine method by passing the line read from the file as a parameter to parse it and create a new place object
				//New place object created is stored in placeP
				Place placeP = parseLine(lineNumber,lineString);
				
				allPlaces[i++]= placeP; //every new object is stored in the array one by one
				lineNumber++;
			}		
		}
		
		catch(FileNotFoundException e)
		{
			System.out.println("File not found : " + e.getMessage());	
		}
		catch(ZipParseException z)
		{ 
			System.out.println("Please enter valid zipcode.");
		}
		return allPlaces;
	}
	
	public static Place lookupZip(Place[] places, String zip)
	{
	//set a new Place to null as default unless a zip code is found
		int low = 0;
		int high = places.length;
		int mid = 0;
		
		while(low <= high)
		{
			mid = (low + high) / 2;
			
			String zipToCheck = places[mid].getZipCode();
			int cmp = zip.compareTo(zipToCheck);
			
			if(cmp > 0)
			{
				low = mid + 1;
			}
			else if(cmp < 0)
			{
				high = mid - 1;
			}
			else
			{
				return places[mid];
			}
		}
		return null;
	}
	

		/*for(int i=0;i<places.length;i++)
		{ 
		//if the zipcode is found, it returns the entire place object or else it returns null
			if((places[i].getZipCode().equals(zip))) 
			{
				return places[i];
			}			
		}
		return null;
	
	
	public static void main(String[] args)
	{ 
		String filename = "src/uszipcodes.txt";
		try
		{
			//stores all the places written in the file, i.e. all the contents of the file in the form of place objects
			Place[] AllPlaces = readZipCodes(filename); 
			String choice; 					//stores the user's choice about if they want to run another search
			
			Scanner in = new Scanner(System.in);
			do
			{
				System.out.print("What zipcode should I look up?");
				String zip = in.next(); //stores the zipcode to be searched for
				
				Place desiredPlace = lookupZip(AllPlaces,zip); //stores the place that is represented by the zipcode and needs to be returned to the user
				
				//if zipcode exists, desiredPlace should not be equal to null
				if(desiredPlace!=null)	
					System.out.println("The zip code "+zip+" belongs to "+desiredPlace.getTown()+", "+desiredPlace.getState());
				
				System.out.print("Do you want me to search again?");
				choice = in.next();			//asking the user to enter their choice again
				
				
			}while(choice.equals("yes")); //loop runs till the user says no
		}
		catch(FileNotFoundException e)
		{ 
			System.out.println("File not found.");
		}
		catch(NullPointerException n)
		{
			System.out.println("The zipcode you entered does not exist.");
		}	
		catch(ZipParseException z)
		{ 
			System.out.println("Enter valid zipcode please");
		}
		
		System.out.print("Good-bye!");	
	}		
}*/





/*Name: Saumyaa Mehra
 * File : LookupZip.java
 * Description: Program to ask the user for a zipcode and return the town and state the zipcode belongs to.
 
import java.util.*;
import java.io.*;

public class LookupZip
{	
	public static ArrayList<Place>
	readZipCodes(String zips, String locs)
			throws FileNotFoundException, ZipParseException
	{
		ArrayList<Place> allPlaces = new ArrayList<> ();
	//	ArrayList<LocatedPlace> locatedPlaces = new ArrayList<> ();
		//ArrayList<PopulatedPlace> populatedPlaces = new ArrayList<> ();
		
		try(Scanner file = new Scanner(new FileReader(zips)))
		{
			String lineString; 		
									
			while(file.hasNextLine())
			{ 
				lineString = file.nextLine();
				
				String[] pieces1 = lineString.split(",");
				String zip = pieces1[0];  	
				String town= pieces1[1]; 	
				String state=pieces1[2];	
				
				allPlaces.add(new Place(zip,town,state)); 
		
			}		
		}
		
		catch (FileNotFoundException e)
		{
			System.out.println("File not found.");
		}
		
		try(Scanner file = new Scanner(new FileReader(locs)))
		{
			//int lineNumber =1;
			String lineString; 		
			String zip;  	
			String town; 	
			String state;	
			String latitude;
			String longitude;
			String population;
									
			while(file.hasNextLine())
			{ 
				lineString = file.nextLine();
				
				String[] pieces2 = lineString.split(",");
				zip = pieces2[0];  	
				town= pieces2[2]; 	
				state=pieces2[3];	
				latitude = pieces2[5];
				longitude = pieces2[6];
				population = pieces2[10];
			
				if(population!=null)
				{
					for(int j=0;j<allPlaces.size();j++)
					{
						{
							if(allPlaces.get(j).getZipCode()==zip)
							{
								PopulatedPlace p1= new PopulatedPlace(zip,town,state,Double.parseDouble(latitude), Double.parseDouble(longitude),Integer.parseInt(population));
								allPlaces.set(j,p1);
							}
						}
					}
				}
				if(latitude!=null && longitude!=null)
				{ 
					for(int j=0;j<allPlaces.size();j++)
					{
						if(allPlaces.get(j).getZipCode()==zip)
						{
							LocatedPlace p2= new LocatedPlace(zip,town,state,Double.parseDouble(latitude), Double.parseDouble(longitude));
							allPlaces.set(j,p2);
						}
					}
				}		
			}			
		}
		
		catch (FileNotFoundException e)
		{
			System.out.println("File not found.");
		}
		
		return allPlaces;
	}
	
	
	public static void main(String[] args)
	{ 
		String file1 = "src/uszipcodes.txt";
		String file2 = "src/ziplocs.csv";
		try
		{
			String choice; 					
			
			Scanner in = new Scanner(System.in);
			do
			{
				System.out.print("What zipcode should I look up?");
				String zip = in.next(); 
				ArrayList<Place> allPlaces = readZipCodes(file1,file2);
				
				for(int i=0;i<allPlaces.size();i++)
				{ 
					if(allPlaces.get(i).getZipCode()==zip)
					{ 
						allPlaces.get(i).placeDescription();
					}
				}
				
				System.out.print("Do you want me to search again? (yes/no)");
				choice = in.next();		
				
			}while(choice.equals("yes")); 
		}
		
		catch(FileNotFoundException e)
		{ 
			System.out.println("File not found.");
		}
		
		catch(ZipParseException z)
		{ 
			System.out.println("Enter valid zipcode please");
		}
		
		System.out.print("Good-bye!");	
	}
	
}*/



