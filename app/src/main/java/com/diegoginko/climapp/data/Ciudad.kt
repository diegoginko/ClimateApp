package com.diegoginko.climapp.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "ciudades")
data class Ciudad(
    @PrimaryKey(autoGenerate = true)
    var ciudadId : Long = 0L,
    @ColumnInfo(name = "nombre")
    var nombre : String = "",
    @ColumnInfo(name = "codigo_pais")
    var codigoPais : String = "",
    @ColumnInfo(name = "localidad")
    var localidad : String = "",
    @ColumnInfo(name = "municipio")
    var municipio : String = "",
    @ColumnInfo(name = "temperatura")
    var temp : Double = 0.0,
    @ColumnInfo(name = "temperatura_minima")
    var tempMin : Double = 0.0,
    @ColumnInfo(name = "temperatura_maxima")
    var tempMax : Double = 0.0,
    @ColumnInfo(name = "sensacion_termica")
    var sensTemp : Double = 0.0,
    @ColumnInfo(name = "descripcion_clima")
    var descripcionClima : String = "01d",
    @ColumnInfo(name = "icono_clima")
    var iconoClima : String = "01d"
): Parcelable