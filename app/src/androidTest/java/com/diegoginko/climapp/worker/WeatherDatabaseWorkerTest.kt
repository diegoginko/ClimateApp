package com.diegoginko.climapp.worker

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.ListenableWorker
import androidx.work.WorkManager
import androidx.work.testing.TestListenableWorkerBuilder
import com.diegoginko.climapp.workers.WeatherDatabaseWorker
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class WeatherDatabaseWorkerTest {
    private lateinit var context: Context
    private lateinit var workManager: WorkManager

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        workManager = WorkManager.getInstance(context)
    }

    @Test
    fun testRefreshMainDataWork() {
        // Get the ListenableWorker
        val worker = TestListenableWorkerBuilder<WeatherDatabaseWorker>(context).build()

        // Start the work synchronously
        val result = worker.startWork().get()

        Assert.assertThat(result, CoreMatchers.`is`(ListenableWorker.Result.success()))
    }
}