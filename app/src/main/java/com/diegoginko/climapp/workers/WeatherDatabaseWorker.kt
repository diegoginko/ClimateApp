package com.diegoginko.climapp.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.diegoginko.climapp.data.AppDatabase
import com.diegoginko.climapp.data.Ciudad
import kotlinx.coroutines.coroutineScope
import timber.log.Timber
import java.lang.Exception

class WeatherDatabaseWorker (
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            val database = AppDatabase.getInstance(context = applicationContext)
            //Primera ciudad (la actual que se va a ir actualizando)
            database.ciudadDao().insert(Ciudad(0L,"Buenos Aires","ar"))

            Result.success()
        }catch (ex : Exception) {
            Timber.e(ex, "Error inicializando la base")
            Result.failure()
        }
    }

}