package eu.tutorials.a7minutesworkout

import android.app.Application
import kotlinx.coroutines.InternalCoroutinesApi

class WorkOutApp:Application() {
    @InternalCoroutinesApi
    val db by lazy {
        HistoryDatabase.getInstance(this)
    }

}