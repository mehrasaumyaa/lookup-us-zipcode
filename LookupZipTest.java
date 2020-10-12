/*Name: Saumyaa Mehra
 * File : LookupZipTest.java
 * Description: Program to run JUnit tests
 */
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class LookupZipTest
{
	Place[] places= new Place[99999];
	Place p;
		@Test 
		void Test1()
		{ 
			assertEquals("19010:Bryn Mawr:PA", LookupZip.lookupZip(places,"19010"));
		}
		
		@Test
		void Test2()
		{ 
			assertEquals("The zipcode you entered does not exist", LookupZip.lookupZip(places,"123"));
		}
		
		void Test3()
		{ 
			assertEquals("The zipcode you entered does not exist", LookupZip.lookupZip(places,"abc"));
		}
		
}
