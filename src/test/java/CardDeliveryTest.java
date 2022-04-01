import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
    private Faker faker;

    @BeforeEach
    void setupTest() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        faker = new Faker(new Locale("ru"));
        $x("//span[@data-test-id='date']//input[@type='tel']").doubleClick().sendKeys(Keys.chord(Keys.BACK_SPACE));
    }

    String dateByClient = DataGenerator.date(3);

    @Test
    void buildWorkTest() {
        $x("//span[@data-test-id='city']//input[@type='text']").val(DataGenerator.city());
        $x("//span[@data-test-id='date']//input[@type='tel']").val(dateByClient);
        $x("//span[@data-test-id='name']//input[@type='text']").val(DataGenerator.surNameAndFirstName());
        $x("//span[@data-test-id='phone']//input[@type='tel']").val(DataGenerator.phone());
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//div[@class='notification__title']").shouldHave(Condition.text("Успешно!"), Duration.ofSeconds(15));
        $x("//div[@class='notification__content']").shouldHave(Condition.text("Встреча успешно запланирована на " + dateByClient), Duration.ofSeconds(15));
        $x("//span[@class='button__text']").click();
        $x("//*[contains(text(),'У вас уже запланирована встреча на другую дату. Перепланировать?')]").should(visible, Duration.ofSeconds(15));
        $x("//span[@data-test-id='date']//input[@type='tel']").doubleClick().sendKeys(Keys.chord(Keys.BACK_SPACE));
        String newDateByClient = DataGenerator.date(4);
        $x("//span[@data-test-id='date']//input[@type='tel']").val(newDateByClient);
        $x("//*[contains(text(),'У вас уже запланирована встреча на другую дату. Перепланировать?')]//button").click();
        $x("//div[@class='notification__title']").shouldHave(Condition.text("Успешно"), Duration.ofSeconds(15));
        $x("//div[@class='notification__content']").shouldHave(Condition.text("Встреча успешно запланирована на " + newDateByClient), Duration.ofSeconds(15));
    }

    @Test
    void notCorrectPhoneNumberTest() {
        $x("//span[@data-test-id='city']//input[@type='text']").val(DataGenerator.city());
        $x("//span[@data-test-id='date']//input[@type='tel']").val(dateByClient);
        $x("//span[@data-test-id='name']//input[@type='text']").val(DataGenerator.surNameAndFirstName());
        $x("//span[@data-test-id='phone']//input[@type='tel']").val(DataGenerator.notCorrectPhone());
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//div[@class='notification__title']").shouldHave(Condition.text("Успешно!"), Duration.ofSeconds(15));
        $x("//div[@class='notification__content']").shouldHave(Condition.text("Встреча успешно запланирована на " + dateByClient), Duration.ofSeconds(15));
        $x("//span[@class='button__text']").click();
        $x("//*[contains(text(),'У вас уже запланирована встреча на другую дату. Перепланировать?')]").should(visible, Duration.ofSeconds(15));
        $x("//span[@data-test-id='date']//input[@type='tel']").doubleClick().sendKeys(Keys.chord(Keys.BACK_SPACE));
        String newDateByClient = DataGenerator.date(4);
        $x("//span[@data-test-id='date']//input[@type='tel']").val(newDateByClient);
        $x("//*[contains(text(),'У вас уже запланирована встреча на другую дату. Перепланировать?')]//button").click();
        $x("//div[@class='notification__title']").shouldHave(Condition.text("Успешно"), Duration.ofSeconds(15));
        $x("//div[@class='notification__content']").shouldHave(Condition.text("Встреча успешно запланирована на " + newDateByClient), Duration.ofSeconds(15));
    }

    @Test
    void notWorkingNameTest() {
        $x("//span[@data-test-id='city']//input[@type='text']").val(DataGenerator.city());
        $x("//span[@data-test-id='date']//input[@type='tel']").val(dateByClient);
        $x("//span[@data-test-id='name']//input[@type='text']").val(DataGenerator.notWorkingName());
        $x("//span[@data-test-id='phone']//input[@type='tel']").val(DataGenerator.phone());
        $x("//label[@data-test-id='agreement']").click();
        $x("//span[@class='button__text']").click();
        $x("//span[@data-test-id='name']").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
}