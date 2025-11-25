package by.javaguru.avitotests.NewTaskTests.Pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class BoardPage {
    private final Page page;

    public BoardPage(Page page) {
        this.page = page;
    }


    public void goToProjects() {
        page.getByRole(
                AriaRole.LINK,
                new Page.GetByRoleOptions().setName("Проекты")
        ).click();

        page.waitForURL("**/boards");
    }

    public void goToBoard() {
        page.locator("a[href='/board/1']").click();

        page.waitForURL("**/board/1");
    }

    public void goToAllIssues() {
        page.getByRole(
                AriaRole.LINK,
                new Page.GetByRoleOptions().setName("Все задачи")
        ).click();

        page.waitForURL("**/issues");
    }

    public void searchTask(String text) {
        page.locator("input[placeholder='Поиск']").fill(text);
    }

    public boolean isTaskVisible(String taskName) {
        return page.getByText(taskName).first().isVisible();
    }

    public void setStatus(String newStatus) {
        page.locator("label:text('Статус') + div [role='combobox']").click();

        page.getByRole(
                AriaRole.OPTION,
                new Page.GetByRoleOptions().setName(newStatus)
        ).click();
    }
}
