package com.verolynz.kelompok5.inoter.data.remote

import java.time.LocalDateTime

data class AtletResponse(
    val name: String,
    val deskripsi: String,
    val tahun: String,
    val dibuat: String,
    val id: String,
    val CabangOlahragaId: String
)