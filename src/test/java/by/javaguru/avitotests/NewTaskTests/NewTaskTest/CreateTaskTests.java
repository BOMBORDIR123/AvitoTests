package by.javaguru.avitotests.NewTaskTests.NewTaskTest;

import by.javaguru.avitotests.NewTaskTests.Pages.CreateTaskPage;
import by.javaguru.avitotests.annotations.video.RecordVideo;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * Тесты на проверку создания задачи:
 * позитивные и негативные сценарии заполнения формы создания задачи.
 */
@Epic("Task Management")
@Feature("Create Task")
@RecordVideo
public class CreateTaskTests extends BaseTest {

    Random rand = new Random();
    private final int newRand = rand.nextInt(999);

    // Позитивные тесты

    /**
     * Проверка создания задачи со всеми корректно заполненными данными.
     */
    @Test
    @Story("Создание задачи")
    @Description("Создание задачи со всеми корректно заполненными полями.")
    @Severity(SeverityLevel.CRITICAL)
    void createTaskWithAllData() {
        CreateTaskPage createTaskPage = new CreateTaskPage(getPage()).login();

        String taskName = "Задача со всеми параметрами NEW" + newRand;

        Allure.step("Заполняем форму задачи всеми валидными параметрами");
        createTaskPage.fillTaskForm(
                taskName,
                taskName,
                "Оптимизация производительности",
                "Low",
                "Илья Романов"
        );

        Allure.step("Нажимаем кнопку создания и проверяем успешное создание");
        createTaskPage.submitAndCheck(taskName);
    }

    /**
     * Создание задачи без описания — допустимый позитивный сценарий.
     */
    @Test
    @Story("Создание задачи")
    @Description("Создание задачи с пустым описанием.")
    @Severity(SeverityLevel.NORMAL)
    void createTaskWithValidData() {
        CreateTaskPage createTaskPage = new CreateTaskPage(getPage()).login();

        String taskName = "Задача без описания New" + newRand;

        Allure.step("Создаём задачу с пустым описанием");
        createTaskPage.fillTaskForm(
                taskName,
                "",
                "Оптимизация производительности",
                "Low",
                "Илья Романов"
        );

        Allure.step("Проверяем, что задача успешно создана");
        createTaskPage.submitAndCheck(taskName);
    }

    /**
     * Создание задачи с минимально возможной длиной названия.
     */
    @Test
    @Story("Создание задачи")
    @Description("Проверка создания задачи с минимальной длиной названия.")
    @Severity(SeverityLevel.NORMAL)
    void createTaskWithMinTitleLength() {
        CreateTaskPage createTaskPage = new CreateTaskPage(getPage()).login();

        String taskName = "♂" + newRand;

        Allure.step("Заполняем форму задачи минимально коротким заголовком");
        createTaskPage.fillTaskForm(
                taskName,
                taskName,
                "Оптимизация производительности",
                "Low",
                "Илья Романов"
        );

        createTaskPage.submitAndCheck(taskName);
    }

    /**
     * Создание задачи с максимально длинным корректным названием.
     */
    @Test
    @Story("Создание задачи")
    @Description("Проверка создания задачи с максимально длинным названием.")
    @Severity(SeverityLevel.MINOR)
    void createTaskWithMaxTitleLength() {
        CreateTaskPage createTaskPage = new CreateTaskPage(getPage()).login();

        String taskName = "Очень длинное название из "
                + "111111111111111111111111111111111111111111111111111111"
                + newRand;

        Allure.step("Создаём задачу с максимальной длиной названия");
        createTaskPage.fillTaskForm(
                taskName,
                taskName,
                "Оптимизация производительности",
                "Low",
                "Илья Романов"
        );

        createTaskPage.submitAndCheck(taskName);
    }

