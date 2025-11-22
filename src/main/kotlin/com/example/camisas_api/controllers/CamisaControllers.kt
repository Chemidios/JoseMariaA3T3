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
    fun createCamisa(@RequestBody request: CamisaRequestDto): ResponseEntity<CamisaResponseDto> {        // Bloque de depuración para forzar que el error se muestre en los logs
        return try {
            println("API: INTENTANDO CREAR CAMISA -> $request") // Mensaje de diagnóstico

            val nuevaCamisa = camisaService.create(request)

            println("API: CAMISA CREADA CON ÉXITO -> $nuevaCamisa") // Mensaje de éxito

            ResponseEntity.status(HttpStatus.CREATED).body(nuevaCamisa)

        } catch (e: Exception) {
            // Si algo dentro del 'try' falla, el programa salta aquí
            println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
            println("!!!!!!!!!!!!! ERROR CAPTURADO EN POST !!!!!!!!!!!!!!!")
            println("CLASE DEL ERROR: ${e.javaClass.simpleName}")
            println("MENSAJE DEL ERROR: ${e.message}")
            println("STACK TRACE COMPLETO (LA PISTA DEFINITIVA):")
            e.printStackTrace() // Este comando imprime el informe completo del error en los logs
            println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")

            // Devolvemos el mismo error 500 a Postman, pero ahora sabremos la causa
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
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
