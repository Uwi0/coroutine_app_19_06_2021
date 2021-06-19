package com.kakapo.coroutineapp

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.kakapo.coroutineapp.main.RefreshMainDataWork

class KotlinCoroutinesApp : Application(){

    override fun onCreate() {
        super.onCreate()
        setupWorkManagerJob()
    }

    private fun setupWorkManagerJob(){
        val workManagerConfiguration = Configuration.Builder()
            .setWorkerFactory(RefreshMainDataWork.Factory())
            .build()
        WorkManager.initialize(this, workManagerConfiguration)
    }
}