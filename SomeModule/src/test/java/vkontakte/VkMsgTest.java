package vkontakte;

import io.qameta.allure.junit4.DisplayName;
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

import io.qameta.allure.*;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

@RunWith(Parameterized.class)

public class VkMsgTest {
    private WebDriver driver;
    private int number;

    public VkMsgTest(int number) {
        this.number = number;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{{1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10}};
        return Arrays.asList(data);

    }

    Timestamp ts = new Timestamp(System.currentTimeMillis());
    private VkConfig cfg = ConfigFactory.create(VkConfig.class);

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.get("https://vk.com/login");
    }

    @Description("Тест отправки сообщения ВК")
    @DisplayName("Отправка сообщения ВК указанному юзеру")
    @Test
    public void allSteps() {
        login();
        searchUser();
        sendMsg();
        checkMsg();
        logOut();
    }

    @Step("Авторизация")
    public void login() {
        //Блок авторизации
        WebElement loginField = driver.findElement(By.xpath("//input[@id=\"email\"]"));
        loginField.sendKeys(cfg.login());
        WebElement passwordField = driver.findElement(By.xpath("//input[@id=\"pass\"]"));
        passwordField.sendKeys(cfg.password());
        WebElement loginButton = driver.findElement(By.xpath("//div[@class=\"login_buttons_wrap\"]" +
                "//button[contains(@id, 'login_button')]"));
        loginButton.click();
    }

    @Step("Поиск юзера")
    public void searchUser() {
        //Блок поиска нужного юзера
        WebElement msgButton = driver.findElement(By.xpath("//li[contains(@id,'l_fr')]"));
        msgButton.click();
        WebElement searchField = driver.findElement(By.xpath("//input[@id=\"s_search\"]"));
        searchField.sendKeys(cfg.userName());
    }

    @Step("Отправка сообщения")
    public void sendMsg() {
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
    }

    @Step("Проверка что сообщение отправилось")
    public void checkMsg() {
        //Блок Проверки что сообщение отправилось
        driver.get("https://vk.com/im?media=&sel=" + cfg.userId()); //Грязный хак
        assertThat(driver.findElement(By.xpath(format
                ("//div[contains(@class, 'im-mess-stack')]//div[contains(@class, 'im-mess--text') " +
                        "and contains(., '%s')]", ts))).getText()).matches("test №" + " " + number + " " + ts);
        System.out.println("Test completed, yeah");
    }

    @Step("Выход")
    public void logOut() {
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

    @After
    public void exit() {
        driver.quit();
    }
}