package com.verolynz.kelompok5.inoter.data.local

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity("atlet",
    foreignKeys = [
        ForeignKey(
            entity = COEntity::class,
            parentColumns = ["id"],
            childColumns = ["idCO"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index("idCO")]
)
data class AtletEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val deskripsi: String,
    val tahun: String,
    val dibuat: String,
    val idCO: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(deskripsi)
        parcel.writeString(tahun)
        parcel.writeString(dibuat)
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