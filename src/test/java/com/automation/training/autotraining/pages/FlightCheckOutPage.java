package com.automation.training.autotraining.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FlightCheckOutPage extends BasePage {

	@FindBy(id="page-header")
	private WebElement pageHeader;
	
	@FindBy(className="your-price-summary")
	private WebElement priceSummaryTitle;
	
	@FindBy(css="h3.faceoff-module-title")
	private WebElement sendConfirmationHeader;
	
	@FindBy(id="complete")
	private WebElement completeBox;
	
	@FindBy(id="complete-booking")
	private WebElement completeBookingBtn;
	
	
	public FlightCheckOutPage(WebDriver pDriver) {
		super(pDriver);
		getWait().until(ExpectedConditions.elementToBeClickable(completeBookingBtn));
	}
	
	public String getPageHeader() {
		return pageHeader.getText();
	}
	
	public String getPriceSummaryTitle() {
		return priceSummaryTitle.getText();
	}
	
	public String getConfirmationHeader() {
		return sendConfirmationHeader.getText();
	}
	
	public String getCompleteBox() {
		return completeBox.findElement(By.cssSelector("h2.module-title")).getText();
	}
	
	public String getCompleteBtn() {
		return completeBookingBtn.findElement(By.className("button-text")).getText();
	}
	
}
