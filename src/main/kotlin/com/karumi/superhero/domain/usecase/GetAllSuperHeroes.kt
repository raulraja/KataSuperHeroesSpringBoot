package com.karumi.superhero.domain.usecase

import arrow.fx.IO
import com.karumi.superhero.data.SuperHeroRepository
import com.karumi.superhero.domain.model.SuperHero
import org.springframework.stereotype.Service

@Service
class GetAllSuperHeroes(
  val superHeroesRepository: SuperHeroRepository
) {
  operator fun invoke(): IO<List<SuperHero>> =
    superHeroesRepository.getAll()
}