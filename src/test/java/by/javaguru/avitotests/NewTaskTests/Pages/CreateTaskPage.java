package by.javaguru.avitotests.NewTaskTests.Pages;

import by.javaguru.avitotests.utils.TestContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Assertions;

import static by.javaguru.avitotests.utils.TestContext.getPage;

/**
 * Page Object для страницы создания задачи.
 * <p>
 * Содержит методы для:
 * <ul>
 *     <li>открытия страницы создания задачи;</li>
 *     <li>заполнения формы задачи (название, описание, проект, приоритет, исполнитель);</li>
 *     <li>подтверждения создания задачи и проверки результата;</li>
 *     <li>валидации состояния кнопки "Создать".</li>
 * </ul>
 *
 * <p>Класс инкапсулирует взаимодействие с UI формы создания задачи
 * и обеспечивает удобное использование в автотестах.
 */
public class CreateTaskPage {

    private final Page page;

    /**
     * Конструктор Page Object.
     *
     * @param page объект Playwright {@link Page}, через который выполняются действия.
     */
    public CreateTaskPage(Page page) {
        this.page = page;
    }

    /**
     * Переходит на базовый URL приложения.
     *
     * @return текущий объект {@link CreateTaskPage}
     */
    public CreateTaskPage open() {
        page.navigate(TestContext.getBaseUrl());
        return this;
    }

    /**
     * Заглушка для login() в случае необходимости авторизации.
     * Сейчас просто открывает страницу.
     *
     * @return текущий объект {@link CreateTaskPage}
     */
    public CreateTaskPage login() {
        open();
        return this;
    }

    /**
     * Заполняет форму создания задачи.
     * Каждое поле заполняется только если его значение непустое.
     *
     * @param title       заголовок задачи
     * @param description описание задачи
     * @param projectName название проекта для выбора
     * @param priority    приоритет задачи
     * @param assignee    исполнитель задачи
     * @return текущий объект {@link CreateTaskPage}
     */
    public CreateTaskPage fillTaskForm(
            String title,
            String description,
            String projectName,
            String priority,
            String assignee
    ) {
        Page page = getPage();

        page.getByText("Создать задачу").first().click();

        if (title != null && !title.isEmpty()) {
            page.getByLabel("Название").fill(title);
        }

        if (description != null && !description.isEmpty()) {
            page.getByLabel("Описание").fill(description);
        }

        if (projectName != null && !projectName.isEmpty()) {
            page.locator("#select-label:has-text('Проект') + div .MuiSelect-select").click();
            page.getByRole(AriaRole.OPTION,
                    new Page.GetByRoleOptions().setName(projectName)
            ).click();
        }

        if (priority != null && !priority.isEmpty()) {
            page.locator("#select-label:has-text('Приоритет') + div .MuiSelect-select").click();
            page.getByRole(AriaRole.OPTION,
                    new Page.GetByRoleOptions().setName(priority)
            ).click();
        }

        if (assignee != null && !assignee.isEmpty()) {
            page.locator("#select-label:has-text('Исполнитель') + div .MuiSelect-select").click();
            page.getByRole(AriaRole.OPTION,
                    new Page.GetByRoleOptions().setName(assignee)
            ).click();
        }

        return this;
    }

    /**
     * Подтверждает создание задачи, ожидает появления задачи на странице
     * и выполняет ассерты.
     *
     * @param expectedName ожидаемое имя задачи, которое должно появиться после создания
     * @return текущий объект {@link CreateTaskPage}
     */
    public CreateTaskPage submitAndCheck(String expectedName) {
        Page page = getPage();

        Locator createBtn = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Создать")
        );

        // Ожидание, пока кнопка станет активной
        page.waitForCondition(() ->
                !(Boolean) createBtn.evaluate("el => el.disabled")
        );

        createBtn.click();

        // Ожидание появления созданной задачи
        page.waitForSelector("text=" + expectedName);

        Assertions.assertTrue(
                page.getByText(expectedName).isVisible(),
                "Задача не отображается после создания!"
        );

        return this;
    }

    /**
     * Проверяет, что кнопка "Создать" находится в неактивном (disabled) состоянии.
     */
    public CreateTaskPage assertCreateButtonDisabled() {
        Locator createBtn = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Создать")
        );

        boolean disabled = (Boolean) createBtn.evaluate("el => el.disabled");

        Assertions.assertTrue(
                disabled,
                "Ожидалось, что кнопка 'Создать' будет недоступна, но она активна."
        );

        return this;
    }
}
