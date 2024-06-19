package com.verolynz.kelompok5.inoter.data.local

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity("cabang_olahraga")
data class COEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val deskripsi: String,
    val dibuat: String

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(deskripsi)
        parcel.writeString(dibuat)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<COEntity> {
        override fun createFromParcel(parcel: Parcel): COEntity {
            return COEntity(parcel)
        }

        override fun newArray(size: Int): Array<COEntity?> {
            return arrayOfNulls(size)
        }
    }
}