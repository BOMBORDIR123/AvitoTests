package by.javaguru.avitotests.NewTaskTests.Pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
/**
 * Page Object для страницы Board (доски задач).
 * <p>
 * Содержит навигационные методы и действия, связанные с:
 * <ul>
 *     <li>переходом в раздел "Проекты";</li>
 *     <li>открытием конкретной доски задач;</li>
 *     <li>переходом в список всех задач;</li>
 *     <li>поиском задач;</li>
 *     <li>изменением статуса задачи;</li>
 *     <li>проверкой отображения задач на доске.</li>
 * </ul>
 *
 * <p>Класс инкапсулирует логику взаимодействия со страницей,
 * обеспечивая удобное и централизованное использование в тестах.
 */
public class BoardPage {
    private final Page page;

    /**
     * Конструктор Page Object.
     *
     * @param page основной объект Playwright {@link Page},
     *             через который выполняются все действия.
     */
    public BoardPage(Page page) {
        this.page = page;
    }

    /**
     * Переход в раздел "Проекты".
     * Ожидает переход на страницу /boards.
     */
    public void goToProjects() {
        page.getByRole(
                AriaRole.LINK,
                new Page.GetByRoleOptions().setName("Проекты")
        ).click();

        page.waitForURL("**/boards");
    }

    /**
     * Открывает первую доску задач (id = 1).
     * Ожидает переход на страницу /board/1.
     */
    public void goToBoard() {
        page.locator("a[href='/board/1']").click();
        page.waitForURL("**/board/1");
    }

    /**
     * Переход в раздел "Все задачи".
     * Ожидает переход на страницу /issues.
     */
    public void goToAllIssues() {
        page.getByRole(
                AriaRole.LINK,
                new Page.GetByRoleOptions().setName("Все задачи")
        ).click();

        page.waitForURL("**/issues");
    }

    /**
     * Выполняет поиск задачи по тексту.
     *
     * @param text строка, которую необходимо ввести в поле поиска.
     */
    public void searchTask(String text) {
        page.locator("input[placeholder='Поиск']").fill(text);
    }

    /**
     * Проверяет, отображается ли задача с заданным именем.
     *
     * @param taskName имя задачи
     * @return true — если задача видна на странице
     */
    public boolean isTaskVisible(String taskName) {
        return page.getByText(taskName).first().isVisible();
    }

    /**
     * Выбирает новый статус задачи в комбобоксе.
     *
     * @param newStatus название статуса, например: "В работе", "Готово", "Новая"
     */
    public void setStatus(String newStatus) {
        page.locator("label:text('Статус') + div [role='combobox']").click();

        page.getByRole(
                AriaRole.OPTION,
                new Page.GetByRoleOptions().setName(newStatus)
        ).click();
    }
}
