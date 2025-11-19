package com.example.camisas_api.controllers

import com.example.camisas_api.dtos.CategoriaDto
import com.example.camisas_api.dtos.CategoriaRequestDto
import com.example.camisas_api.services.CategoriaService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/categorias")
class CategoriaController(
    private val categoriaService: CategoriaService
) {

    @GetMapping("")
    fun getAllCategorias(): ResponseEntity<List<CategoriaDto>> {
        return ResponseEntity.ok(categoriaService.getAll())
    }

    @PostMapping("")
    fun createCategoria(@RequestBody request: CategoriaRequestDto): ResponseEntity<CategoriaDto> {
        val nuevaCategoria = categoriaService.create(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoria)
    }
}
