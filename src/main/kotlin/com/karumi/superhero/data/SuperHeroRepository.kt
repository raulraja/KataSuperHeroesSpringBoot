package com.karumi.superhero.data

import arrow.core.Option
import arrow.core.toOption
import arrow.fx.IO
import com.karumi.superhero.data.common.or
import com.karumi.superhero.data.model.mapToDomain
import com.karumi.superhero.data.model.mapToSuperHeroEntity
import com.karumi.superhero.domain.exceptions.DbStorageError
import com.karumi.superhero.domain.model.NewSuperHero
import com.karumi.superhero.domain.model.SuperHero
import org.springframework.stereotype.Repository

@Repository
class SuperHeroRepository(
  val superHeroStorage: SuperHeroDataSource
) {

  operator fun get(id: String): IO<Option<SuperHero>> =
    suspend {
      superHeroStorage.findById(id.toLong()).orElse(null).toOption().map { it.mapToDomain() }
    } or DbStorageError

  fun addSuperHero(newSuperHero: NewSuperHero): IO<SuperHero> =
    suspend {
      val superHeroEntity = newSuperHero.mapToSuperHeroEntity()
      superHeroStorage.save(superHeroEntity).mapToDomain()
    } or DbStorageError

  fun getAll(): IO<List<SuperHero>> =
    suspend {
      superHeroStorage.findAll().map { it.mapToDomain() }
    } or DbStorageError

  fun searchBy(name: String): IO<List<SuperHero>> =
    suspend {
      superHeroStorage.findByNameContainingIgnoreCase(name).map { it.mapToDomain() }
    } or DbStorageError
}