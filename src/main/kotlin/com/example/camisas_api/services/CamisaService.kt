package com.example.camisas_api.services

import com.example.camisas_api.dtos.CamisaRequestDto
import com.example.camisas_api.dtos.CamisaResponseDto
import com.example.camisas_api.mappers.CamisaMapper
import com.example.camisas_api.models.Camisa
import com.example.camisas_api.repositories.CamisaRepository
import com.example.camisas_api.repositories.CategoriaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

// Excepciones personalizadas para un mejor manejo de errores en la API
class CategoriaNotFoundException(id: Long) : RuntimeException("Categoría con id $id no encontrada.")
class CamisaNotFoundException(id: Long) : RuntimeException("Camisa con id $id no encontrada.")


@Service
class CamisaService(
    // Inyección de dependencias (repositorios y mappers) mediante el constructor
    private val camisaRepository: CamisaRepository,
    private val categoriaRepository: CategoriaRepository,
    private val camisaMapper: CamisaMapper
) {

    /**
     * Obtiene todas las camisas de la base de datos y las mapea a su DTO de respuesta.
     */
    fun getAll(): List<CamisaResponseDto> {
        return camisaRepository.findAll().map { camisaMapper.toResponseDto(it) }
    }

    /**
     * Busca una camisa por su ID.
     * @throws CamisaNotFoundException si la camisa no se encuentra.
     */
    fun getById(id: Long): CamisaResponseDto {
        val camisa = camisaRepository.findByIdOrNull(id)
            ?: throw CamisaNotFoundException(id)
        return camisaMapper.toResponseDto(camisa)
    }

    /**
     * Crea una nueva camisa a partir de los datos de la petición.
     * @throws CategoriaNotFoundException si la categoría especificada no existe.
     */
    fun create(request: CamisaRequestDto): CamisaResponseDto {
        // 1. Buscamos la categoría por el ID que nos llega en el DTO.
        val categoria = categoriaRepository.findByIdOrNull(request.categoriaId)
            ?: throw CategoriaNotFoundException(request.categoriaId)

        // 2. Creamos la nueva entidad Camisa usando argumentos nombrados para claridad.
        val nuevaCamisa = Camisa(
            marca = request.marca,
            modelo = request.modelo,
            precio = request.precio,
            categoria = categoria
        )

        // 3. Guardamos la nueva camisa en la base de datos.
        val camisaGuardada = camisaRepository.save(nuevaCamisa)

        // 4. Mapeamos la entidad guardada a un DTO de respuesta y la devolvemos.
        return camisaMapper.toResponseDto(camisaGuardada)
    }

    /**
     * Actualiza una camisa existente.
     * @throws CamisaNotFoundException si la camisa a actualizar no existe.
     * @throws CategoriaNotFoundException si la nueva categoría especificada no existe.
     */
    fun update(id: Long, request: CamisaRequestDto): CamisaResponseDto {
        // 1. Nos aseguramos de que la camisa que se quiere actualizar existe.
        val camisaExistente = camisaRepository.findByIdOrNull(id)
            ?: throw CamisaNotFoundException(id)

        // 2. Verificamos que la nueva categoría también exista.
        val categoria = categoriaRepository.findByIdOrNull(request.categoriaId)
            ?: throw CategoriaNotFoundException(request.categoriaId)

        // 3. Creamos una copia actualizada del objeto camisa usando el .copy() de la data class.
        // El uso de argumentos nombrados aquí es la práctica correcta.
        val camisaActualizada = camisaExistente.copy(
            marca = request.marca,
            modelo = request.modelo,
            precio = request.precio,
            categoria = categoria
        )

        /* 4. Guardamos los cambios. El método save() actualiza si el ID ya existe.*/
        val camisaGuardada = camisaRepository.save(camisaActualizada)
        return camisaMapper.toResponseDto(camisaGuardada)
    }

    /**
     * Elimina una camisa por su ID.
     * @throws CamisaNotFoundException si la camisa a eliminar no existe.
     */
    fun delete(id: Long) {
        // Verificamos que la camisa exista antes de intentar borrarla para lanzar nuestra excepción.
        if (!camisaRepository.existsById(id)) {
            throw CamisaNotFoundException(id)
        }
        camisaRepository.deleteById(id)
    }
}
