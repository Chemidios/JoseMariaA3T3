package com.example.camisas_api.models

import jakarta.persistence.*

@Entity
@Table(name = "categorias")
data class Categoria(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true, nullable = false)
    val nombre: String
)
        