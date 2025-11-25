package by.javaguru.avitotests;

public class Constants {
    public static class Capability {

        // Дефолтный браузер — CHROMIUM
        public final static String BROWSER =
                System.getProperty("browser", "CHROMIUM");

        public final static Boolean NEED_VIDEO =
                Boolean.valueOf(System.getProperty("isVideoRecord", "false"));

        public final static String BASE_URL = "https://avito-tech-internship-psi.vercel.app/";

    }
}
