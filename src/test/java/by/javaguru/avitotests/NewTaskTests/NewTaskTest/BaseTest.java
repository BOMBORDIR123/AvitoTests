package by.javaguru.avitotests.NewTaskTests.NewTaskTest;

import by.javaguru.avitotests.allure.AllureUtils;
import by.javaguru.avitotests.driver.BrowserContextManager;
import by.javaguru.avitotests.utils.TestContext;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static by.javaguru.avitotests.Constants.Capability.BASE_URL;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    protected Page getPage() {
        return TestContext.getPage();
    }

    protected BrowserContext getBrowserContext() {
        return TestContext.getBrowserContext();
    }

    protected AllureUtils getAllureUtils() {
        return TestContext.getAllureUtils();
    }

    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        BrowserContextManager.initializeBrowserContext(1920, 1080);

        Page page = getBrowserContext().newPage();
        TestContext.setPage(page);

        TestContext.setBaseUrl(BASE_URL);
        TestContext.setAllureUtils(new AllureUtils(getBrowserContext(), getPage()));
    }

    @AfterEach
    public void attachVideoToAllure(TestInfo testInfo) throws IOException {
        if (getAllureUtils() != null) {
            getAllureUtils().attachVideoToAllure(testInfo);
        }
        if (getBrowserContext() != null) {
            getBrowserContext().close();
            TestContext.setBrowserContext(null);
            TestContext.setPage(null);
        }
    }

    @AfterAll
    void closeBrowser() {
        TestContext.cleanup();
    }
}