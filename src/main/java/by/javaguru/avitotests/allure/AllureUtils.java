package by.javaguru.avitotests.allure;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.TestInfo;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static by.javaguru.avitotests.Constants.Capability.NEED_VIDEO;

public class AllureUtils {
    protected BrowserContext browserContext;
    protected Page page;

    public AllureUtils(BrowserContext browserContext, Page page){
        this.page = page;
        this.browserContext = browserContext;
    }

    /**
     * Прикрепляет видео запись теста к отчету Allure вместе со скриншотом.
     * Метод выполняет следующие действия:
     * Создает скриншот текущей страницы и прикрепляет его к отчету
     * Закрывает контекст браузера, завершая запись видео
     * Извлекает путь к записанному видеофайлу
     * Читает видеофайл и прикрепляет его к отчету Allure с именем в формате: {@code [имя_тестового_метода]_video}
     */
    public void attachVideoToAllure(TestInfo testInfo) throws IOException {
        takeScreenshot(testInfo);
        browserContext.close();
        if (NEED_VIDEO){
            String videoPath = page.video().path().toString();
            try (InputStream videoStream = new FileInputStream(videoPath)) {
                Allure.addAttachment(testInfo.getTestMethod().get().getName() + "_video", "video/webm", videoStream, ".webm");
            }
        }
    }

    public void takeScreenshot(TestInfo testInfo) {
        try {
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
            Allure.addAttachment(
                    testInfo.getTestMethod().get().getName() + "_screenshot",
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    ".png"
            );
        } catch (Exception e) {
            System.out.println("Ошибка при снятии скриншота: " + e.getMessage());
        }
    }

}
