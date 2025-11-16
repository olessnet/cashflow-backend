package app.theblu.cashflow.cs.batteries.common

import com.twmacinta.util.MD5

object Hash {
    fun md5(vararg args: Any?): String {
        return MD5(args.contentToString()).asHex()
    }
}