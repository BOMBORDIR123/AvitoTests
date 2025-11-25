package by.javaguru.avitotests.driver;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;

import static by.javaguru.avitotests.Constants.Capability.BROWSER;

/**
 * BrowserManager — фабрика верхнего уровня для создания экземпляра браузера Playwright.
 * <p>
 * Определяет, какой браузер должен быть запущен, на основе значения переменной
 * окружения/системного свойства {@code BROWSER}. Если свойство не указано или пустое,
 * используется браузер по умолчанию — {@code CHROMIUM}.
 * <p>
 * Поддерживаемые значения:
 * <ul>
 *     <li>CHROMIUM</li>
 *     <li>FIREFOX</li>
 *     <li>WEBKIT</li>
 * </ul>
 *
 * Если пользователь указал неизвестное имя браузера, будет выброшено исключение
 * {@link RuntimeException} с информативным сообщением.
 *
 * <p>Пример использования:
 * <pre>{@code
 * Playwright playwright = Playwright.create();
 * Browser browser = BrowserManager.getBrowser(playwright);
 * }</pre>
 */
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
