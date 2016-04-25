package com.org.Slp_Project;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.Test;
import org.testng.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class SLP2016_DriverTest {
	
	// Define global variables 
	String vXLPath, vXLTC, vXLTS, vXLTD, vXLEM;
	String vXLTSResPath, vXLTCResPath, vXLTDResPath;
	int xTCRows, xTCCols, xTSRows, xTSCols, xTDRows, xTDCols, xEMCols, xEMRows; 
	String[][] xTCData, xTSData, xTDData, xEMData;
	//WebDriver myD;
	String vKW, vXP, vData;
	String vResult, vError, vTCResult;
	SLP_KeyWords Keys = new SLP_KeyWords();
	
	@Test
	public void driverTest() throws Exception{
		
	// Define the webdriver
	//	myD = new FirefoxDriver();
	//	myD.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	//	myD.get("http://test.atomic77.in/TWAv1/1");
		
		vXLPath = "C:/NetraSLPData/NewSlpData/NewSLP_HBFW_Netra_032016.xlsx";
		vXLTSResPath = "C:/NetraSLPData/NewSlpData/HF4TS_Res_";
		vXLTCResPath = "C:/NetraSLPData/NewSlpData/HF4TC_Res_";
		vXLTDResPath = "C:/NetraSLPData/NewSlpData/HF1TD_Res.xls";
		vXLTC = "TestCases";
		vXLTS = "TestSteps";
		vXLTD = "TestData";
		vXLEM = "ElementMap";
		
		// Read this excel
		xTCData = readXL(vXLPath, vXLTC); // returns TestCases Data 
		xTSData = readXL(vXLPath, vXLTS); // returns TestSteps Data
		xTDData = readXL(vXLPath, vXLTD); // returns TestData Data
		xEMData = readXL(vXLPath, vXLEM); // returns ElementMap Data
		
		// Get the number of rows and columns in both the sheets
		xTCRows = xTCData.length;
		xTCCols = xTCData[0].length;

		xTSRows = xTSData.length;
		xTSCols = xTSData[0].length;
		
		xTDRows = xTDData.length;
		xTDCols = xTDData[0].length;
		
		xEMRows = xEMData.length;
		xEMCols = xEMData[0].length;
		String AllRows = "TC Roc "+xTCRows;
			//	+"TData Row"+xTDRows +"Element Map"+xEMRows;
		System.out.println(AllRows); 
		// Run the KDF main code for different sets of Test Data
		long startTime, endTime, totalTime;
		
		for (int k=1; k<xTDRows; k++){
			if (xTDData[k][1].equals("Y")){  // TestData to be executed??
				startTime = System.currentTimeMillis();
				
				
				// The main code of execution is over here 
				for (int i=1; i<xTCRows; i++) { // Go through each row in TC
					if (xTCData[i][2].equals("Y")){ // Verify if TC is ready for run
						System.out.println("Test Case ID to RUN: " + xTCData[i][0]);
						vTCResult = "Pass";
						for (int j=1; j <xTSRows; j ++){ // Go through each row in TS
							if (xTCData[i][0].equals(xTSData[j][0])) { // Do the Test Case ID's match
								vKW = xTSData[j][3];
								vXP = xTSData[j][4];
								vXP = getElementIdentifier(xTSData[j][4], k);
								vData = getTestData(xTSData[j][5], k);
								System.out.println("vXP is " + vXP);
								System.out.println("vData is " + vData);
								vResult = "Pass";
								vError = "No Error";
								
								try {
									executeKW(vKW, vXP, vData);
								} catch (Exception e) {
									vResult = "Fail";
									vError = "Error: " + e;
								}
								xTSData[j][6] = vResult;
								xTSData[j][7] = vError;
								// Set the Test Case to Fail as soon as even 1 step fails.
								if (vResult.equals("Fail")) {
									vTCResult = "Fail";
								}
								xTCData[i][3] = vTCResult;
							}
						}
						
					} else { 
						System.out.println("Test Case Not ready for execution.");
					}		
				
				}
				writeXL(vXLTSResPath + xTDData[k][0] + ".xls", "TestSteps", xTSData);				
				writeXL(vXLTCResPath + xTDData[k][0] + ".xls", "TestCases", xTCData);
			endTime = System.currentTimeMillis();
			totalTime = (endTime - startTime)/1000;
			System.out.println("To execute " + xTDData[k][0] + " total time taken is " + totalTime + " seconds.");
			}
		}
		Keys.myD.quit();
	}
	
	// Custom Keyword Functions
	
	public String getTestData(String fData, int fRowNumber){
		// Purpose : Gets the actual value of the test data variable
		// Inputs : Data variable name and the row number
		// Output : Data value
		
		for (int a=0; a<xTDCols; a++) {
			if (fData.equals(xTDData[0][a])){
				return xTDData[fRowNumber][a];
			}	
		}
		return fData;
		
	}
	
	public String getElementIdentifier(String fXP, int fRowNumber){
		// Purpose : Gets the actual xpath of the test step
		// Inputs : Element name and the row number
		// Output : xpath value
		
		for (int a=1; a<xEMRows; a++) {
			if (fXP.equals(xEMData[a][2])){
				return xEMData[a][3];
			}	
		}
		return fXP;
	}
	
	public void executeKW(String vKW, String vXP, String vData) throws Exception{
		// Purpose : Calls the corresponding function to execute the Test Case (Keyword) 
		// Inputs : KW, xP, Data
		// Output : No output
		// Chose the Keyword and call the corresponding function
		switch (vKW) {
			case "navigateUrl" :
				System.out.println("Running: " + vKW);
				Keys.getURL(vData);
				break;
			case "clickLink" :
				System.out.println("Running: " + vKW);
				Keys.clickLink(vXP);
				break;
			case "enterText" :
				System.out.println("Running: " + vKW);
				Keys.enterText(vXP, vData);
				break;
			case "clickElement" :
				System.out.println("Running: " + vKW);
				Keys.clickElement(vXP);
				break;
			case "clickLogin" :
				System.out.println("Running: " + vKW);
				Keys.clickLogin(vXP);
				break;
			case "ConfirmYes" :
				System.out.println("Running: " + vKW);
				Keys.ConfirmYes();
				break;	
			case "verifyText" :
				System.out.println("Running: " + vKW);
				vResult = Keys.verifyText(vXP, vData);
				if (vResult.equals("Fail")){
					vError = "Verification Failed";
				}
				 
				System.out.println("Result is " + vResult);
				break;
			case "verifyUpCaseLetter" :
				System.out.println("Running: " + vKW);
				vResult = Keys.verifyUpCaseLetter(vXP, vData);
				if (vResult.equals("Fail")){
					vError = "Verification Failed";
				}
				 
				System.out.println("Result is " + vResult);
				break;
	
			case "verifyNotText" :
				System.out.println("Running: " + vKW);
				vResult = Keys.verifyNotText(vXP, vData);
				if (vResult.equals("Fail")){
					vError = "Verification Failed";
				}
				 
				System.out.println("Result is " + vResult);
				break;
				
			case "verifyElementPresent" :
				System.out.println("Running: " + vKW);
				vResult = Keys.verifyElementPresent(vXP, vData);
				if (vResult.equals("Fail")){
					vError = "Verification Failed";
				}
				 
				System.out.println("Result is " + vResult);
				break;
			case "verifyUIPresent" :
				System.out.println("Running: " + vKW);
				vResult = Keys.verifyUIPresent(vXP, vData);
				if (vResult.equals("Fail")){
					vError = "Verification Failed";
				}
				System.out.println("Result is " + vResult);
				break;
			case "verifyUIXPthPresent" :
				System.out.println("Running: " + vKW);
				vResult = Keys.verifyUIXPthPresent(vXP, vData);
				if (vResult.equals("Fail")){
					vError = "Verification Failed";
				}
				System.out.println("Result is " + vResult);
				break;
							
			case "verifyElement" :
				System.out.println("Running: " + vKW);
				vResult = Keys.verifyElement(vXP, vData);
				if (vResult.equals("Fail")){
					vError = "Verification Failed";
				}
				 
				System.out.println("Result is " + vResult);
				break;	
			case "clickSubNewCourse":
				System.out.println("Running: " + vKW);
				 Keys.clickSubNewCourse(vXP, vData);
			//	if (vResult.equals("Fail")){
			//		vError = "Verification Failed";
			//	}
				 
			//	System.out.println("Result is " + vResult);
				break;
			case "clickBigNewCourseIcon":
				System.out.println("Running: " + vKW);
				 Keys.clickBigNewCourseIcon(vXP, vData);
			//	if (vResult.equals("Fail")){
			//		vError = "Verification Failed";
			//	}
				 
			//	System.out.println("Result is " + vResult);
				break;
			case "hitEnter" :
				System.out.println("Running: " + vKW);
				Keys.hitEnter(vXP);
				break;
			default :
				System.out.println("ALERT : Keyword is missing. " + vKW);
				break;
		}
			}
	
	// Read and Write from an excel
	// Method to write into an XL
		public void writeXL(String sPath, String iSheet, String[][] xData) throws Exception{

		    	File outFile = new File(sPath);
		        XSSFWorkbook wb = new XSSFWorkbook();
		        XSSFSheet osheet = wb.createSheet(iSheet);
		        int xR_TS = xData.length;
		        int xC_TS = xData[0].length;
		    	for (int myrow = 0; myrow < xR_TS; myrow++) {
			        XSSFRow row = osheet.createRow(myrow);
			        for (int mycol = 0; mycol < xC_TS; mycol++) {
			        	XSSFCell cell = row.createCell(mycol);
			        	cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			        	cell.setCellValue(xData[myrow][mycol]);
			        }
			        FileOutputStream fOut = new FileOutputStream(outFile);
			        wb.write(fOut);
			        fOut.flush();
			        fOut.close();
		    	}
			}
		
		// Method to read XL
		public String[][] readXL(String sPath, String iSheet) throws Exception{  
		// Purpose : Read data from an excel sheet
		// I/P : Path and Sheet name.
		// O/P : 2D Array containing the xl sheet data
			
			String[][] xData;
			int xRows, xCols;
			
			File myxl = new File(sPath);                                
			FileInputStream myStream = new FileInputStream(myxl);                                
			XSSFWorkbook myWB = new XSSFWorkbook(myStream);                                
			XSSFSheet mySheet = myWB.getSheet(iSheet);                                 
			xRows = mySheet.getLastRowNum()+1;                                
			xCols = mySheet.getRow(0).getLastCellNum();                                
			xData = new String[xRows][xCols];        
			for (int i = 0; i < xRows; i++) {                           
					XSSFRow row = mySheet.getRow(i);
					for (int j = 0; j < xCols; j++) {                               
						XSSFCell cell = row.getCell(j);
						String value = "-";
						if (cell!=null){
							value = cellToString(cell);
						}
						xData[i][j] = value;      
						//System.out.println(value);
						//System.out.print("--");
						}        
					}                                      
			return xData;
		}
			
			//Change cell type
			public static String cellToString(XSSFCell cell) { 
				// This function will convert an object of type excel cell to a string value
				int type = cell.getCellType();                        
				Object result;                        
				switch (type) {                            
					case XSSFCell.CELL_TYPE_NUMERIC: //0                                
						result = cell.getNumericCellValue();                                
						break;                            
					case XSSFCell.CELL_TYPE_STRING: //1                                
						result = cell.getStringCellValue();                                
						break;                            
					case XSSFCell.CELL_TYPE_FORMULA: //2                                
						throw new RuntimeException("We can't evaluate formulas in Java");  
					case XSSFCell.CELL_TYPE_BLANK: //3                                
						result = " ";                                
						break;                            
					case XSSFCell.CELL_TYPE_BOOLEAN: //4     
						result = cell.getBooleanCellValue();       
						break;                            
					case XSSFCell.CELL_TYPE_ERROR: //5       
						throw new RuntimeException ("This cell has an error");    
					default:                  
						throw new RuntimeException("We don't support this cell type: " + type); 
						}                        
				return result.toString();      
				}

	
}
