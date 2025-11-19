package com.example.camisas_api.repositories

import com.example.camisas_api.models.Camisa
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CamisaRepository : JpaRepository<Camisa, Long>
    