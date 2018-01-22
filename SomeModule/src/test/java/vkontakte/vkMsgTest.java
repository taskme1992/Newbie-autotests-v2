package vkontakte;

import org.aeonbits.owner.ConfigFactory;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;


@RunWith(Parameterized.class)

public class vkMsgTest {
    private static WebDriver driver;
    private int number;

    public vkMsgTest(int number) {
        this.number = number;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{{1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10}};
        return Arrays.asList(data);
    }

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "c:/users/taskme/work/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://vk.com/login");
    }

    @Test
    public void userLogin() {
        vkConfig cfg = ConfigFactory.create(vkConfig.class); // конфиг

        //Блок авторизации
        WebElement loginField = driver.findElement(By.id("email"));
        loginField.sendKeys(cfg.login());
        WebElement passwordField = driver.findElement(By.id("pass"));
        passwordField.sendKeys(cfg.password());
        WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"login_button\"]"));
        loginButton.click();
        try {

            TimeUnit.SECONDS.sleep(5);  //wait 5 second(s)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Блок поиска нужного человека
        WebElement msgButton = driver.findElement(By.xpath("//*[@id=\"l_fr\"]/a/span/span[2]"));
        msgButton.click();
        WebElement searchField = driver.findElement(By.id("s_search"));
        searchField.sendKeys("Анна Сергеевна");
        try {

            TimeUnit.SECONDS.sleep(2);  //wait 2 second(s)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Блок отправки сообщения, клик на "написать" +текс + "отправить"
        WebElement writeMsgButtom = driver.findElement(By.xpath("//*[@id=\"friends_user_row363738874\"]/div[4]/a"));
        writeMsgButtom.click();
        WebElement msgField = driver.findElement(By.xpath("//*[@id=\"mail_box_editable\"]"));
        msgField.sendKeys("test" + " " + number);
        WebElement sendMsgButtom = driver.findElement(By.id("mail_box_send"));
        sendMsgButtom.click();
        try {

            TimeUnit.SECONDS.sleep(2);  //wait 2 second(s)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        WebElement menuUser = driver.findElement(By.xpath("//*[@id=\"top_profile_link\"]/img"));
        menuUser.click();
        WebElement logoutButton = driver.findElement(By.id("top_logout_link"));
        logoutButton.click();
        driver.quit();
    }
}