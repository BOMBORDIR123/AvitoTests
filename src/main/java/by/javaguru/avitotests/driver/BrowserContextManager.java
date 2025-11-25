package by.javaguru.avitotests.driver;

import by.javaguru.avitotests.utils.TestContext;
import com.microsoft.playwright.Browser.NewContextOptions;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Playwright;

import java.nio.file.Paths;

import static by.javaguru.avitotests.Constants.Capability.NEED_VIDEO;

public class BrowserContextManager {

    private BrowserContextManager() {
    }

    /**
     * Создает и настраивает браузер, контекст и страницу для текущего потока
     */
    public static void initializeBrowserContext(Integer width, Integer height) {
        initializePlaywright();
        initializeBrowser();
        createBrowserContextAndPage(width, height);
    }

    /**
     * Инициализирует Playwright для текущего потока, если еще не создан
     */
    private static void initializePlaywright() {
        if (TestContext.getPlaywright() == null) {
            TestContext.setPlaywright(Playwright.create());
        }
    }

    /**
     * Инициализирует браузер для текущего потока, если еще не создан
     */
    private static void initializeBrowser() {
        if (TestContext.getBrowser() == null) {
            TestContext.setBrowser(BrowserManager.getBrowser(TestContext.getPlaywright()));
        }
    }

    /**
     * Создает контекст браузера и страницу для текущего теста
     */
    private static void createBrowserContextAndPage(Integer width, Integer height) {
        NewContextOptions contextOptions = createContextOptions(width, height);
        BrowserContext context = TestContext.getBrowser().newContext(contextOptions);

        TestContext.setBrowserContext(context);
//        TestContext.setPage(context.newPage());
    }

    /**
     * Создает настройки для контекста браузера
     */
    private static NewContextOptions createContextOptions(Integer width, Integer height) {
        NewContextOptions options = new NewContextOptions()
                .setIgnoreHTTPSErrors(true)
                .setViewportSize(width, height);

        if (NEED_VIDEO) {
            options.setRecordVideoDir(Paths.get("build", "video-results"))
                    .setRecordVideoSize(width, height);
        }

        return options;
    }

}