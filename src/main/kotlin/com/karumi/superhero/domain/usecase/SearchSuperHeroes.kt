package com.karumi.superhero.domain.usecase

import arrow.fx.IO
import com.karumi.superhero.data.SuperHeroRepository
import com.karumi.superhero.domain.model.SuperHero
import org.springframework.stereotype.Service

@Service
class SearchSuperHeroes(
  val superHeroesRepository: SuperHeroRepository
) {
  operator fun invoke(name: String): IO<List<SuperHero>> =
    superHeroesRepository.searchBy(name = name)
}