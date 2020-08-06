package com.automation.training.autotraining.pages;

import java.util.Iterator;
import java.util.Set;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.TimeoutException;

public abstract class BasePage {
	private  WebDriver driver;
	private WebDriverWait wait;
	private Actions actions;
	
	public BasePage(WebDriver pDriver) {
		PageFactory.initElements(pDriver, this);
		driver = pDriver;
		wait = new WebDriverWait(driver,20);
		actions = new Actions(getDriver());
	}
	
	protected WebDriver getDriver() {
		return driver;
	}
	
	protected WebDriverWait getWait() {
		return wait;
	}
	
	protected Actions getActions() {
		return actions;
	}
	
	public String pageTitle() {
		return driver.getTitle();
	}
	
	public void dispose() {
		if (driver != null) {
			driver.quit();
		}
	}
	
	protected void closePopUp(int number) {
		//Close PopUp and wait for results
		String firstTab = driver.getWindowHandle();
		boolean flag = waitForNewWindow(number);
		if (flag) {
			String subWindowHandler="";
			Set<String> handles = driver.getWindowHandles();
			Iterator<String> iterator = handles.iterator(); 
			while (iterator.hasNext()){ subWindowHandler = iterator.next(); } 
			driver.switchTo().window(subWindowHandler);
			driver.close();
			driver.switchTo().window(firstTab);
		}
	}
	
	private boolean waitForNewWindow( int number){
		try {
			getWait().until(ExpectedConditions.numberOfWindowsToBe(number));
		} catch (TimeoutException e) {
			return false;
		}
        return true;
    }
}
