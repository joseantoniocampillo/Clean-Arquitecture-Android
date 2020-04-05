package es.jacampillo.avancedelcovid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.jacampillo.avancedelcovid.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}
