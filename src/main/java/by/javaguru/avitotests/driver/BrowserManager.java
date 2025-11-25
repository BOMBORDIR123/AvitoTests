package by.javaguru.avitotests.driver;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;

import static by.javaguru.avitotests.Constants.Capability.BROWSER;

public class BrowserManager {
    public static Browser getBrowser(final Playwright playwright) {

        String browserName = (BROWSER == null || BROWSER.isBlank())
                ? "CHROMIUM"
                : BROWSER.toUpperCase();

        try {
            return BrowserFactory.valueOf(browserName).createInstance(playwright);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(
                    "Unknown browser: " + browserName + ". Use CHROMIUM, FIREFOX, WEBKIT"
            );
        }
    }
}
