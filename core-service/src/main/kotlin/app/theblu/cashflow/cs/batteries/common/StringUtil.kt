package app.theblu.cashflow.cs.batteries.common

import java.util.*

object StringUtil {
    fun splitByNewLine(content: String): MutableList<String?> {
        return Arrays.stream<String>(content.split("\\r?\\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            .toList()
    }

    fun splitBy(content: String, splitBy: String): MutableList<String?> {
        return Arrays.stream<String>(content.split(splitBy.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            .toList()
    }

    fun trimDoubleQuotes(input: String): String {
        return input.replace("^\"+|\"+$".toRegex(), "")
    }
}