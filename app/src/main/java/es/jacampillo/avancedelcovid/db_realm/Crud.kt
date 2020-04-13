package es.jacampillo.avancedelcovid.db_realm

import es.jacampillo.avancedelcovid.database.asListDatabasePaisesRealM
import es.jacampillo.avancedelcovid.models_api_response.Pais
import io.realm.Realm
import io.realm.RealmList
import io.realm.kotlin.createObject

val realm = Realm.getDefaultInstance()

fun ingresarPaises(listpais: List<Pais>){
    listpais.asListDatabasePaisesRealM(realm)
}