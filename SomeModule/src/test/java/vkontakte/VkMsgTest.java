package vkontakte;

import org.aeonbits.owner.ConfigFactory;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.assertj.core.api.Assertions.assertThat;
//import io.qameta.allure.*;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import static java.lang.String.format;

@RunWith(Parameterized.class)

public class VkMsgTest {
    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.get("https://vk.com/login");
    }

    @After
    public void exit() {
        driver.quit();
    }

    public VkMsgTest(int number) {
        this.number = number;
    }

    private WebDriver driver;
    private int number;
    Timestamp ts = new Timestamp(System.currentTimeMillis());
    private VkConfig cfg = ConfigFactory.create(VkConfig.class);

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{{1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10}};
        return Arrays.asList(data);
    }

    @Test
//    public void somesteps (){
//        loginSendLogout();
//        step2();
//    }
//
//    @Step("Название шага1")
    public void loginSendLogout() {
        //Блок авторизации
        WebElement loginField = driver.findElement(By.xpath("//input[@id=\"email\"]"));
        loginField.sendKeys(cfg.login());
        WebElement passwordField = driver.findElement(By.xpath("//input[@id=\"pass\"]"));
        passwordField.sendKeys(cfg.password());
        WebElement loginButton = driver.findElement(By.xpath("//div[@class=\"login_buttons_wrap\"]" +
                "//button[contains(@id, 'login_button')]"));
        loginButton.click();
//    }
//    @Step("shag 2")
//    public void step2(){
        //Блок поиска нужного юзера
        WebElement msgButton = driver.findElement(By.xpath("//li[contains(@id,'l_fr')]"));
        msgButton.click();
        WebElement searchField = driver.findElement(By.xpath("//input[@id=\"s_search\"]"));
        searchField.sendKeys(cfg.userName());

        // Блок отправки сообщения, клик "написать" -> пишем -> клик "отправить"
        (new WebDriverWait(driver, 3))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(format
                        ("//div[contains(@class, 'friends_user_row') and contains(., '%s')]" +
                                "//a[@class='friends_field_act']", cfg.userName()))));
        WebElement writeMsgButtom = driver.findElement(By.xpath(format
                ("//div[contains(@class, 'friends_user_row') and contains(., '%s')]" +
                        "//a[@class='friends_field_act']", cfg.userName())));
        writeMsgButtom.click();
        WebElement msgField = driver.findElement(By.xpath("//div[@id=\"mail_box_editable\"]"));
        msgField.sendKeys("test №" + " " + number + " " + ts);
        WebElement sendMsgButtom = driver.findElement(By.xpath("//button[@id=\"mail_box_send\"]"));
        sendMsgButtom.click();

        //Блок Проверки что сообщение отправилось
        driver.get("https://vk.com/im?media=&sel=" + cfg.userId()); //Грязный хак
        assertThat(driver.findElement(By.xpath(format
                ("//div[contains(@class, 'im-mess-stack')]//div[contains(@class, 'im-mess--text') " +
                        "and contains(., '%s')]", ts))).getText()).matches("test №" + " " + number + " " + ts);
        System.out.println("Ура тест пройден" + ";" + "Test completed, yeah");

        //Блок логаута
        (new WebDriverWait(driver, 3))
                .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id=\"box_layer_wrap\"]")));
        (new WebDriverWait(driver, 3))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id=\"top_profile_link\"]")));
        WebElement menuUser = driver.findElement(By.xpath("//a[@id=\"top_profile_link\"]"));
        menuUser.click();
        WebElement logoutButton = driver.findElement(By.xpath("//a[@id=\"top_logout_link\"]"));
        logoutButton.click();
        (new WebDriverWait(driver, 3)).until(ExpectedConditions.visibilityOfElementLocated(By
                .id("top_reg_link"))); //Проверяем, что действительно разлогинились
    }
}