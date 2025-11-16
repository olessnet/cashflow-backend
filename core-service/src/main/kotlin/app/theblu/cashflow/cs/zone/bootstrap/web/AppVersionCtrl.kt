package app.theblu.cashflow.cs.zone.bootstrap.web

import app.theblu.cashflow.cs.batteries.rocket.res.ApiResBuilder
import app.theblu.cashflow.cs.zone.bootstrap.service.AppVersionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/app-version")
class AppVersionCtrl(private var appVersionService: AppVersionService) {
    @GetMapping
    fun state(@RequestParam version: String): ResponseEntity<Any> {
        val state = appVersionService.state(version)
        return ApiResBuilder.buildRes(data = state)
    }
}