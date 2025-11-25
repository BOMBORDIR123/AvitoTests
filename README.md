Этот проект содержит набор автоматизированных UI-тестов для веб-приложения
Avito Tech Internship Task Manager:
https://avito-tech-internship-psi.vercel.app/

Приложение представляет собой систему управления задачами, где пользователь может:

создавать задачи;

редактировать задачи;

просматривать карточки задач;

искать задачи;

фильтровать по статусам;

работать с доской задач (Board);

перемещаться между разделами.

Тестовый проект написан на Java + Playwright, использует JUnit 5, создаёт Allure отчёты, и поддерживает запись видео тестов.

Проект протестирован в разных браузерах:

Chromium

Firefox

WebKit

Запуск проекта
➤ 1. Установка зависимостей

Перед первым запуском установите браузеры для Playwright:

mvn playwright:install

▶Запуск тестов
Обычный запуск:
mvn test

Запись видео тестов

Включить запись видео можно командой:

mvn test -DisVideoRecord=true


Видео будут сохранены в директорию:

/target/videos

Запуск тестов в разных браузерах

По умолчанию используется Chromium, но можно указать другой браузер:

Chromium (по умолчанию)
mvn test -Dbrowser=chromium

Firefox
mvn test -Dbrowser=firefox

WebKit
mvn test -Dbrowser=webkit

Формирование Allure отчёта
1. Запуск тестов с генерацией Allure результатов:
   mvn clean test


После запуска Allure-файлы появятся в папке:

/target/allure-results

2. Просмотр отчёта:
   allure serve target/allure-results


Команда откроет интерактивный отчёт в браузере.

3. Генерация отчёта в директорию:
   allure generate target/allure-results -o target/allure-report --clean

4. Просмотр локального отчёта:
   allure open target/allure-report

Структура проекта
src/
└── test/java/
├── BaseTest.java              # Базовые настройки Playwright
├── CreateTaskTests.java       # Тесты создания задач
├── CheckTaskTests.java        # Тесты проверки и обновления задач
├── BoardPageTests.java        # Тесты доски задач, поиска, навигации
└── Pages/                     # Page Object Model

Основные технологии
Технология	Назначение
Java 21	Язык тестирования
Playwright Java	UI-автотесты
JUnit 5	Фреймворк тестирования
Allure 2	Формирование отчётов
Maven	Управление проектом
Возможности проекта

Полный набор UI-тестов на создание, валидацию, поиск и обновление задач

Поддержка трёх браузеров

Поддержка записи видео

Поддержка Allure-отчётов

Полный Page Object Model

Детализированные шаги с Allure