package com.FlipkartKiwiqaFramework.product;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.FlipkartKiwiqaFramework.Init.AbstractPage;
import com.FlipkartKiwiqaFramework.Init.Common;


public class ProductVerification extends AbstractPage{

	public ProductVerification(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath=".//div[@class='_3ycxrs _2Rwa71']")
	WebElement productDiv;
	public boolean verifyProductInCart()
	{
//		WebDriverWait wait = new WebDriverWait(driver, 30);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[@class='_3ycxrs _2Rwa71']")));
//		Common.pause(1);
//		Common.clickableElement(productDiv, driver);
		if(productDiv.isDisplayed())
			return true;
		else
			return false;
	}
	
	public boolean verifyProductName()
	{
		String product_name;
		boolean value=true;
		List<WebElement> list_of_products = driver.findElements(By.xpath("//div[@class='_3wU53n']"));
		for(int i=0;i<list_of_products.size();i++) {
			product_name = list_of_products.get(i).getText();
			
			if(!product_name.contains("Realme"))
			{
				value=false;
				break;
			}
		}
		return value;
	}
	
	public boolean verifyRamFilter()
	{
		String Ram = "4 GB RAM";
		String product_name;
		boolean value=true;
		List<WebElement> list_of_products = driver.findElements(By.xpath(".//li[@class='tVe95H'][contains(text(),'"+Ram+"')]"));
		for(int i=0;i<list_of_products.size();i++) {
			product_name = list_of_products.get(i).getText();
			
			if(!product_name.contains("4 GB RAM"))
			{
				value=false;
				break;
			}
		}
		return value;
	}
	
}
