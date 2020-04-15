package es.jacampillo.avancedelcovid.di

import es.jacampillo.avancedelcovid.repo.HelloRepository
import es.jacampillo.avancedelcovid.repo.HelloRepositoryImpl
import es.jacampillo.avancedelcovid.ui.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
//import org.koin.androidx.viewmodel.compat.ScopeCompat.viewModel
//import org.koin.androidx.viewmodel.ext.android.viewModel

import org.koin.dsl.module

val appModule = module {

    // single instance of HelloRepository
    //single<HelloRepository> { HelloRepositoryImpl() }

    // MyViewModel ViewModel
    viewModel { MainViewModel(androidContext()) }
}