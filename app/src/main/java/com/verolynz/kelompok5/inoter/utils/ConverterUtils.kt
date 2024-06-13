package com.verolynz.kelompok5.inoter.utils

import com.verolynz.kelompok5.inoter.data.local.AtletEntity
import com.verolynz.kelompok5.inoter.data.local.COEntity
import com.verolynz.kelompok5.inoter.data.remote.AtletResponse
import com.verolynz.kelompok5.inoter.data.remote.COResponse
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun List<COResponse>.toListCOEntity(): List<COEntity> =
    map{
        COEntity(
            id = it.id.toInt(),
            name = it.name,
            deskripsi = it.deskripsi,
            dibuat = LocalDateTime.parse(it.dibuat.toString(), DateTimeFormatter.ISO_DATE_TIME)
        )
    }
fun List<AtletResponse>.toListAtletEntity(): List<AtletEntity> =
    map{
        AtletEntity(
            id = it.id.toInt(),
            name = it.name,
            deskripsi = it.deskripsi,
            tahun = LocalDateTime.parse(it.tahun.toString(), DateTimeFormatter.ISO_DATE_TIME),
            dibuat = LocalDateTime.parse(it.dibuat.toString(), DateTimeFormatter.ISO_DATE_TIME),
            idCO = it.CabangOlahragaId.toInt()
        )
    }

fun List<COEntity>.toListCOResponse() : List<COResponse> =
    map{
        COResponse(
            id = it.id.toString(),
            name = it.name,
            deskripsi = it.deskripsi,
            dibuat = it.dibuat

        )
    }
fun List<AtletEntity>.toListAtletResponse() : List<AtletResponse> =
    map{
        AtletResponse(
            id = it.id.toString(),
            name = it.name,
            deskripsi = it.deskripsi,
            dibuat = it.dibuat,
            tahun = it.tahun,
            CabangOlahragaId = it.idCO.toString()

        )
    }

fun COEntity.toCOResponse() : COResponse =
    COResponse(
        id = id.toString(),
        name = name,
        deskripsi = deskripsi,
        dibuat = dibuat
    )
fun AtletEntity.toAtletResponse() : AtletResponse =
    AtletResponse(
        id = id.toString(),
        name = name,
        deskripsi = deskripsi,
        dibuat = dibuat,
        tahun = tahun,
        CabangOlahragaId = idCO.toString()
    )
fun COResponse.toCOEntity() : COEntity =
    COEntity(
        id = id.toInt(),
        name = name,
        deskripsi = deskripsi,
        dibuat = dibuat
    )
fun AtletResponse.toAtletEntity() : AtletEntity =
    AtletEntity(
        id = id.toInt(),
        name = name,
        deskripsi = deskripsi,
        dibuat = dibuat,
        tahun = tahun,
        idCO = CabangOlahragaId.toInt()
    )

