package com.verolynz.kelompok5.inoter.utils
import androidx.room.TypeConverter
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
class Converters {

        private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

        @TypeConverter
        fun fromTimestamp(value: String?): LocalDateTime? {
            return value?.let {
                return LocalDateTime.parse(it, formatter)
            }
        }

        @TypeConverter
        fun dateToTimestamp(date: LocalDateTime?): String? {
            return date?.format(formatter)
        }

        @TypeConverter
        fun fromFile(file: File?): String? {
            // Mengembalikan path dari file jika file tidak null, jika null maka mengembalikan null
            return file?.path
        }

        // Fungsi ini mengubah String (path file) menjadi objek File
        @TypeConverter
        fun toFile(path: String?): File? {
            // Membuat objek File dari path jika path tidak null, jika null maka mengembalikan null
            return if (path != null) File(path) else null
        }
    }
