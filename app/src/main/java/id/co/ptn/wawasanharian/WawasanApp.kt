package id.co.ptn.wawasanharian

import android.app.Application

class WawasanApp : Application() {

    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}
