
package ru.cource.inno.java_pro.hw1_annotations_create;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AfterSuite.
 * @author Kirill_Lustochkin
 * @since 19.07.2025
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AfterSuite {}
