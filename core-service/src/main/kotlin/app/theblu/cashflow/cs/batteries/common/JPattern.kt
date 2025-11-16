package app.theblu.cashflow.cs.batteries.common

import java.util.regex.Matcher
import java.util.regex.Pattern

data class JPattern(
    val expression: String,
    val caseSensitive: Boolean = true,
    val fullTextSearch: Boolean = true,
) {

    @Transient
    private lateinit var pattern: Pattern

    init {
        if (caseSensitive) {
            pattern = Pattern.compile(expression)
        } else {
            pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        }
    }

    companion object {
        fun forExpression(expression: String): JPattern {
            return JPattern(expression = expression)
        }
    }

    private fun pattern(): Pattern {
        return pattern;
    }

    fun expression(): String = expression

    private fun getMatcherIfMatches(text: String): Matcher? {
        val matcher = pattern().matcher(text)
        if ((fullTextSearch && matcher.matches()) || matcher.find()) {
            return matcher
        } else {
            return null
        }
    }

    fun hasMatch(text: String): Boolean {
        return getMatcherIfMatches(text) != null
    }

    fun data(text: String): List<String> {
        val list = mutableListOf<String>()
        val matcher = getMatcherIfMatches(text) ?: return list

        val groupCount = matcher.groupCount()
        for (ii in 1 until groupCount + 1) {
            val group = matcher.group(ii)
            list.add(group)
        }
        return list
    }
}