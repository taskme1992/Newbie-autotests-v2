package scentbird;

import org.junit.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by Lsi!p on 31.10.2018.
 */


public class giftPage {
    private WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.get("https://www.scentbird.com/gift");
    }

    @Test
    public void allSteps() {
        sectionOne();
        sectionTwo();
        sectionThree();
        sectionFour();
        sectionFive();
    }

    public void sectionOne() {
        //TODO assert about background image

        List<WebElement> arrayOfSections = driver.findElements(By.xpath("//div[@class='_1FsAy0']//section"));
        assertThat(arrayOfSections.size()).isEqualTo(5);

        String alignForSectionOne = driver.findElement(By.xpath("//section[@class='_2P_jdo _2tOTFD oDc88G']")).getCssValue("text-align");
        assertThat(alignForSectionOne).isEqualTo("center");

        WebElement headerTextElement = driver.findElement(By.xpath("//section[@class='_2P_jdo _2tOTFD oDc88G']//h1[@class='_1zpZNQ']"));
        String headerText = headerTextElement.getText();
        assertThat(headerText).isEqualTo("GIVE THE GIFT OF THE SCENT");


        WebElement textTwoElement = driver.findElement(By.xpath("//section[@class='_2P_jdo _2tOTFD oDc88G']//div[@class='_2Yom1G']"));
        String textTwoS1 = textTwoElement.getText();
        assertThat(textTwoS1).isEqualTo("A Scentbird subscription is the gift that keeps on giving! For her or for him");

        WebElement buyGiftButtonElement = driver.findElement(By.xpath("//section[@class='_2P_jdo _2tOTFD oDc88G']//button[@id='buyGiftNow']"));
        String textBuyGiftButton = buyGiftButtonElement.getText();
        String roleBuyGiftButton = buyGiftButtonElement.getAttribute("role");
        assertThat(new String[]{textBuyGiftButton, roleBuyGiftButton}).containsExactly("BUY GIFT NOW", "button");
    }

    public void sectionTwo() {
        WebElement textOneElementS2 = driver.findElement(By.xpath("//section[@class='_2tOTFD']//h3[@class='_1Jw7Km']"));
        String textOneS2 = textOneElementS2.getText();
        assertThat(textOneS2).isEqualTo("The Perfect Gift");

        WebElement textTwoElementS2 = driver.findElement(By.xpath("//section[@class='_2tOTFD']//div[@class='_1EyPJi']"));
        String textTwoS2 = textTwoElementS2.getText();
        assertThat(textTwoS2).isEqualTo("No guess work, they choose the perfume or cologne they want");

        List<WebElement> arrayOfElementsS2 = driver.findElements(By.xpath("//section[@class='_2tOTFD']//div[@class='_25mxUQ']"));
        int i = 0;
        String[] test = new String[arrayOfElementsS2.size()];
        for (WebElement element : arrayOfElementsS2) {
            test[i] = element.getText();
            i++;
        }
        String[] test2 = {"GENEROUS SUPPLY OF FRAGRANCE (120 SPRAYS MONTHLY)", "3-, 6-, AND 12-MONTH OPTIONS AVAILABLE",
                "450 PERFUMES AND 100+ COLOGNES IN OUR COLLECTION", "STARTING AT JUST $44"};
        Assert.assertArrayEquals(test2, test);

        WebElement pictureS2 = driver.findElement(By.xpath("//section[@class='_2tOTFD']//picture[@class='_1Ow_N5 _2yAwtq M4lUUj']//img[@class='image']"));
        String roleForElementS2 = pictureS2.getAttribute("role");
        String linkForElementS3 = pictureS2.getAttribute("src");
        //TODO Assert For image link
        assertThat(roleForElementS2).isEqualTo("pictureImage");
    }

    public void sectionThree() {
        WebElement textOneElementS3 = driver.findElement(By.xpath("//section[@id='selectGender']//div[@class='_1Hf1sM']"));
        String textOneS3 = textOneElementS3.getText();
        assertThat(textOneS3).isEqualTo("Who is this gift for?");

        WebElement pictureHeS3 = driver.findElement(By.xpath("//section[@id='selectGender']//div[@class='_2EQuDQ qIgTCJ']//img[@class='image']"));
        String roleForElementOneS3 = pictureHeS3.getAttribute("role");
        String linkForElementOneS3 = pictureHeS3.getAttribute("src");
        //TODO Assert For image link
        assertThat(roleForElementOneS3).isEqualTo("pictureImage");

        WebElement pictureSheS3 = driver.findElement(By.xpath("//section[@id='selectGender']//div[@class='_2EQuDQ _1EaBly']//img[@class='image']"));
        String roleForElementTwoS3 = pictureSheS3.getAttribute("role");
        String linkForElementTwoS3 = pictureSheS3.getAttribute("src");
        //TODO Assert For image link
        assertThat(roleForElementTwoS3).isEqualTo("pictureImage");

        WebElement buttonHe = driver.findElement(By.xpath("//section[@id='selectGender']//div[@class='_2EQuDQ qIgTCJ']//button[@id='buyForHim']"));
        String buttonHeText = buttonHe.getAttribute("innerText");
        assertThat(buttonHeText).isEqualTo("BUY THE GIFT FOR HIM");

        WebElement buttonShe = driver.findElement(By.xpath("//section[@id='selectGender']//div[@class='_2EQuDQ _1EaBly']//button[@id='buyForHer']"));
        String buttonSheText = buttonShe.getAttribute("innerText");
        assertThat(buttonSheText).isEqualTo("BUY THE GIFT FOR HER");
        //TODO need to investigate  possibility to check animation of Buttons
    }

    public void sectionFour() {
        //TODO Get all data from main classes
        //TODO assert for arrays
    }

    public void sectionFive() {
        WebElement textElementS5 = driver.findElement(By.xpath("//section[@class='UFMvM0']//h3[@class='_3ODo3i']"));
        String textS5 = textElementS5.getText();
        assertThat(textS5).isEqualTo("No guess work, they choose the perfume or cologne they want");

        WebElement buyGiftButtonElementS5 = driver.findElement(By.xpath("//section[@class='UFMvM0']//button[@class='_3xh483 _2nQ2pA _3ltY_c _3BJw4m _2VuzPJ _17YI6K']"));
        String textBuyGiftButtonS5 = buyGiftButtonElementS5.getText();
        String roleBuyGiftButtonS5 = buyGiftButtonElementS5.getAttribute("role");
        assertThat(new String[]{textBuyGiftButtonS5, roleBuyGiftButtonS5}).containsExactly("BUY GIFT NOW", "button");

        WebElement pictureSheS5 = driver.findElement(By.xpath("//section[@class='UFMvM0']//img[@class='image']"));
        String roleForElementTwoS5 = pictureSheS5.getAttribute("role");
        String linkForElementTwoS5 = pictureSheS5.getAttribute("src");
        //TODO Assert For image link
        assertThat(roleForElementTwoS5).isEqualTo("pictureImage");
    }

    @After
    public void exit() {
        driver.quit();
    }

}
