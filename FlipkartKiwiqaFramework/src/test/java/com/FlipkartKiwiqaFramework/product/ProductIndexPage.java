package com.FlipkartKiwiqaFramework.product;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.FlipkartKiwiqaFramework.Init.AbstractPage;
import com.FlipkartKiwiqaFramework.Init.Common;


public class ProductIndexPage extends AbstractPage{
	

	public ProductIndexPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
//	@FindBy(xpath=".//span[@class='_1QZ6fC _3Lgyp8'][text()='Electronics']")
//	WebElement electronics;
	@FindBy(xpath=".//button[@class='_2AkmmA _29YdH8']")
	WebElement closeLoginPopup;
	@FindBy(xpath=".//span[@class='_1QZ6fC _3Lgyp8'][text()='Electronics']")
	WebElement electronics;
//	String VisibleText = "Electronics";
//	WebElement electronics = driver.findElement(By.xpath(".//span[@class='_1QZ6fC _3Lgyp8'][text()='"+VisibleText+"']"));
	public ProductVerification mouseHoveronElectronics()
	{
//		Common.clickableElement(closeLoginPopup, driver);
		closeLoginPopup.click();
		Actions action = new Actions(driver);
		action.moveToElement(electronics).perform();
		return new ProductVerification(driver);
	}
	
	@FindBy(xpath=".//a[@title='Realme']")
	WebElement realme6;
//	String Company = "Realme";
//	WebElement realme6 = driver.findElement(By.xpath(".//a[@title='"+Company+"']"));
	public ProductVerification clickonRealme()
	{
		Common.clickableElement(realme6, driver);
		realme6.click();
		return new ProductVerification(driver);
	}
	
	@FindBy(xpath=".//div[@class='_3G9WVX oVjMho']/div[@class='_3aQU3C']")
	WebElement sourceLocator;
	@FindBy(xpath=".//div[@class='_4IiNRh _2mtkou'][@title='4 GB']")
	WebElement gb4Checkbox;
	
	public ProductVerification clickonFilter()
	{
		Actions action = new Actions(driver);
		action.dragAndDropBy(sourceLocator, 80, 0).build().perform();
//		Common.Pause(1);
		Common.clickableElement(gb4Checkbox, driver);
		gb4Checkbox.click();
//		Common.Pause(1);
		return new ProductVerification(driver);
	}
	
//	@FindBy(xpath=".//div[@class='_3wU53n'][text()='Realme 1 (Diamond Red, 64 GB)']")
//	WebElement clickOnProductimg;
//	public ProductVerification selectProduct()
//	{
//		WebDriverWait wait = new WebDriverWait(driver, 30);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[@class='_3wU53n'][text()='Realme 1 (Diamond Red, 64 GB)']")));
//		clickOnProductimg.click();
//		return new ProductVerification(driver);
//	}
	
	
	public ProductVerification selectProduct()
	{
		List<WebElement> list_of_products = driver.findElements(By.xpath("//div[@class='_3wU53n']"));
		List<WebElement> list_of_products_price = driver.findElements(By.xpath("//div[@class='_1vC4OE _2rQ-NK']"));
		
		String product_name;
		String product_price;
		int int_product_price;
		HashMap<Integer, String> map_final_products = new HashMap<Integer, String>();
		for (int i = 0; i < list_of_products.size(); i++) {
			product_name = list_of_products.get(i).getText();
			product_price = list_of_products_price.get(i).getText();
			product_price = product_price.replaceAll("[^0-9]", "");
			int_product_price = Integer.parseInt(product_price);
			map_final_products.put(int_product_price, product_name);
		}
		Set<Integer> allkeys = map_final_products.keySet();
		ArrayList<Integer> array_list_values_product_prices = new ArrayList<Integer>(allkeys);
		Collections.sort(array_list_values_product_prices);
		int high_price = array_list_values_product_prices.get(array_list_values_product_prices.size() - 1);

		String w1 = map_final_products.get(high_price);
		String product1;
		for (int i = 0; i < list_of_products.size(); i++) {
			product1 = list_of_products.get(i).getText();
			if (product1.equals(w1)) {
				driver.findElement(By.partialLinkText(w1)).click();
				Common.pause(3);
			}
		}
		return new ProductVerification(driver);
	}
	
	@FindBy(xpath=".//button[@class='_2AkmmA _2Npkh4 _2MWPVK']")
	WebElement productAddtoCart;
	public ProductVerification addToCart()
	{
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		Common.clickableElement(productAddtoCart, driver);
		productAddtoCart.click();
		return new ProductVerification(driver);
	}
}
