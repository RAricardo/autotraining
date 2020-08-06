package com.automation.training.autotraining.pages;

import java.util.List;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HotelInfoPage extends BasePage {

	@FindBy(id="hotel-name")
	private WebElement hotelName;
	
	@FindBy(css=".price.link-to-rooms")
	private WebElement hotelPrice;
	
	@FindBy(css="strong.star-rating .visuallyhidden")
	private WebElement hotelStars;
	
	@FindBy(id="rooms-and-rates")
	private WebElement roomListing;
	
	@FindBy(className="modal-header")
	private WebElement modal;
	
	private String name;
	private String price;
	private String stars;
	
	public HotelInfoPage(WebDriver pDriver, String name, String price, String stars) {
		super(pDriver);
		
		this.name = name;
		this.price = price;
		this.stars = stars;
	}
	
	public boolean hotelNameEquals() {
		getWait().until(ExpectedConditions.visibilityOf(hotelName));
		return hotelName.getText().equals(name);
	}
	
	public boolean hotelPriceEquals() {
		getWait().until(ExpectedConditions.visibilityOf(hotelPrice));
		return hotelPrice.getText().equals(price);
	}
	
	public boolean hotelStarsEquals() {
		getWait().until(ExpectedConditions.visibilityOf(hotelStars));
		return hotelStars.getText().equals(stars);
	}
	
	public FlightSearchPage chooseARoom() {
		List<WebElement> rooms;
		if(!roomListing.findElements(By.cssSelector("a[data-control='modal']")).isEmpty()) {
			rooms = roomListing.findElements(By.cssSelector("a[data-control='modal']"));
			rooms.get(0).click();
			System.out.println("asdas");
		} else if (!roomListing.findElements(By.cssSelector(".room-price-book-button-wrapper a[role='button']")).isEmpty()) {
			rooms = roomListing.findElements(By.cssSelector(".room-price-book-button-wrapper a[role='button']"));
			rooms.get(0).click();
		}
		
		if(!getDriver().findElements(By.className("non-refundable")).isEmpty()) {
			WebElement modal = getDriver().findElement(By.id("covid-alert-refundability-0"));
			modal.findElement(By.cssSelector("a[data-track-page='HOT.PHIS.NonRefundable.Continue']")).click();
		}
		return new FlightSearchPage(getDriver());
	}

}
