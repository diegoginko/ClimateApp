package com.diegoginko.climapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.diegoginko.climapp.utilities.getValue
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CiudadDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var ciudadDao: CiudadDao
    private val CiudadA = Ciudad(2L,"Tucuman","ar")
    private val CiudadB = Ciudad(3L,"Santa Fe", "ar")
    private val CiudadC = Ciudad(4L,"Rio Negro", "ar")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        ciudadDao = database.ciudadDao()

        //Borro ciudades previas (menos la default)
        ciudadDao.deleteAllSafe()

        ciudadDao.insertAll(listOf(CiudadB,CiudadA,CiudadC))
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetCiudades() {
        val ciudadList = getValue(ciudadDao.getAllLiveData())
        Assert.assertThat(ciudadList.size, Matchers.equalTo(3))

        //asegurarse el orden por id
        Assert.assertThat(ciudadList[0], Matchers.equalTo(CiudadA))
        Assert.assertThat(ciudadList[1], Matchers.equalTo(CiudadB))
        Assert.assertThat(ciudadList[2], Matchers.equalTo(CiudadC))
    }

}