package com.FlipkartKiwiqaFramework.product;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.FlipkartKiwiqaFramework.Init.Common;
import com.FlipkartKiwiqaFramework.Init.SeleniumInit;

public class ProductIndex extends SeleniumInit {
	  
	@Test
	public void flipkart() throws InterruptedException {
		int step = 1;
		int numOfFailure = 0;

		logStep(step++, "Open the URL -> " + testUrl);
		productVerification = productIndexPage.mouseHoveronElectronics();
		logStep(step++, "Mouse hover on 'electronics'");
//		Common.Pause(1);

		productVerification = productIndexPage.clickonRealme();
		logStep(step++, "Click on 'Realme'");
		
		Common.Pause(2);
		log("Verify to only Realme products are displayed: -> ");
		if (productVerification.verifyProductName()) {
			logStatus(1, "Only Realme products are displayed");
		} else {
			logStatus(2, "Only Realme products are not displayed");
			numOfFailure++;
		}
		if (numOfFailure > 0) {
			Assert.assertTrue(false);
		}
		Common.Pause(2);

		productVerification = productIndexPage.clickonFilter();
		logStep(step++, "Select price range from dragger");
		logStep(step++, "Select '4 GB' checkbox");
		
		Common.Pause(2);
		log("Verify to only 4 GB RAM products are displayed: -> ");
		if (productVerification.verifyRamFilter()) {
			logStatus(1, "Only 4 GB RAM products are displayed");
		} else {
			logStatus(2, "Only 4 GB RAM products are not displayed");
			numOfFailure++;
		}
		if (numOfFailure > 0) {
			Assert.assertTrue(false);
		}
		Common.Pause(2);

		productVerification = productIndexPage.selectProduct();
		logStep(step++, "Click on First product");
//		Common.Pause(1);

		productVerification = productIndexPage.addToCart();
		logStep(step++, "Click on 'Add to Cart' button");
		Common.Pause(2);
		log("Verify to check cart is not empty: -> ");
		if (productVerification.verifyProductInCart()) {
			logStatus(1, "Cart is not empty");
		} else {
			logStatus(2, "Cart is empty");
			numOfFailure++;
		}
		if (numOfFailure > 0) {
			Assert.assertTrue(false);
		}
		Common.Pause(2);
	}

}
