package Vkontakte;
import org.aeonbits.owner.ConfigFactory;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;


public class vkMsgTest {
    private static WebDriver driver;

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "c:/users/taskme/work/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://vk.com/login");}


    @Test
    public void userLogin() {
        vkConfig cfg = ConfigFactory.create(vkConfig.class); // конфиг

        WebElement loginField = driver.findElement(By.id("email"));
        loginField.sendKeys(cfg.login());
        WebElement passwordField = driver.findElement(By.id("pass"));
        passwordField.sendKeys(cfg.password());

        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"login_button\"]"));
        loginButton.click();

        try {

            TimeUnit.SECONDS.sleep(5);  //Ждем 5 секунд
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement msgButton = driver.findElement(By.xpath("//*[@id=\"l_fr\"]/a/span/span[2]"));
        msgButton.click();
        WebElement searchField = driver.findElement(By.id("s_search"));
        searchField.sendKeys("Анна Сергеевна");

        try {

            TimeUnit.SECONDS.sleep(5);  //Ждем 5 секунд
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }


        int i = 0;
        do {
            i++;
            WebElement writeMsgButtom = driver.findElement(By.xpath("//*[@id=\"friends_user_row363738874\"]/div[4]/a"));
            writeMsgButtom.click();

            WebElement msgField = driver.findElement(By.xpath("//*[@id=\"mail_box_editable\"]"));
            msgField.sendKeys("test"+" "+i);

            WebElement sendMsgButtom = driver.findElement(By.id("mail_box_send"));
            sendMsgButtom.click();
            try {

                TimeUnit.SECONDS.sleep(3);  //Ждем 3 секунды
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (i<10);




    }
    @AfterClass
    public static void tearDown() {
        WebElement menuUser = driver.findElement(By.xpath("//*[@id=\"top_profile_link\"]/img"));
        menuUser.click();
        WebElement logoutButton = driver.findElement(By.id("top_logout_link"));
        logoutButton.click();
        try {

            TimeUnit.SECONDS.sleep(3);  //wait 3 second(s)
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }
}
