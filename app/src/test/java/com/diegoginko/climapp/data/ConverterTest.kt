package com.diegoginko.climapp.data

import com.diegoginko.climapp.utilities.doubleToString
import com.diegoginko.climapp.utilities.dtToDow
import com.diegoginko.climapp.utilities.stringToDouble
import org.junit.Assert
import org.junit.Test

class ConverterTest {

    @Test
    fun dtToDowTest(){
        Assert.assertEquals("DOMINGO", dtToDow(1596942000000).trim().toUpperCase())
        Assert.assertEquals("LUNES", dtToDow(1597076326149).trim().toUpperCase())
    }

    @Test
    fun doubleToStringTest(){
        val numero = 2.0
        val string = doubleToString(numero)
        Assert.assertEquals("2.0", string)
    }

    @Test
    fun stringtoDoubleTest(){
        val string = "2.0"
        val numero = stringToDouble(string)
        Assert.assertEquals(2.0, numero, 0.1)
    }


}