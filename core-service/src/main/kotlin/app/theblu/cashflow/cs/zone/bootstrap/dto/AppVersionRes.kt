package app.theblu.cashflow.cs.zone.bootstrap.dto

import app.theblu.cashflow.cs.zone.bootstrap.model.AppVersionState
import java.util.Date

data class AppVersionRes(
    val appVersionState: AppVersionState,
    val validityEnd: Date? = null
)