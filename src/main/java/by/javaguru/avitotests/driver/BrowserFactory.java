package by.javaguru.avitotests.driver;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
/**
 * BrowserFactory — перечисление-фабрика для создания конкретных экземпляров браузеров Playwright.
 * <p>
 * Каждое значение enum представляет один из поддерживаемых Playwright браузеров:
 * <ul>
 *     <li>{@code CHROMIUM}</li>
 *     <li>{@code FIREFOX}</li>
 *     <li>{@code WEBKIT}</li>
 * </ul>
 *
 * <p>Каждый элемент реализует метод {@link #createInstance(Playwright)}, который запускает
 * соответствующий браузер с базовыми параметрами, определёнными в {@link #options()}.
 *
 * <p>Метод {@link #options()} задаёт общие настройки запуска браузера:
 * <ul>
 *     <li>{@code headless = false} — запуск в режиме с UI (видимый браузер)</li>
 *     <!-- если slowMo нужен — можно раскомментировать -->
 * </ul>
 *
 * <p>Пример использования:
 * <pre>{@code
 * Playwright playwright = Playwright.create();
 * Browser browser = BrowserFactory.CHROMIUM.createInstance(playwright);
 * }</pre>
 *
 * <p>Используется совместно с {@link by.javaguru.avitotests.driver.BrowserManager},
 * который выбирает тип браузера на основе системного свойства {@code -Dbrowser=...}.
 */
public enum BrowserFactory {

    CHROMIUM {
        @Override
        public Browser createInstance(final Playwright playwright) {
            return playwright.chromium().launch(options());
        }
    },
    FIREFOX {
        @Override
        public Browser createInstance(final Playwright playwright) {
            return playwright.firefox().launch(options());
        }
    },
    WEBKIT {
        @Override
        public Browser createInstance(final Playwright playwright) {
            return playwright.webkit().launch(options());
        }
    };

    public BrowserType.LaunchOptions options() {
        return new BrowserType.LaunchOptions()
                .setHeadless(false);
//                .setSlowMo(2000);
    }

    public abstract Browser createInstance(final Playwright playwright);
}
