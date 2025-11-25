package by.javaguru.avitotests.NewTaskTests.NewTaskTest;

import by.javaguru.avitotests.NewTaskTests.Pages.CreateTaskPage;
import by.javaguru.avitotests.NewTaskTests.Pages.TaskCardPage;
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
 * Тесты для проверки содержимого карточки задачи и обновления данных задачи.
 */
@Epic("Task Management")
@Feature("Task Card")
@RecordVideo
public class CheckTaskTests extends BaseTest {

    Random rand = new Random();
    private final int newRand = rand.nextInt(999);

    /**
     * Проверяет, что карточка задачи отображает все данные,
     * указанные при создании задачи.
     */
    @Test
    @Story("Проверка данных задачи")
    @Description("Создаём задачу со всеми параметрами, открываем карточку и проверяем корректность отображаемых данных.")
    @Severity(SeverityLevel.CRITICAL)
    void checkTaskWithAllData() {
        CreateTaskPage create = new CreateTaskPage(getPage()).login();

        String taskName = "Задача со всеми параметрами NEW" + newRand;
        String projectName = "Оптимизация производительности";
        String priority = "Low";
        String status = "Backlog";
        String assignee = "Илья Романов";

        Allure.step("Заполняем форму создания задачи всеми параметрами");
        create.fillTaskForm(taskName, taskName, projectName, priority, assignee);

        Allure.step("Отправляем форму и проверяем успешное создание задачи");
        create.submitAndCheck(taskName);

        TaskCardPage taskCardPage = new TaskCardPage(getPage());

        Allure.step("Открываем карточку задачи");
        taskCardPage.openTaskCard(taskName);

        Allure.step("Проверяем, что данные в карточке соответствуют введённым");
        taskCardPage.validateTaskCard(
                taskName,
                taskName,
                priority,
                status,
                assignee
        );
    }

    /**
     * Проверяет корректность обновления данных в карточке задачи:
     * изменение приоритета, статуса и исполнителя.
     */
    @Test
    @Story("Редактирование задачи")
    @Description("Создаём задачу, обновляем её параметры и проверяем, что изменения сохранены корректно.")
    @Severity(SeverityLevel.NORMAL)
    void updateTaskData() {
        CreateTaskPage create = new CreateTaskPage(getPage()).login();

        String taskName = "Задача для обновления NEW" + newRand;
        String project = "Оптимизация производительности";
        String priority = "Low";
        String assignee = "Илья Романов";

        Allure.step("Создаём задачу для последующего редактирования");
        create.fillTaskForm(taskName, taskName, project, priority, assignee);
        create.submitAndCheck(taskName);

        TaskCardPage taskCard = new TaskCardPage(getPage());

        Allure.step("Открываем созданную задачу");
        taskCard.openTaskCard(taskName);

        String newPriority = "Medium";
        String newStatus = "Done";
        String newAssignee = "Артем Белов";

        Allure.step("Вносим изменения в карточку задачи");
        taskCard.updateTask(newPriority, newStatus, newAssignee);

        Allure.step("Снова открываем карточку после обновления");
        taskCard.openTaskCard(taskName);

        Allure.step("Проверяем, что данные задачи были успешно обновлены");
        taskCard.validateTaskCard(
                taskName,
                taskName,
                newPriority,
                newStatus,
                newAssignee
        );
    }
}
