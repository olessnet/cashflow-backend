package app.theblu.cashflow.cs.batteries.common

import java.time.Instant

object DateTimeUtil {
    fun currentUtcTimestamp(): Long {
        return Instant.now().getEpochSecond()
    }
}