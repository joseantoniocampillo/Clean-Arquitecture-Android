package es.jacampillo.avancedelcovid.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import es.jacampillo.avancedelcovid.ui.main.MainFragment.Companion.FALLECIDOS
import es.jacampillo.avancedelcovid.ui.main.MainFragment.Companion.FALLECIDOS_HOY
import es.jacampillo.avancedelcovid.ui.main.MainFragment.Companion.GRAVES
import es.jacampillo.avancedelcovid.ui.main.MainFragment.Companion.POSITIVOS
import es.jacampillo.avancedelcovid.ui.main.MainFragment.Companion.RECUPERADOS
import es.jacampillo.avancedelcovid.ui.main.MainFragment.Companion.TEST
import es.jacampillo.avancedelcovid.ui.main.MainFragment.Companion.TEST_POR_MILLON

@Dao
interface PaisesDao {

    @Query("select * from databasepais order by cases desc")
    fun getPaises(): LiveData<List<DatabasePais>>

    @Query("select * from databasepais order by deaths desc")
    fun getOrdenadosFallecidos(): LiveData<List<DatabasePais>>

    @Query("select * from databasepais order by todayDeaths desc")
    fun getOrdenFallecidosHoy(): LiveData<List<DatabasePais>>

    @Query("select * from databasepais order by recovered desc")
    fun getOrdenRecuperados(): LiveData<List<DatabasePais>>

    @Query("select * from databasepais order by critical desc")
    fun getOrdenGraves(): LiveData<List<DatabasePais>>

    @Query("select * from databasepais order by tests desc")
    fun getOrdenTest(): LiveData<List<DatabasePais>>

    @Query("select * from databasepais order by testsPerOneMillion desc")
    fun getOrdenTestPorMillon(): LiveData<List<DatabasePais>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPaises(paises: List<DatabasePais>)

    fun getFromDatabase(int: Int): LiveData<List<DatabasePais>> {
        return when (int) {
            FALLECIDOS -> getOrdenadosFallecidos()
            POSITIVOS -> getPaises()
            FALLECIDOS_HOY -> getOrdenFallecidosHoy()
            RECUPERADOS -> getOrdenRecuperados()
            GRAVES -> getOrdenGraves()
            TEST -> getOrdenTest()
            TEST_POR_MILLON -> getOrdenTestPorMillon()
            else -> getPaises()
        }
    }

}

@Database(entities = [DatabasePais::class], version = 1)
abstract class PaisesDatabase : RoomDatabase() {
    abstract val paisesDao: PaisesDao
}

private lateinit var INSTANCE: PaisesDatabase

fun getDatabase(context: Context): PaisesDatabase {
    synchronized(PaisesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                PaisesDatabase::class.java, "paises"
            ).build()
        }
    }
    return INSTANCE
}