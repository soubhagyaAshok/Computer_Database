package computerDatabase;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.Formatter;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class TC_04_ComputerDatabase {
	WebDriver driver;

	@BeforeTest
	public void launchApp() throws Exception {

		driver = new ChromeDriver();
		driver.get("https://computer-database.gatling.io/computers");
		driver.manage().window().maximize();

		System.out.println("launching app");
	}

	@Test
	public void navigatetoComputerDatabse() throws Exception {

		// Verify the title as Computers database
		String actualTitle1 = driver.getTitle();
		System.out.println("Application Title as Computer Database: " + actualTitle1);

		// Verify the page header is the same as the page title

		String pageHeader1 = "Computers database";

		if (actualTitle1.equals(pageHeader1))

			System.out.println("Test Passed: Verified the page header is same as page title");
		else
			System.out.println("Test Failed: Page header is not same as page Title");

		// User must see the filter by computer name text box

		String Actual = driver.findElement(By.xpath("//input[@placeholder='Filter by computer name...']"))
				.getAttribute("Placeholder");

		System.out.println("Element with text(): " + Actual);

		String Expected = "Filter by computer name";

		System.out.println(Actual.contains(Expected));

		// User able to see add a new computer button
		String actualName = driver.findElement(By.xpath("//a[@href='/computers/new']")).getText();
		String ExpectedName = "Add a new computer";

		if (actualName.equals(ExpectedName))
			System.out.println("Test Passed: Add a new computer button is dispalyed correctly");
		else
			System.out.println("Test Failed: Add a new computer button is not displayed correctly");

		// User able to see the filter by name button
		// Locating element with text()

		String actualFilterName = driver.findElement(By.xpath("//input[@value='Filter by name']"))
				.getAttribute("value");
		System.out.println(actualFilterName);

		String ExpectedFilterName = "Filter by name";

		if (actualFilterName.equals(ExpectedFilterName))
			System.out.println("Test Passed: Filter by name is dispalyed correctly");
		else
			System.out.println("Test Failed: Filter by name is not displayed correctly");

		System.out.println(actualFilterName.equals(ExpectedFilterName));

		// User able to see the table and the headers as follows-------Computer name,
		// Introduced, Discontinued, Company

		// String tablen = driver.findElement(By.xpath("//table[@class='computers zebra-striped']")).getText();
		// System.out.println(tablen);

		List<WebElement> Tableheadervalues = driver.findElements(By.xpath("/html/body/section/table/thead/tr"));
		Formatter fmt = new Formatter();

		// And iterate over them and get all the cells
		for (WebElement row : Tableheadervalues) {
			List<WebElement> cells = row.findElements(By.tagName("th"));

			// Print the contents of each cell
			for (WebElement cell : cells) {

				System.out.print(cell.getText() + "\t");
				// Or try below code
				// System.out.println(cell.getAttribute("innerHTML"));
			}

		}
		System.out.println();
		List<WebElement> TableData = driver.findElements(By.xpath("/html/body/section/table/tbody/tr"));
		for (WebElement row : TableData) {
			List<WebElement> cells = row.findElements(By.tagName("td"));

			// Print the contents of each cell
			for (WebElement cell : cells) {

				System.out.print(cell.getText() + "\t\t");
				// Or try below code
				// System.out.println(cell.getAttribute("innerHTML"));
			}
			System.out.println();
		}

		// driver.findElement(By.xpath("//a[@href='/computers?p=0&n=10&s=name&d=asc']"));
		// driver.findElement(By.xpath("//th[contains(@class,'col-introduced
		// header')]//a[1]"));
		// driver.findElements(By.cssSelector("//th[@class='col-discontinued header
		// ']//a[1]"));
		// driver.findElement(By.xpath("//th[@class='col-company header ']//a[1]"));

		// The user should see the pagination

		String ActualpageName = driver.findElement(By.xpath("//li[@class='current']//a[1]")).getText();
		System.out.println(ActualpageName);

		String ExpectedPageName = "Displaying 1 to 10 of 574";

		if (ActualpageName.equals(ExpectedPageName))
			System.out.println("Test Passed: Able to see pagination successfully");
		else
			System.out.println("Test Failed: Not able to see pagination correctly");

	}

	@Test
	// Add a new computer
	public void verifyAddaNewComputer() throws InterruptedException {

		driver.findElement(By.xpath("//a[@href='/computers/new']")).click();

		// Click on create this computer
		driver.findElement(By.xpath("//input[@class='btn primary']")).click();

		// User should see the red background on the computer name field
		String displaycolor = driver.findElement(By.xpath("//div[@class='clearfix error']")).getCssValue("color");
		System.out.println(displaycolor);
		String Expectedcolor = "rgba(64, 64, 64, 1)";

		if (displaycolor.equals(Expectedcolor))
			System.out.println("Test Passed: Red Background color is displayed correctly");
		else
			System.out.println("Test Failed: Red Background color is not dispalyed correctly");

		// Enter the computer name

		driver.findElement(By.xpath("//label[text()='Computer name']/following::input")).sendKeys("Lenovo1");
		driver.findElement(By.xpath("//label[text()='Introduced']/following::input")).sendKeys("2023-01-12");
		driver.findElement(By.xpath("(//div[@class='input']//input)[3]")).sendKeys("2023-02-12");

		// Select the company as Nokia
		WebElement companyList = driver.findElement(By.xpath("//div[@class='input']//select[1]"));
		// Select class

		Select sel = new Select(companyList);

		// System.out.println("is listbox accept multiple selection or not: " + sel);

		// Select value from drop down
		// visibleText

		Thread.sleep(1000);
		sel.selectByVisibleText("Nokia"); // Nokia

		// Submit the form
		driver.findElement(By.xpath("//input[@value='Create this computer']")).click();

		// Successful message should be displayed
		String Msgname = driver.findElement(By.xpath("//div[@class='alert-message warning']")).getText();
		System.out.println("Alert message: " + Msgname);

		// Search the created data

		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@type='search']")).sendKeys("Lenovo1");
		driver.findElement(By.xpath("//input[@value='Filter by name']")).click();

		String ErrMsgname = driver.findElement(By.xpath("(//section[@id='main']//div)[2]")).getText();

		System.out.println("Alert message: " + ErrMsgname);
	}

	@AfterTest
	public void closeApp() throws Exception {
		Thread.sleep(5000);
		driver.close();
	}

}