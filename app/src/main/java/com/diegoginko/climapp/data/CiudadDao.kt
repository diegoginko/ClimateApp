package com.diegoginko.climapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CiudadDao {
    @Insert
    fun insert(ciudad: Ciudad) : Long

    @Update
    fun update(ciudad: Ciudad) : Int

    @Update
    fun updates(ciudades: List<Ciudad>) : Int

    @Delete
    fun delete(ciudad: Ciudad)

    @Query("SELECT * from ciudades")
    fun getAll(): List<Ciudad>

    @Query("SELECT * from ciudades ORDER BY ciudadId")
    fun getAllLiveData(): LiveData<List<Ciudad>>

    @Query("SELECT * from ciudades WHERE ciudadId = :ciudadId" )
    fun getCiudad(ciudadId : Long) : Ciudad

    @Query("SELECT * from ciudades LIMIT 1" )
    fun getPrimeraCiudad() : Ciudad

    @Query("DELETE FROM ciudades")
    fun deleteAll()

    @Query("DELETE FROM ciudades WHERE ciudadId != :ciudadId")
    fun deleteAllSafe(ciudadId: Long = 1L)

    @Query("DELETE FROM ciudades WHERE ciudadId = :idCiudad")
    fun deleteById(idCiudad: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(ciudad: List<Ciudad>)

    fun insertOrUpdate(ciudad: Ciudad) : Boolean{
        var retorno = false

        if(ciudad.ciudadId == 0L){
            //Si no tiene Id asignado la inserto
            if(insert(ciudad) != 0L){
                retorno = true
            }
        }else{
            //Si tiene id me fijo si ya existe
            if(getCiudad(ciudad.ciudadId) != null){
                //Ya existe, updatear
                if(update(ciudad) != 0){
                    retorno = true
                }
            }else{
                //No existe, insertar
                if (insert(ciudad) != 0L){
                    retorno = true
                }
            }
        }

        return retorno
    }
}