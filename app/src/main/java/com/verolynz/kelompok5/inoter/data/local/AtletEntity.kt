package com.verolynz.kelompok5.inoter.data.local

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity("atlet",
    foreignKeys = [
        ForeignKey(
            entity = COEntity::class,
            parentColumns = ["id"],
            childColumns = ["idCO"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class AtletEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val deskripsi: String,
    val tahun: LocalDateTime,
    val dibuat: LocalDateTime,
    val idCO: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        LocalDateTime.parse(parcel.readString()!!, DateTimeFormatter.ISO_DATE_TIME),
        LocalDateTime.parse(parcel.readString()!!, DateTimeFormatter.ISO_DATE_TIME),
        parcel.readInt()

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(deskripsi)
        parcel.writeString(dibuat.format(DateTimeFormatter.ISO_DATE_TIME))
        parcel.writeString(dibuat.format(DateTimeFormatter.ISO_DATE_TIME))
        parcel.readInt()
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AtletEntity> {
        override fun createFromParcel(parcel: Parcel): AtletEntity {
            return AtletEntity(parcel)
        }

        override fun newArray(size: Int): Array<AtletEntity?> {
            return arrayOfNulls(size)
        }
    }
}