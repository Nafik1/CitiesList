package org.example.citieslist.data.mapper


import org.example.citieslist.data.model.CityDto
import org.example.citieslist.domain.entity.City

fun CityDto.toEntity(): City = City(name,country)

