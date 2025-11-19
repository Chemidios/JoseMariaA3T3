package com.example.camisas_api.mappers

import com.example.camisas_api.dtos.CamisaResponseDto
import com.example.camisas_api.dtos.CategoriaDto
import com.example.camisas_api.models.Camisa
import com.example.camisas_api.models.Categoria
import org.springframework.stereotype.Component

// La anotación @Component le dice a Spring que cree una instancia de esta clase
// para que podamos inyectarla en otros sitios, como en el Service.
@Component
class CamisaMapper {

    // Convierte una entidad Categoria a su DTO correspondiente.
    fun toCategoriaDto(categoria: Categoria): CategoriaDto {
        return CategoriaDto(
            id = categoria.id,
            nombre = categoria.nombre
        )
    }

    // Convierte una entidad Camisa a su DTO de respuesta.
    // Reutiliza el mapper de categoría para el objeto anidado.
    fun toResponseDto(camisa: Camisa): CamisaResponseDto {
        return CamisaResponseDto(
            id = camisa.id,
            marca = camisa.marca,
            modelo = camisa.modelo,
            precio = camisa.precio,
            categoria = toCategoriaDto(camisa.categoria)
        )
    }
}
