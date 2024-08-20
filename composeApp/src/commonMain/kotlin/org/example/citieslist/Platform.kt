package org.example.citieslist


interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
