package by.javaguru.avitotests.NewTaskTests.NewTaskTest;

import by.javaguru.avitotests.NewTaskTests.Pages.BoardPage;
import by.javaguru.avitotests.NewTaskTests.Pages.CreateTaskPage;
import by.javaguru.avitotests.annotations.video.RecordVideo;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * Тесты для проверки функциональности доски задач:
 *  - поиск задачи
 *  - навигация между разделами
 */
@Epic("Task Management")
@Feature("Board Page")
@RecordVideo
public class BoardPageTests extends BaseTest {

    Random rand = new Random();
    private final int newRand = rand.nextInt(999);

    /**
     * Проверяет, что задача успешно находится через строку поиска.
     */
    @Test
    @Story("Поиск задач")
    @Description("Создаём задачу со случайным именем, затем ищем её через поиск на доске.")
    @Severity(SeverityLevel.CRITICAL)
    void searchTaskByRandomName() {
        CreateTaskPage create = new CreateTaskPage(getPage()).login();

        String taskName = "Поиск задачи NEW" + newRand;
        String project = "Оптимизация производительности";
        String priority = "Low";
        String assignee = "Илья Романов";

        Allure.step("Заполняем форму создания задачи");
        create.fillTaskForm(taskName, taskName, project, priority, assignee);

        Allure.step("Отправляем форму и проверяем, что задача создалась");
        create.submitAndCheck(taskName);

        BoardPage board = new BoardPage(getPage());

        Allure.step("Выполняем поиск по части имени задачи: NEW" + newRand);
        board.searchTask("NEW" + newRand);

        Allure.step("Проверяем, что задача отображается в результатах поиска");
        Assertions.assertTrue(
                board.isTaskVisible(taskName),
                "Задача не найдена по поиску!"
        );
    }

    /**
     * Проверяет корректную навигацию между разделами:
     * Проекты → Доска → Все задачи
     */
    @Test
    @Story("Навигация по разделам")
    @Description("Проверяем навигацию между основными разделами: Projects → Board → All Issues.")
    @Severity(SeverityLevel.NORMAL)
    public void testNavigation() {
        new CreateTaskPage(getPage()).login();
        BoardPage board = new BoardPage(getPage());

        Allure.step("Переходим в раздел Проекты");
        board.goToProjects();
        Assertions.assertTrue(
                getPage().url().contains("/boards"),
                "После перехода в Проекты URL не содержит /boards!"
        );

        Allure.step("Переходим на доску");
        board.goToBoard();
        Assertions.assertTrue(
                getPage().url().contains("/board"),
                "После перехода к доске URL не содержит /board!"
        );

        Allure.step("Переходим в раздел всех задач");
        board.goToAllIssues();
        Assertions.assertTrue(
                getPage().url().contains("/issues"),
                "После перехода к списку задач URL не содержит /issues!"
        );
    }
}
