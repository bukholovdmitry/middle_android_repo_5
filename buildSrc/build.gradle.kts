plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(gradleApi())
}

gradlePlugin {
    plugins {
        create("find-untranslated-plugin") {
            id = "find-untranslated-plugin"
            implementationClass = "FindUntranslatedStringsPlugin"
        }
    }
    plugins {
        create("find_untranslated_plugin") {
            id = "find_untranslated_plugin"
            implementationClass =
                "com.yandex.practicum.middle_homework_5.gradle_plugins.FindUntranslatedStringsPlugin"
        }
    }
}