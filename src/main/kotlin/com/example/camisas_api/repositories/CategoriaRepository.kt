package com.example.camisas_api.repositories

import com.example.camisas_api.models.Categoria // <-- Debe importar y usar Categoria
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
// La interfaz debe extender JpaRepository<Categoria, Long>
interface CategoriaRepository : JpaRepository<Categoria, Long>
