
package ru.cource.inno.java_pro.hw1_annotations_create;

/**
 * AnnotatedMethods.
 * @author Kirill_Lustochkin
 * @since 19.07.2025
 */
public class AnnotatedMethods {

    @BeforeSuite
    public static void init() {
        System.out.println("Подготавливаем среду до начала тестов.");
    }

    @AfterSuite
    public static void cleanup() {
        System.out.println("Завершаем все процессы после завершения тестов.");
    }

    @Test(priority = 1)
    public void highPriorityTest() {
        System.out.println("Низкоприоритетный тест.");
    }

    @Test(priority = 10)
    public void lowPriorityTest() {
        System.out.println("Высокоприоритетный тест.");
    }

    @Test
    public void anotherMediumTest() {
        System.out.println("Тест без явного определения приоритета имеет средний приоритет.");
    }
}
