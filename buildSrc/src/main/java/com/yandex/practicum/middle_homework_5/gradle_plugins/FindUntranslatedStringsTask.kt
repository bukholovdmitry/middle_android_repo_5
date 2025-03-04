package com.yandex.practicum.middle_homework_5.gradle_plugins

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory


@CacheableTask
abstract class FindUntranslatedStringsTask : DefaultTask() {
    @TaskAction
    fun findUntranslatedStrings() {
        val resDir = File(project.projectDir, "src/main/res")
        val stringsFile = File(resDir, "values/strings.xml")
        val missingStrings = mutableMapOf<String, MutableList<String>>()

        if (!stringsFile.exists()) {
            throw GradleException("Файл strings.xml не найден: ${stringsFile.absolutePath}")
        }

        val stringsFromXml = DocumentBuilderFactory
            .newInstance()
            .newDocumentBuilder()
            .parse(stringsFile)
            .getElementsByTagName("string")

        val stringIdentities = (0 until stringsFromXml.length).map { i ->
            val node = stringsFromXml.item(i)
            val name = node.attributes?.getNamedItem("name")?.nodeValue ?: ""
            name
        }

        stringIdentities.forEach { stringName ->
            val hasTranslation = hasTranslation(stringName)
            if (!hasTranslation) {
                missingStrings.computeIfAbsent(stringName) { mutableListOf() }
                    .add("отсутствует перевод в файле: $stringsFile")
            }
        }

        if (missingStrings.isNotEmpty()) {
            val stringBuilderErrorText =
                StringBuilder("Пропущенные переводы для следующих значений:").append(System.lineSeparator())
            missingStrings.forEach { missing ->
                stringBuilderErrorText
                    .append(" ${missing.key} ")
                    .append(System.lineSeparator())
                    .append(missing.value.joinToString(separator = System.lineSeparator()))
                    .append(System.lineSeparator())

            }
            throw GradleException(stringBuilderErrorText.toString())
        }
    }

    private fun hasTranslation(stringName: String): Boolean {
        val resDir = File(project.projectDir, "src/main/res")
        val localizationDirs = resDir.listFiles { file ->
            file.isDirectory && file.name.startsWith("values") && file.name != "values"
        } ?: return false

        return localizationDirs.any { dir ->
            val localizedStringsFile = File(dir, "strings.xml")
            if (localizedStringsFile.exists()) {
                val localizedStringsFromXml = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(localizedStringsFile)
                    .getElementsByTagName("string")

                (0 until localizedStringsFromXml.length).any { i ->
                    localizedStringsFromXml.item(i).attributes?.getNamedItem("name")?.nodeValue == stringName
                }
            } else {
                false
            }
        }
    }
}