package com.org.Slp_Project;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

//import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SLP_KeyWords {
	
	WebDriver myD;
	
	public SLP_KeyWords () {
		myD = new FirefoxDriver();
		myD.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	
	public void enterText(String fXP, String fText){
		// Purpose : Webdriver and enters a text into text Field. 
		// Inputs : Where to type (fXP), What to type (ftext)
		// Output : No output
		myD.findElement(By.xpath(fXP)).clear();
		myD.findElement(By.xpath(fXP)).sendKeys(fText);
		
	}
	
	public void clickLink(String fLinkText) throws InterruptedException{
	// Purpose : It clicks on a link 
	// Inputs : fLinkText of the link to click on.
	// Output : No output
		myD.findElement(By.linkText(fLinkText)).click();
		Thread.sleep(10000);
	}
	
	public void getURL(String fURL){
	// Purpose : Navigate to a fURL in our Webdriver  
	// Inputs : fURL
	// Output : No output
		myD.get(fURL);
	}
	
	public void navigateURL(String fURL){
	// Purpose : Navigate to a fURL in our Webdriver  
	// Inputs : fURL
	// Output : No output
		myD.navigate().to(fURL);
	}
	
	public void clickElement(String fXP){
	// Purpose : It clicks any element
	// Inputs : fXP of the element to click on.
	// Output : No output
		myD.findElement(By.xpath(fXP)).click();	
			
	}
	public void clickLogin(String fXP) throws InterruptedException {
		// Purpose : It clicks on LoginButton
		// Inputs : fXP of the element to click on.
		// Output : No output
	
		 myD.findElement(By.cssSelector(fXP)).click();
		// myD.wait(30);
		Thread.sleep(5000);
	}
	
	public void hitEnter(String fXP){
		// Purpose : Hits an enter over an element
		// Inputs :fXP of the element to hit enter on
		// Output : No output
			myD.findElement(By.xpath(fXP)).sendKeys(Keys.ENTER);
	}
	
	public String verifyText(String fXP, String fText){
		// Purpose : Verifies if a specific text is present on that element
		// Inputs : fXP of the element and the fText to verify
		// Output : Pass or a Fail
			String fTemp;
			fTemp = myD.findElement(By.linkText(fXP)).getText();
			System.out.println( "Text--- " +fTemp);
			System.out.println( "fText--- " +fText);
		//	fTemp = myD.findElement(By.xpath(fXP)).getText();
			if (fTemp.equals(fText)){
				return "Pass" ;
			} else {
				return "Fail" ;
			}
			
	}
	public String verifyUpCaseLetter(String fXP, String fText){
		// Purpose : Verifies uppercase letter
		// Inputs : fXP of the element and the fText to verify
		// Output : Pass or a Fail
			String fTemp;
			fTemp = myD.findElement(By.linkText(fXP)).getText();
			System.out.println( "Text--- " +fTemp);
			System.out.println( "fText--- " +fText);
		//	fTemp = myD.findElement(By.xpath(fXP)).getText();
		//	if (fTemp.equals(fText)){
		//		return "Pass" ;
		//	} else {
		//		return "Fail" ;
		//	}
			if(Character.isUpperCase(fTemp.charAt(0))==true)
			{
			    return "Pass";
			    }
			else {
			    return "Fail";
			}
	}
	public String verifyElement(String fXP, String fText){
		// Purpose : Verifies element present by css selector
		// Inputs : fXP of the element and the text to verify
		// Output : Pass or a Fail
			String fTemp;
		//	fTemp = myD.findElement(By.linkText(fXP)).getText();
		//	fTemp = myD.findElement(By.xpath(fXP)).getText();
			fTemp = myD.findElement(By.cssSelector(fXP)).getText();
			System.out.println("fTemp  --" + fTemp);
			if (fTemp.equals(fText))
			{
				return "Pass" ;
			} else {
				return "Fail" ;
			}
		}
	public void ConfirmYes() {
		// Purpose : confirms "Yes" to alert message
		// Inputs : None
		// Output : None
		Alert alt=myD.switchTo().alert();
		//alt.dismiss();
		alt.accept();
	}
	public String verifyNotText(String fXP, String fText){
		// Purpose : Verifies Text Not Present
		// Inputs : fXP of the element and the fText not present
		// Output : Pass or a Fail
			String fTemp;
			fTemp = myD.findElement(By.linkText(fXP)).getText();
		//	fTemp = myD.findElement(By.xpath(fXP)).getText();
			System.out.println("fTemp Check --" + fTemp);
			if (fTemp.isEmpty()){
				return "Pass" ;
			} else {
				return "Fail" ;
			}
			
	}
	public String verifyElementPresent(String fXP, String fText){
		// Purpose : Verifies if a specific text is present by xpath
		// Inputs : fXP of the element and the fText to verify
		// Output : Pass or a Fail
			String fTemp, Vsrc;
		//	fTemp = myD.findElement(By.linkText(fXP)).getText();
		//	fTemp = myD.findElement(By.xpath(fXP)).getAttribute(fText);
		//	Vsrc= "http://test.atomic77.in/TWAv1/1/wp-content/uploads/2016/04/HPIcon-.jpg";
		/*	System.out.println("Src " + fTemp);
			if (fTemp.equals(Vsrc)){
				return "Pass" ;
			} else {
				return "Fail" ;
			} */
			if (myD.findElement(By.xpath(fXP)).isDisplayed()) {
				//	if (fTemp.equals(Vsrc)){
						return "Pass" ;
					} else {
						return "Fail" ;
					}
	}
	public String verifyUIPresent(String fXP, String fText){
		// Purpose : Verifies element present by css selector  
		// Inputs : fXP of the element and the fText to verify
		// Output : Pass or a Fail
		//	String fTemp, Vsrc;
		//	fTemp = myD.findElement(By.linkText(fXP)).getText();
		//	fTemp = myD.findElement(By.xpath(fXP)).getAttribute(fText);
		//	Vsrc= "http://test.atomic77.in/TWAv1/1/wp-content/uploads/2016/04/HPIcon-16.jpg";
		//	System.out.println("Src " + fTemp);
		if (myD.findElement(By.cssSelector(fXP)).isDisplayed()) {
		//	if (fTemp.equals(Vsrc)){
				return "Pass" ;
			} else {
				return "Fail" ;
			}
			
	}
	public String verifyUIXPthPresent(String fXP, String fText){
		// Purpose : Verifies element present by Xpath  
		// Inputs : fXP of the element and the fText to verify
		// Output : Pass or a Fail
		//	String fTemp, Vsrc;
		//	fTemp = myD.findElement(By.linkText(fXP)).getText();
		//	fTemp = myD.findElement(By.xpath(fXP)).getAttribute(fText);
		//	Vsrc= "http://test.atomic77.in/TWAv1/1/wp-content/uploads/2016/04/HPIcon-16.jpg";
		//	System.out.println("Src " + fTemp);
	//	if (myD.findElement(By.cssSelector(fXP)).isDisplayed()) 
		if ( myD.findElement(By.xpath(fXP)).isDisplayed()){
		//	if (fTemp.equals(Vsrc)){
				return "Pass" ;
			} else {
				return "Fail" ;
			}
		}

		
	public void clickSubNewCourse(String fXP, String fText) throws IOException, InterruptedException{
		// Purpose : Click Add New COurse Button
		// Inputs : fXP of the element to click on.
		// Output : No output
		
			myD.findElement(By.xpath(fXP)).click();	
			myD.findElement(By.xpath(fXP)).clear();
	//		myD.findElement(By.xpath(fXP)).sendKeys(fText);
			Runtime.getRuntime().exec("C:/Users/Netrs DS/Documents/AutoItUpload/SLPFileUploadVOne.exe");
		//	myD.findElement(By.linkText(fXP)).click();
		//   myD.findElement(By.cssSelector(fXP)).click();
		//   myD.findElement(ByClassName.className(fXP)).click();
			Thread.sleep(10000);
	}
	public void clickBigNewCourseIcon(String fXP, String fText) throws IOException, InterruptedException{
		// Purpose : To Upload Icon 
		// Inputs : fXP of the element to click on.
		// Output : No output
		
			myD.findElement(By.xpath(fXP)).click();	
			myD.findElement(By.xpath(fXP)).clear();
	//		myD.findElement(By.xpath(fXP)).sendKeys(fText);
			Runtime.getRuntime().exec("C:/Users/Netrs DS/Documents/AutoItUpload/SLPFileUploadVTwo.exe");
		//	myD.findElement(By.linkText(fXP)).click();
		//   myD.findElement(By.cssSelector(fXP)).click();
		//   myD.findElement(ByClassName.className(fXP)).click();
			Thread.sleep(10000);
	}
}
