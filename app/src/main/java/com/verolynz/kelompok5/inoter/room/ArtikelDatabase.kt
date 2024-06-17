package com.verolynz.kelompok5.inoter.room

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.File

@Entity
data class ArtikelDatabase (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "judul")
    val name: String,

    @ColumnInfo(name = "deskripsi")
    val description: String,

    @ColumnInfo(name = "artikel_image")
    val image: File
): Parcelable {
    // Konstruktor sekunder untuk membuat objek ArtikelDatabase dari Parcel
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        File(parcel.readString()!!)
    )

    // Fungsi untuk menulis data objek ArtikelDatabase ke Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(image.path)
    }

    // Fungsi untuk mendeskripsikan jenis konten khusus yang ditangani oleh Parcelable
    override fun describeContents(): Int {
        return 0
    }

    // Objek pendamping untuk ArtikelDatabase yang berisi fungsi untuk membuat objek ArtikelDatabase dari Parcel dan Array
    companion object CREATOR : Parcelable.Creator<ArtikelDatabase> {
        // Fungsi untuk membuat objek ArtikelDatabase dari Parcel
        override fun createFromParcel(parcel: Parcel): ArtikelDatabase {
            return ArtikelDatabase(parcel)
        }

        // Fungsi untuk membuat array dari objek ArtikelDatabase
        override fun newArray(size: Int): Array<ArtikelDatabase?> {
            return arrayOfNulls(size)
        }
    }
}