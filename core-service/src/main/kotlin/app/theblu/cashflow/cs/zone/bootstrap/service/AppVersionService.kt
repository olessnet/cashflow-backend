package app.theblu.cashflow.cs.zone.bootstrap.service

import app.theblu.cashflow.cs.batteries.common.ClasspathUtil
import app.theblu.cashflow.cs.batteries.common.JsonUtil
import app.theblu.cashflow.cs.zone.bootstrap.dto.AppVersionRes
import app.theblu.cashflow.cs.zone.bootstrap.model.AppVersionRange
import app.theblu.cashflow.cs.zone.bootstrap.model.AppVersionState
import jakarta.annotation.PostConstruct
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class AppVersionService {
    private var list: List<AppVersionRange> = mutableListOf()

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @PostConstruct
    private fun init() {
        val yaml = ClasspathUtil.readPathAsString("config/app-versions.yaml")
        val json = JsonUtil.fromYamlToJson(yaml)
        val list = JsonUtil.fromJsonAsList(json, AppVersionRange::class.java).reversed()

        // validation
        val previous: AppVersionRange = list.first()
        previous.validate()
        for (ii in 1..<list.size) {
            val current = list[ii]
            current.validate()
            if (previous.validityStart.after(current.validityStart)) {
                throw RuntimeException("Invalid validity start date. Previous version validityStart must be before next version validityStart current: $current. previous: $previous")
            }
            if (previous.validityEnd.after(current.validityEnd)) {
                throw RuntimeException("Invalid validity end date. Previous version validityEnd must be before next version validityEnd current: $current. previous: $previous")
            }
            if (previous.versionTo.isGreaterThanOrEqualTo(current.versionFrom)) {
                throw RuntimeException("Invalid version to. previous.versionTo isGraterThanOrEqualTo current.versionFrom: $current. previous: $previous")
            }
            // if ACTIVE or DEPRECATED validityEnd is in past date, then throw error
            if((current.state == AppVersionState.ACTIVE || current.state == AppVersionState.DEPRECATED) && current.validityEnd.before(Date())){
                throw RuntimeException("Invalid validity end date. Validity end date is expired. It should not be expired. current: $current")
            }
        }
        this.list = list
    }

    fun state(version: String): AppVersionRes? {
        val optional: Optional<AppVersionRange> = list.stream()
            .filter { avr: AppVersionRange -> avr.contains(version) }.findFirst()
        if (optional.isPresent()) {
            val avr = optional.get()
            if (avr.state == AppVersionState.DEPRECATED) {
                return AppVersionRes(avr.state, avr.validityEnd)
            } else {
                return AppVersionRes(avr.state)
            }
        } else {
            return AppVersionRes(AppVersionState.BLOCKED)
        }
    }
}