package testcases;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import baseClass.TestBase;
import utilities.ExcelReader;

public class TestDataProvider extends TestBase {

	/*@Test(dataProvider="dp")
	public void testTest(Hashtable<String, String> data) {
		
		System.out.println(data.get("firstName")+" "+data.get("lastName")+"---"+data.get("postCode")+"---"+data.get("currency1")+"---"+data.get("currency2")+"---"+data.get("currency3"));
		System.out.println(data.get("FirstName")+" "+data.get("LastName")+"---"+data.get("PostCode")+"---"+data.get("AccountNumber"));
		
	}*/
	
	public static String sheetName;
	
	@DataProvider
	public Object[][] getData() {

		sheetName = "addCustomerTest";

		int rowCount = excel.getRowCount(sheetName);
		System.out.println(excel.getRowCount(sheetName));

		int colCount = excel.getColumnCount(sheetName);
		System.out.println(excel.getColumnCount(sheetName));

		Object[][] data = new Object[rowCount - 1][1];

		Hashtable<String, String> table=null;
		
		// data[0][0] = "";
		for (int row = 2; row <= rowCount; row++) {
			
			table = new Hashtable<String, String>();
			
			for (int col = 0; col < colCount; col++) {

				//data[row - 2][col] = excel.getCellData(sheetName, col, row);
				
				table.put(excel.getCellData(sheetName, col, 1), excel.getCellData(sheetName, col, row));
				data[row-2][0]=table;
				
			}
		}
		return data;
	}
	
	
	@DataProvider
	public Object[][] getAccNumber() {

		ExcelReader excelRead = new ExcelReader(
				System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\accDetails.xlsx");
		sheetName = "custAccDetails";

		int rowCount = excelRead.getRowCount(sheetName);
		int colCount = excelRead.getColumnCount(sheetName);
		System.out.println(rowCount);

		Object[][] data = new Object[rowCount - 1][1];
		
		Hashtable<String, String> table = null;

		for (int row = 2; row <= rowCount; row++) {
			
			table = new Hashtable<String, String>();

			for (int col = 0; col < colCount; col++) {

				//data[row - 2][col] = excelRead.getCellData(sheetName, col, row);
				
				table.put(excelRead.getCellData(sheetName, col, 1), excelRead.getCellData(sheetName, col, row));
				
				data[row-2][0]=table;
				
			}
		}
		return data;

	}
	
	@DataProvider(name="dp")
	public Object[][] dp() {
	  List<Object[]> result = Lists.newArrayList();
	  result.addAll(Arrays.asList(getData()));
	  result.addAll(Arrays.asList(getAccNumber()));
	  return result.toArray(new Object[result.size()][]);
	}

	
	
	
}
