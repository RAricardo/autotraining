package com.automation.training.autotraining.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HotelSearchPage extends BasePage {
	
	@FindBy(id="hotelResultTitle")
	private WebElement pageHeader;
	
	@FindBy(className="sort-options")
	private WebElement sortOptions;
	
	@FindBy(id="resultsContainer")
	private WebElement hotelList;
	
	public HotelSearchPage(WebDriver pDriver) {
		super(pDriver);
	}
	
	public String getPageHeader () {
		return pageHeader.findElement(By.tagName("h1")).getText();
	}
	
	private WebElement getSortByPriceBtn() {
		return sortOptions.findElements(By.cssSelector("button.btn-sort")).get(1);
	}
	
	public String getSortByPriceTag() {
		return getSortByPriceBtn().getText();
	}
	
	public void sortByPrice() {
		getSortByPriceBtn().click();
	}
	
	private List<WebElement> getHotelList(){
		return hotelList.findElements(By.tagName("article"));
	}
	
	public int hotelCount() {
		return getHotelList().size();
	}
	
	public int getHotelNamesCount() {
		return hotelList.findElements(By.className("hotelName")).size();
	}
	
	public int getHotelImageCount() {
		return hotelList.findElements(By.className("hotel-thumbnail")).size();
	}
	
	public int getHotelLocationCount() {
		return hotelList.findElements(By.cssSelector(".neighborhood.secondary ")).size();
	}

	public void hotelListLoadedWait() {
		getWait().until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(hotelList, (By.tagName("article"))));
	}
	
	public ArrayList<Integer> sortedPrices(){
		ArrayList<Integer> prices = new ArrayList<Integer>();
		hotelListLoadedWait();
		List<WebElement> webPrices = hotelList.findElements(By.className("actualPrice"));
		for (WebElement price: webPrices){
			String priceText = price.getText();
			String formatedPrice = priceText.replaceAll("[^0-9]","");
			prices.add(Integer.parseInt(formatedPrice));
		}
		return prices;
	}
	
	public HotelInfoPage selectThreeStarHotel() {
		closePopUp(2);
		List<WebElement> buttons = hotelList.findElements(By.cssSelector("a.flex-link"));
		try { 
			getWait().until(ExpectedConditions.stalenessOf(buttons.get(0)));
		} catch (Exception e) {
		}
		
		hotelList = getDriver().findElement(By.id("resultsContainer"));
		buttons = hotelList.findElements(By.cssSelector("a.flex-link"));
		List<WebElement> stars = hotelList.findElements(By.cssSelector(".reviewOverall span[aria-hidden='true']"));
		int index = 0;
		while(Double.parseDouble(stars.get(index).getText()
				.substring(0, stars.get(index).getText().indexOf("/")))<3){
			index++;
		}
		getWait().until(ExpectedConditions.elementToBeClickable(buttons.get(index)));
		String hotelName= hotelList.findElements(By.className("hotelName")).get(index).getText();
		String hotelPrice = hotelList.findElements(By.className("actualPrice")).get(index).getText();
		String hotelStars = hotelList.findElements(By.cssSelector("strong.star-rating .visuallyhidden")).get(index).getText();
		getActions().moveToElement(buttons.get(index)).click().perform();
		ArrayList<String> tabs = new ArrayList<String> (getDriver().getWindowHandles());
		getDriver().switchTo().window(tabs.get(1));
		return new HotelInfoPage(getDriver(), hotelName,hotelPrice,hotelStars);
	}
}
