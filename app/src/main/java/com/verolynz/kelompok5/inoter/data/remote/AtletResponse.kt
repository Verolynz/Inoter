package com.verolynz.kelompok5.inoter.data.remote

import java.time.LocalDateTime

data class AtletResponse(
    val name: String,
    val deskripsi: String,
    val tahun: LocalDateTime,
    val dibuat: LocalDateTime,
    val id: String
)