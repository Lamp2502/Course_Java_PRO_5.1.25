/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2023 VTB Group. All rights reserved.
 */

package ru.cource.inno.java_pro.hw2_streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Main.
 * @author Kirill_Lustochkin
 * @since 19.07.2025
 */
public class Main {
    record Employee(String name, int age, String position) {}

    public static void main(String[] args) {
        // 1. Третье наибольшее число
        System.out.println("1: " + Stream.of(5, 2, 10, 9, 4, 3, 10, 1, 13).sorted(Comparator.reverseOrder()).skip(2).findFirst().orElse(null));

        // 2. Третье наибольшее уникальное число
        System.out.println("2: " + Stream.of(5, 2, 10, 9, 4, 3, 10, 1, 13).distinct().sorted(Comparator.reverseOrder()).skip(2).findFirst().orElse(null));

        // 3. Три самых старших инженера
        List<Employee> employees = List.of(
            new Employee("Слава", 33, "Инженер"),
            new Employee("Женя", 18, "Механик"),
            new Employee("Игорь", 44, "Инженер"),
            new Employee("Юра", 33, "Инженер"),
            new Employee("Настя", 19, "Инженер"),
            new Employee("Женя", 28, "Инженер")
        );
        System.out.println("3: " + employees.stream().filter(e -> e.position().equals("Инженер")).sorted(Comparator.comparingInt(Employee::age).reversed()).limit(3).map(Employee::name).toList());

        // 4. Средний возраст инженеров
        System.out.println("4: " + employees.stream().filter(e -> e.position().equals("Инженер")).mapToInt(Employee::age).average().orElse(0));

        // 5. Самое длинное слово
        List<String> words = List.of("ярд", "ад", "Самое_длинное_слово", "слово");
        System.out.println("5: " + words.stream().max(Comparator.comparingInt(String::length)).orElse(null));

        // 6. Статистика слов
        String text = "3 2 2 3 1 3";
        System.out.println("6: " + Arrays.stream(text.split(" ")).collect(Collectors.groupingBy(w -> w, Collectors.counting())));

        // 7. Сортировка слов по длине и алфавиту
        List<String> strings = List.of("ярд", "ад", "Самое_длинное_слово", "слово", "род", "яд");
        System.out.println("7: " + strings.stream().sorted(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder())).toList());

        // 8. Самое длинное слово в массиве строк
        String[] arrays = {"снег сон сноб", "ярд ад Самое_длинное_слово", "вода лёд хлеб солнце"};
        System.out.println("8: " + Arrays.stream(arrays).flatMap(s -> Arrays.stream(s.split(" "))).max(Comparator.comparingInt(String::length)).orElse(null));
    }
}
