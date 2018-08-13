package org.sunbird.testscripts;

import java.util.List;
import java.util.Random;

import org.apache.poi.util.SystemOutLogger;
import org.sunbird.generic.ReadTestDataFromExcel;
import org.sunbird.testdata.TestDataForSunbird;
import org.testng.annotations.Test;

public class TestRun 
{
	public static void main(String[] args)
	{
		List <TestDataForSunbird> objListOFTestDataForSunbird1=null;
		objListOFTestDataForSunbird1 = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetcredentials");
	//	objListOFTestDataForSunbird1 = ReadTestDataFromExcel.getTestDataForSunbird("testdatasheetprofileaddress");
		System.out.println(objListOFTestDataForSunbird1.get(0).getUsername());
		System.out.println(objListOFTestDataForSunbird1.get(0).getPassword());
		/*System.out.println(objListOFTestDataForSunbird1.get(0).getCity());
		System.out.println(objListOFTestDataForSunbird1.get(0).getState());
		System.out.println(objListOFTestDataForSunbird1.get(0).getCountry());
		System.out.println(objListOFTestDataForSunbird1.get(0).getPincode());
		System.out.println(objListOFTestDataForSunbird1.get(0).getSummary());
		System.out.println(objListOFTestDataForSunbird1.get(0).getOccupation());
		System.out.println(objListOFTestDataForSunbird1.get(0).getOrganization());
		System.out.println(objListOFTestDataForSunbird1.get(0).getPercentage());*/
		
		/*Object[] objArr=new Object[];
		public static int getRandom(int[] array) 
		{
	        int rnd = new Random().nextInt(array.length);
	        System.out.println(array[rnd]);
		
		
	}	*/

	
/*
	   String[] items = new String[]{"course","course test","course creator"};

	   Random rand = new Random();

	
	        System.out.println(items[rand.nextInt(items.length)]);*/
	  
	}

}
