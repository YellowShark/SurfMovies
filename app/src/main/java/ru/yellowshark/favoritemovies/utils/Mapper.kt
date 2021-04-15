package ru.yellowshark.favoritemovies.utils

interface Mapper<Dto, Domain> {
    fun toDomain(dto: Dto): Domain
    fun fromDomain(domain: Domain): Dto
}