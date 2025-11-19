package com.example.camisas_api.dtos

// DTO para representar una categoría en las respuestas.
data class CategoriaDto(
    val id: Long,
    val nombre: String
)

data class CategoriaRequestDto(
    val nombre: String
)


// DTO para la respuesta al obtener una o varias camisas.
data class CamisaResponseDto(
    val id: Long,
    val marca: String,
    val modelo: String,
    val precio: Double,
    val categoria: CategoriaDto
)

// DTO para la petición de crear o actualizar una camisa.
data class CamisaRequestDto(
    val marca: String,
    val modelo: String,
    val precio: Double,
    val categoriaId: Long
)
