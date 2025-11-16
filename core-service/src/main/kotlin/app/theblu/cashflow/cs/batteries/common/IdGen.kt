package app.theblu.cashflow.cs.batteries.common

import io.azam.ulidj.ULID

object IdGen {
    fun uuid(): String {
        return ULID.random()
//        return UUID.randomUUID().toString();
    }

    fun ulid(): String {
        return ULID.randomULID().toString()
    }
}