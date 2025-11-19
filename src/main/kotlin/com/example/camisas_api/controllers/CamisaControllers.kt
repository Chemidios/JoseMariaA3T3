package com.example.camisas_api.controllers

import com.example.camisas_api.dtos.CamisaRequestDto
import com.example.camisas_api.dtos.CamisaResponseDto
import com.example.camisas_api.services.CamisaService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/camisas") // La URL base para todos los endpoints de este controlador
class CamisaController(
    private val camisaService: CamisaService
) {

    @GetMapping("")
    fun getAllCamisas(): ResponseEntity<List<CamisaResponseDto>> {
        val camisas = camisaService.getAll()
        return ResponseEntity.ok(camisas)
    }

    @GetMapping("/{id}")
    fun getCamisaById(@PathVariable id: Long): ResponseEntity<CamisaResponseDto> {
        val camisa = camisaService.getById(id)
        return ResponseEntity.ok(camisa)
    }

    @PostMapping("")
    fun createCamisa(@RequestBody request: CamisaRequestDto): ResponseEntity<CamisaResponseDto> {
        val nuevaCamisa = camisaService.create(request)
        // Devolvemos un código 201 Created y la camisa creada en el body.
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCamisa)
    }

    @PutMapping("/{id}")
    fun updateCamisa(
        @PathVariable id: Long,
        @RequestBody request: CamisaRequestDto
    ): ResponseEntity<CamisaResponseDto> {
        val camisaActualizada = camisaService.update(id, request)
        return ResponseEntity.ok(camisaActualizada)
    }

    @DeleteMapping("/{id}")
    fun deleteCamisa(@PathVariable id: Long): ResponseEntity<Unit> {
        camisaService.delete(id)
        // Devolvemos un código 204 No Content, que indica éxito sin devolver cuerpo.
        return ResponseEntity.noContent().build()
    }
}
