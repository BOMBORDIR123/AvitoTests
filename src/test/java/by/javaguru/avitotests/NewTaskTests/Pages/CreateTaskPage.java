package by.javaguru.avitotests.NewTaskTests.Pages;

import by.javaguru.avitotests.utils.TestContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Assertions;

import static by.javaguru.avitotests.utils.TestContext.getPage;

public class CreateTaskPage {

    private final Page page;

    public CreateTaskPage(Page page) {
        this.page = page;
    }

    public CreateTaskPage open() {
        page.navigate(TestContext.getBaseUrl());
        return this;
    }

    public CreateTaskPage login() {
        open();
        return this;
    }

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

    public CreateTaskPage submitAndCheck(String expectedName) {
        Page page = getPage();

        Locator createBtn = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Создать")
        );

        page.waitForCondition(() ->
                !(Boolean) createBtn.evaluate("el => el.disabled")
        );

        createBtn.click();

        page.waitForSelector("text=" + expectedName);

        Assertions.assertTrue(
                page.getByText(expectedName).isVisible(),
                "Задача не отображается после создания!"
        );

        return this;
    }

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