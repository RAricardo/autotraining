package com.automation.training.autotraining.tests.suite1;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.automation.training.autotraining.pages.FlightCheckOutPage;
import com.automation.training.autotraining.pages.FlightInfoPage;
import com.automation.training.autotraining.pages.FlightSearchPage;
import com.automation.training.autotraining.pages.HotelInfoPage;
import com.automation.training.autotraining.pages.HotelSearchPage;
import com.automation.training.autotraining.pages.PackageInfoPage;
import com.automation.training.autotraining.pages.TravelHomePage;

public class Exercise2 {
	private MyDriver driver;
	private TravelHomePage homePage;
	private HotelSearchPage hotelPage;
	private HotelInfoPage hotelInfo; 
	private FlightSearchPage searchPage;
	private PackageInfoPage infoPage;
	private FlightCheckOutPage checkoutPage;
	
	@BeforeClass (groups = {"exercise2"})
	@Parameters({"browser"})
	public void beforeSuite(String browser) {
		driver = new MyDriver(browser);
		homePage = new TravelHomePage(driver.getDriver());
	}
	
	@Test (groups = {"exercise2"})
	/*
	 * Verifies Hotels + Flight is properly opened by checking contents of vacation packages options.
	 */
	public void verifyHotelsFlightsIsOpened() {
		homePage.switchToFlightAndHotels();
		ArrayList<String> testStrings = new ArrayList<String>();
		testStrings.add("Flight + Hotel");
		testStrings.add("Flight + Hotel + Car");
		testStrings.add("Flight + Car");
		testStrings.add("Hotel + Car");
		assertTrue(homePage.getFlightHotelsOption().containsAll(testStrings));
	}
	
	@Test (groups = {"exercise2"}, dependsOnMethods= {"verifyHotelsFlightsIsOpened"})
	/*
	 * Search for flights + hotels, then proceeds to verify result page.
	 */
	@Parameters({"from-location-2", "to-location-2","departure-date-2","returning-date-2","adult-num-2"})
	public void verifySearchFlightAndHotel(String from, String to, String departure, String returning, String adnum) {
		 hotelPage = homePage.searchFlightAndHotel(from, to, departure, returning, adnum);
		 hotelPage.hotelListLoadedWait();
		 assertEquals(hotelPage.getPageHeader(), "Start by choosing your hotel");
		 assertEquals(hotelPage.getSortByPriceTag(), "Price");
		 assertEquals(hotelPage.getHotelNamesCount(), hotelPage.hotelCount());
		 assertEquals(hotelPage.getHotelLocationCount(), hotelPage.hotelCount());
		 assertEquals(hotelPage.getHotelImageCount(), hotelPage.hotelCount());
	}
	
	@Test (groups = {"exercise2"}, dependsOnMethods= {"verifySearchFlightAndHotel"})
	/*
	 * Clicks on sort by price button, then verifies the hotel list is actually sorted.
	 */
	public void verifySortByPrice() {
		hotelPage.sortByPrice();
		ArrayList<Integer> prices = hotelPage.sortedPrices();
		ArrayList<Integer> copy = prices;
	    Collections.sort(copy);
	    assertTrue(copy.equals(prices));
	}
	
	@Test (groups = {"exercise2"}, dependsOnMethods= {"verifySortByPrice"})
	/*
	 * Internally passes previous hotel listing page to hotel info page constructor, proceeds to compare 
	 * parameters with new page elements.
	 */
	public void verifyThreeStarSelection() {
		hotelInfo = hotelPage.selectThreeStarHotel();
		assertTrue(hotelInfo.hotelNameEquals());
		assertTrue(hotelInfo.hotelPriceEquals());
		assertTrue(hotelInfo.hotelStarsEquals());
	}
	
	@Test (groups = {"exercise2"}, dependsOnMethods= {"verifyThreeStarSelection"})
	/*
	 * Chooses a room, departure and returning dates, adds a car, selects and runs validations on trip details page.
	 */
	@Parameters({"departure-option", "return-option"})
	public void verifyTripDetails(int departureOption, int returnOption) {
		searchPage = hotelInfo.chooseARoom();
		searchPage.flightListLoadedWait();
		infoPage = searchPage.selectFlightsPackage(departureOption, returnOption);
		infoPage.addCar();
		assertEquals("Mon, Oct 5 -to Sun, Oct 18 ", infoPage.getDepartureDates());
		assertEquals("Stay flexible with no change fees", infoPage.getStayFlexibleMessage());
		assertEquals("We recommend booking a flight with no change fees in case your plans change.",
				infoPage.getRecommendationMessage());
		assertEquals("Change flights", infoPage.getChangeFlightsBtn());
		assertEquals("Next: Final Details", infoPage.getFinalDetailsLabel());
		infoPage.pressFinalDetails();
	}
	
	@AfterClass (groups = {"exercise2"})
	public void dispose() {
		homePage.dispose();
		hotelPage.dispose();
		hotelInfo.dispose();
		searchPage.dispose();
		infoPage.dispose();
		checkoutPage.dispose();
	}
}
