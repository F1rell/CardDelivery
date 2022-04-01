import com.github.javafaker.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {

    public static String city() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.options().option("Благовещенск", "Архангельск", "Астрахань", "Белгород", "Брянск", "Владимир", "Ульяновск", "Челябинск", "Ярославль");
    }

    public static String date(int plusDay) {
        return LocalDate.now().plusDays(plusDay).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String surNameAndFirstName() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String phone() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.phoneNumber().phoneNumber();
    }

    public static String notCorrectPhone() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.options().option("123");
    }

    public static String notWorkingName() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.options().option("Свиридова") + " " + faker.options().option("Алёна");
    }
}