package com.automation.training.autotraining.pages;

import java.util.Iterator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeDriver;

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
	
	protected void closePopUps() {
		Iterator<String> handles = getDriver().getWindowHandles().iterator();
		String current = handles.next();
		getDriver().switchTo().window(handles.next());
		getDriver().close();
		getDriver().switchTo().window(current);
	}
	
	public String pageTitle() {
		return driver.getTitle();
	}
	
	public void dispose() {
		if (driver != null) {
			driver.quit();
		}
	}
}
