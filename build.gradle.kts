plugins {
    id("org.springframework.boot") version "3.4.0" apply false
    id("io.spring.dependency-management") version "1.1.6" apply false
}

allprojects {
    group = "ru.cource.inno.java_pro"
    version = "1.0.0"
    repositories { mavenCentral() }
}

subprojects {
    // применяем core-плагин java для всех подпроектов
    pluginManager.apply("java")

    // настраиваем toolchain после применения плагина
    extensions.configure<org.gradle.api.plugins.JavaPluginExtension> {
        toolchain.languageVersion.set(org.gradle.jvm.toolchain.JavaLanguageVersion.of(21))
    }
}
