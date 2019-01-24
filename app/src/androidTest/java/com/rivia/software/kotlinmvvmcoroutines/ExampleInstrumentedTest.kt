package com.rivia.software.kotlinmvvmcoroutines

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    //TODO: Hacer test con ESPRESSO Y MOCKITO PARA SIMULAR LA RESPUESTA DE LA LLAMADA AL SERVICIO


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.rivia.software.kotlinmvvmcoroutines", appContext.packageName)
    }
}