    /**
     * Создание задачи, содержащей специальные символы.
     */
    @Test
    @Story("Создание задачи")
    @Description("Проверка создания задачи со спецсимволами в заголовке.")
    @Severity(SeverityLevel.NORMAL)
    void createTaskWithSpecialCharacters() {
        CreateTaskPage createTaskPage = new CreateTaskPage(getPage()).login();

        String taskName = "Задача c спец символами (♂, ♀, ♪, ♫, ☼, ►, ◄) New" + newRand;

        Allure.step("Создаём задачу со специальными символами в заголовке");
        createTaskPage.fillTaskForm(
                taskName,
                taskName,
                "Оптимизация производительности",
                "Low",
                "Илья Романов"
        );

        createTaskPage.submitAndCheck(taskName);
    }

    // Негативные тесты

    /**
     * Проверка, что задача не создается без заголовка.
     */
    @Test
    @Story("Валидация формы")
    @Description("Проверка запрета создания задачи без названия.")
    @Severity(SeverityLevel.CRITICAL)
    void notCreateTaskWithoutTitle() {
        CreateTaskPage createTaskPage = new CreateTaskPage(getPage()).login();

        Allure.step("Пробуем создать задачу без заголовка");
        createTaskPage.fillTaskForm(
                "",
                "Задача без названия " + newRand,
                "Оптимизация производительности",
                "Low",
                "Илья Романов"
        );

        createTaskPage.assertCreateButtonDisabled();
    }

    /**
     * Проверка, что задача не создается без выбора проекта.
     */
    @Test
    @Story("Валидация формы")
    @Description("Проверка запрета создания задачи без проекта.")
    @Severity(SeverityLevel.CRITICAL)
    void notCreateTaskWithoutProject() {
        CreateTaskPage createTaskPage = new CreateTaskPage(getPage()).login();

        String taskName = "Задача без проекта " + newRand;

        Allure.step("Пробуем создать задачу без выбора проекта");
        createTaskPage.fillTaskForm(
                taskName,
                taskName,
                "",
                "Low",
                "Илья Романов"
        );

        createTaskPage.assertCreateButtonDisabled();
    }

    /**
     * Проверка, что задача не создается без выбора приоритета.
     */
    @Test
    @Story("Валидация формы")
    @Description("Проверка запрета создания задачи без приоритета.")
    @Severity(SeverityLevel.CRITICAL)
    void notCreateTaskWithoutPriority() {
        CreateTaskPage createTaskPage = new CreateTaskPage(getPage()).login();

        String taskName = "Задача без приоритета " + newRand;

        Allure.step("Пробуем создать задачу без выбора приоритета");
        createTaskPage.fillTaskForm(
                taskName,
                taskName,
                "Оптимизация производительности",
                "",
                "Илья Романов"
        );

        createTaskPage.assertCreateButtonDisabled();
    }

    /**
     * Проверка, что задача не создается без исполнителя.
     */
    @Test
    @Story("Валидация формы")
    @Description("Проверка запрета создания задачи без исполнителя.")
    @Severity(SeverityLevel.CRITICAL)
    void notCreateTaskWithoutAssignee() {
        CreateTaskPage createTaskPage = new CreateTaskPage(getPage()).login();

        String taskName = "Задача без исполнителя " + newRand;

        Allure.step("Пробуем создать задачу без выбора исполнителя");
        createTaskPage.fillTaskForm(
                taskName,
                taskName,
                "Оптимизация производительности",
                "Low",
                ""
        );

        createTaskPage.assertCreateButtonDisabled();
    }

    /**
     * Проверка, что задача не создается с названием, состоящим из одних пробелов.
     */
    @Test
    @Story("Валидация формы")
    @Description("Проверка запрета создания задачи с названием из одних пробелов.")
    @Severity(SeverityLevel.NORMAL)
    void notCreateTaskWithWhitespaceTitle() {
        CreateTaskPage createTaskPage = new CreateTaskPage(getPage()).login();

        Allure.step("Пробуем создать задачу с названием, состоящим из одних пробелов");
        createTaskPage.fillTaskForm(
                "             ",
                "Задача с именем, состоящего из пробелов " + newRand,
                "Оптимизация производительности",
                "Low",
                "Илья Романов"
        );

        createTaskPage.assertCreateButtonDisabled();
    }
}
