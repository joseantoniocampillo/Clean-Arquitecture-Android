package es.jacampillo.avancedelcovid.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PaisesDao {

    @Query("select * from databasepais")
    fun getPaises(): LiveData<List<DatabasePais>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPaises(paises: List<DatabasePais>)

}

@Database(entities = [DatabasePais::class], version = 1)
abstract class PaisesDatabase: RoomDatabase(){
    abstract val paisesDao: PaisesDao
}

private lateinit var INSTANCE: PaisesDatabase

fun getDatabase(context: Context): PaisesDatabase {
    synchronized(PaisesDatabase::class.java){
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                PaisesDatabase::class.java, "paises").build()
        }
    }
    return INSTANCE
}