package com.karumi.superhero.domain.usecase

import arrow.fx.IO
import com.karumi.superhero.data.SuperHeroRepository
import com.karumi.superhero.data.common.or
import com.karumi.superhero.domain.exceptions.NotFound
import com.karumi.superhero.domain.model.SuperHero
import org.springframework.stereotype.Service

@Service
class GetSuperHeroById(
  val superHeroesRepository: SuperHeroRepository
) {
  operator fun invoke(id: String): IO<SuperHero> =
    superHeroesRepository[id] or NotFound
}