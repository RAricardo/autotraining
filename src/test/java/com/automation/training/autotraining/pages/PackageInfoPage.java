package com.automation.training.autotraining.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PackageInfoPage extends BasePage {

	@FindBy(id="dxGroundTransportationModule")
	WebElement groundTransportationModule;
	
	@FindBy(id="departure-dates")
	WebElement departureDates;
	
	@FindBy(id="flex-policy-message")
	WebElement policyMessage;
	
	@FindBy(id="changeFlights")
	WebElement changeFlightBtn;
	
	@FindBy(id="bottom-button")
	WebElement finalDetails;
	
	public PackageInfoPage(WebDriver pDriver) {
		super(pDriver);
	}
	
	public void addCar() {
		groundTransportationModule.findElements(By.tagName("button")).get(0).click();
	}
	
	public String getDepartureDates() {
		return departureDates.getText();
	}
	
	public String getStayFlexibleMessage() {
		return policyMessage.findElement(By.className("title")).getText();
	}
	
	public String getRecommendationMessage() {
		return policyMessage.findElement(By.className("message")).getText();
	}
	
	public String getChangeFlightsBtn() {
		return changeFlightBtn.getText();
	}
	
	private WebElement getFinalButton() {
		return finalDetails.findElement(By.tagName("button"));
	}
	
	public String getFinalDetailsLabel() {
		return getFinalButton().getText();
	}
	
	public void pressFinalDetails() {
		getFinalButton().click();
	}
}
