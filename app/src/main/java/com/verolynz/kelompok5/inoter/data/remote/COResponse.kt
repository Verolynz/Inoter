package com.verolynz.kelompok5.inoter.data.remote

import java.time.LocalDateTime

data class COResponse(
    val name: String,
    val deskripsi: String,
    val dibuat: LocalDateTime,
    val id: Int
)
