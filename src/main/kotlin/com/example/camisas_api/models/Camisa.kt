package com.example.camisas_api.models

import jakarta.persistence.*

@Entity@Table(name = "camisas")
data class Camisa(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val marca: String,
    val modelo: String,
    val precio: Double,

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    val categoria: Categoria
)
