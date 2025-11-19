// en services/CategoriaService.kt
package com.example.camisas_api.services

import com.example.camisas_api.dtos.CategoriaDto
import com.example.camisas_api.dtos.CategoriaRequestDto
import com.example.camisas_api.mappers.CamisaMapper
import com.example.camisas_api.models.Categoria
import com.example.camisas_api.repositories.CategoriaRepository
import org.springframework.stereotype.Service

@Service
class CategoriaService(
    private val categoriaRepository: CategoriaRepository,
    private val camisaMapper: CamisaMapper // Reutilizamos el mapper
) {
    fun getAll(): List<CategoriaDto> {
        return categoriaRepository.findAll().map { camisaMapper.toCategoriaDto(it) }
    }

    fun create(request: CategoriaRequestDto): CategoriaDto {
        val nuevaCategoria = Categoria(nombre = request.nombre)
        val categoriaGuardada = categoriaRepository.save(nuevaCategoria)
        return camisaMapper.toCategoriaDto(categoriaGuardada)
    }
}
