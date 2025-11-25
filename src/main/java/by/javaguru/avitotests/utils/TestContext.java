package by.javaguru.avitotests.utils;

import by.javaguru.avitotests.allure.AllureUtils;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class TestContext {
    private static final ThreadLocal<Playwright> playwrightThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> browserContextThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<AllureUtils> allureUtilsThreadLocal = new ThreadLocal<>();

    private static final ThreadLocal<String> baseUrlThreadLocal = new ThreadLocal<>();

    public static void setBaseUrl(String url) {
        baseUrlThreadLocal.set(url);
    }

    public static String getBaseUrl() {
        return baseUrlThreadLocal.get();
    }

    public static Playwright getPlaywright() { return playwrightThreadLocal.get(); }
    public static void setPlaywright(Playwright playwright) { playwrightThreadLocal.set(playwright); }

    public static Browser getBrowser() { return browserThreadLocal.get(); }
    public static void setBrowser(Browser browser) { browserThreadLocal.set(browser); }

    public static BrowserContext getBrowserContext() { return browserContextThreadLocal.get(); }
    public static void setBrowserContext(BrowserContext browserContext) { browserContextThreadLocal.set(browserContext); }

    public static Page getPage() { return pageThreadLocal.get(); }
    public static void setPage(Page page) { pageThreadLocal.set(page); }

    public static AllureUtils getAllureUtils() { return allureUtilsThreadLocal.get(); }
    public static void setAllureUtils(AllureUtils allureUtils) { allureUtilsThreadLocal.set(allureUtils); }

    public static void cleanup() {
        if (getBrowserContext() != null) {
            getBrowserContext().close();
            setBrowserContext(null);
        }
        if (getBrowser() != null) {
            getBrowser().close();
            setBrowser(null);
        }
        if (getPlaywright() != null) {
            getPlaywright().close();
            setPlaywright(null);
        }

        playwrightThreadLocal.remove();
        browserThreadLocal.remove();
        browserContextThreadLocal.remove();
        pageThreadLocal.remove();
        allureUtilsThreadLocal.remove();
        baseUrlThreadLocal.remove();
    }
}
