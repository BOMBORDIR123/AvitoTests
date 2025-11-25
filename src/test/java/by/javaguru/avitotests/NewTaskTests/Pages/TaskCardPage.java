package by.javaguru.avitotests.NewTaskTests.Pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Assertions;

public class TaskCardPage {

    private final Page page;

    public TaskCardPage(Page page) {
        this.page = page;
    }

    public void openTaskCard(String taskName) {

        Locator task = page.locator("h6.MuiTypography-root")
                .filter(new Locator.FilterOptions().setHasText(taskName))
                .first();

        task.waitFor();
        task.click();
    }

    public String getTitle(String expectedValue) {
        Locator title = page.locator("input[value='" + expectedValue + "']");
        return title.getAttribute("value");
    }

    public String getDescription(String expectedValue) {
        Locator description = page.locator("textarea")
                .filter(new Locator.FilterOptions().setHasText(expectedValue));
        return description.inputValue().trim();
    }

    public String getPriority(String expectedValue) {
        Locator priority = page.locator("input[value='" + expectedValue + "']");
        return priority.getAttribute("value");
    }

    public String getStatus(String expectedValue) {
        Locator status = page.locator("input[value='" + expectedValue + "']");
        return status.getAttribute("value");
    }

    public String getAssignee(String expectedValue) {
        Locator assignee = page.locator(
                "div.MuiInputBase-root div[role='combobox']:has-text('" + expectedValue + "')"
        );
        return assignee.innerText().trim();
    }

    public void validateTaskCard(String expectedTitle,
                                 String expectedDescription,
                                 String expectedPriority,
                                 String expectedStatus,
                                 String expectedAssignee) {

        Assertions.assertEquals(expectedTitle,      getTitle(expectedTitle),      "Название задачи неверное!");
        Assertions.assertEquals(expectedDescription, getDescription(expectedDescription), "Описание задачи неверное!");
        Assertions.assertEquals(expectedPriority,    getPriority(expectedPriority),    "Приоритет неверный!");
        Assertions.assertEquals(expectedStatus,      getStatus(expectedStatus),      "Статус неверный!");
        Assertions.assertEquals(expectedAssignee,    getAssignee(expectedAssignee),    "Исполнитель неверный!");
    }

    public void updateTask(
            String newPriority,
            String newStatus,
            String newAssignee
    ) {
        if (newPriority != null && !newPriority.isEmpty()) {
            page.locator("#select-label:has-text('Приоритет') + div .MuiSelect-select").click();
            page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(newPriority)).click();
        }

        if (newStatus != null && !newStatus.isEmpty()) {
            page.locator("#select-label:has-text('Статус') + div .MuiSelect-select").click();
            page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(newStatus)).click();
        }

        if (newAssignee != null && !newAssignee.isEmpty()) {
            page.locator("#select-label:has-text('Исполнитель') + div .MuiSelect-select").click();
            page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(newAssignee)).click();
        }

        page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Обновить")
        ).click();

        page.waitForTimeout(500);
    }


}
