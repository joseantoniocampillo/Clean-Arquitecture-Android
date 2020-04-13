package es.jacampillo.avancedelcovid

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration




class Applicacion: Application(){
    override fun onCreate() {
        super.onCreate()
        Realm.init(this);
        val config = RealmConfiguration.Builder()
            .name("covidrealm.realm")
            .schemaVersion(0)
            .build()
        Realm.setDefaultConfiguration(config)
    }
}