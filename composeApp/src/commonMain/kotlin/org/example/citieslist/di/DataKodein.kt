package org.example.citieslist.di

import org.kodein.di.Kodein

val kodein = Kodein {
    import(dataModule)
    import(presentationModule)
}