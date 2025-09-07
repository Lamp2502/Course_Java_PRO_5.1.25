
package ru.cource.inno.java_pro.hw1_annotations_create;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * TestRunner.
 * @author Kirill_Lustochkin
 * @since 19.07.2025
 */

public class TestRunner {

    public static void runTests(Class<?> testClass) {
        try {
            validateAnnotations(testClass);

            Object testInstance = testClass.getDeclaredConstructor().newInstance();

            executeAnnotatedMethod(testClass, BeforeSuite.class, null);

            runTestMethods(testClass, testInstance);

            executeAnnotatedMethod(testClass, AfterSuite.class, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void validateAnnotations(Class<?> testClass) {
        validateSingleAnnotation(testClass, BeforeSuite.class);
        validateSingleAnnotation(testClass, AfterSuite.class);
        validateTestMethods(testClass);
    }

    private static void validateSingleAnnotation(Class<?> testClass, Class annotation) {
        int count = 0;
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                if (!java.lang.reflect.Modifier.isStatic(method.getModifiers())) {
                    throw new RuntimeException(
                        annotation.getSimpleName() + " метод должен быть статическим: " + method.getName()
                    );
                }
                count++;
            }
        }
        if (count > 1) {
            throw new RuntimeException(
                "Метод " + annotation.getSimpleName() + " должен быть в единственном экземпляре."
            );
        }
    }

    private static void validateTestMethods(Class<?> testClass) {
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                if (java.lang.reflect.Modifier.isStatic(method.getModifiers())) {
                    throw new RuntimeException(
                        String.format("@Test метод %s не должен быть статическим.", method.getName())
                    );
                }
                int priority = method.getAnnotation(Test.class).priority();
                if (priority < 1 || priority > 10) {
                    throw new RuntimeException(
                        String.format("Значение поля priority в @Test методе %s должно быть в пределах от 1 до 10",
                            method.getName())
                    );
                }
            }
        }
    }

    private static void executeAnnotatedMethod(
        Class<?> testClass,
        Class annotationClass,
        Object instance
    ) throws Exception {
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotationClass)) {
                method.setAccessible(true);
                method.invoke(instance);
            }
        }
    }

    private static void runTestMethods(Class<?> testClass, Object instance) throws Exception {
        List<Method> testMethods = new ArrayList<>();
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            }
        }

        testMethods.sort(Comparator
            .comparingInt((Method m) -> m.getAnnotation(Test.class).priority())
            .reversed()
        );

        for (Method testMethod : testMethods) {
            testMethod.setAccessible(true);
            testMethod.invoke(instance);
        }
    }
}
