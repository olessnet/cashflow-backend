package app.theblu.cashflow.cs.zone.bootstrap.model

import org.semver4j.Semver
import org.semver4j.SemverException
import java.util.*

data class AppVersionRange(
    val state: AppVersionState,
    val versionFrom: Semver,
    val versionTo: Semver,
    val validityStart: Date,
    val validityEnd: Date
) {

    fun validate() {
        if(!versionFrom.isLowerThanOrEqualTo(versionTo)) throw RuntimeException("Invalid version from & to. from is before to $this")
        if (validityEnd.before(validityStart)) throw RuntimeException("Invalid validity end date. End date is before start date. $this")
    }

    private fun assertValidVersion(version: String) {
        try {
            Semver(version)
        } catch (ex: SemverException) {
            throw RuntimeException("invalid semver given: $version")
        }
    }

    fun contains(version: String): Boolean {
        assertValidVersion(version);
        val temp = Semver(version)
        return versionFrom.isLowerThanOrEqualTo(temp) && versionTo.isGreaterThanOrEqualTo(version)
    }

    fun state(version: String): AppVersionState {
        this.assertValidVersion(version)
        if (!contains(version)) throw RuntimeException("state() of AppVersionRange should only be called with valid version in the range")
        return state
    }
}
