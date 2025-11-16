package app.theblu.cashflow.cs.zone.bootstrap.web

import app.theblu.cashflow.cs.batteries.rocket.res.ApiResBuilder
import app.theblu.cashflow.cs.zone.bootstrap.service.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/category")
class CategoryCtrl(private var categoryService: CategoryService) {

    @GetMapping
    fun getAll(): ResponseEntity<Any> {
        val list = categoryService.getAll()
        return ApiResBuilder.buildRes(list)
    }
}