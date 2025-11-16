package app.theblu.cashflow.cs.batteries.common

import org.springframework.util.StringUtils
import java.util.*
import java.util.stream.Collectors

object ExceptionUtil {
    fun interface RunnableWithException {
        @Throws(Exception::class)
        fun run()
    }

    /**
     * Can be used to suppress any exception that code can throw
     *
     * @param runnableWithException
     */
    fun suppress(runnableWithException: RunnableWithException) {
        try {
            runnableWithException.run()
        } catch (e: Exception) {
            // do nothing
        }
    }

    /**
     * Converts an exception into list strings. ex: each stack trace line is converted string
     *
     * @param exception
     * @return
     */
    fun formatToPrettyString(exception: Throwable?): MutableList<String?> {
        var exception = exception
        if (exception == null) return LinkedList<String?>()
        val trace: MutableList<String?> = LinkedList<String?>()
        trace.addAll(formatToPrettyStringExcludeCause(exception))
        while (exception!!.cause != null) {
            exception = exception.cause
            trace.add("Cause:")
            trace.addAll(formatToPrettyString(exception))
        }
        return trace
    }

    fun formatToPrettyStringExcludeCause(exception: Throwable?): MutableList<String?> {
        if (exception == null) return LinkedList<String?>()
        val list = Arrays.stream<StackTraceElement?>(exception.getStackTrace())
            .map<String?> { trace: StackTraceElement? -> trace.toString() }.collect(Collectors.toList())
        if (StringUtils.hasText(exception.message)) list.add(0, exception.message)
        list.add(0, exception.javaClass.getName())
        return list
    }
}