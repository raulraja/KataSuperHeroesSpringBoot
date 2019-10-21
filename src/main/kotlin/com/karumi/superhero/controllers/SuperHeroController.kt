package com.karumi.superhero.controllers

import com.karumi.superhero.domain.model.NewSuperHero
import com.karumi.superhero.domain.model.SuperHero
import com.karumi.superhero.domain.usecase.AddSuperHero
import com.karumi.superhero.domain.usecase.GetAllSuperHeroes
import com.karumi.superhero.domain.usecase.GetSuperHeroById
import com.karumi.superhero.domain.usecase.SearchSuperHeroes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SuperHeroController(
  val getAllSuperHeroes: GetAllSuperHeroes,
  val searchSuperHeroes: SearchSuperHeroes,
  val getSuperHeroById: GetSuperHeroById,
  val addSuperHero: AddSuperHero
) {

  @RequestMapping("/superhero")
  suspend fun getSuperHeroesEndpoint(
    @RequestParam(name = "name", required = false) name: String?
  ): List<SuperHero> =
    if (name == null) getAllSuperHeroes().suspended()
    else searchSuperHeroes(name).suspended()

  @RequestMapping("/superhero/{id}")
  suspend fun getSuperHeroByIdEndpoint(@PathVariable("id") superHeroId: String): SuperHero =
    getSuperHeroById(superHeroId).suspended()

  @PostMapping("/superhero")
  suspend fun postSuperHeroEndpoint(
    @RequestBody newSuperHero: NewSuperHero
  ): ResponseEntity<SuperHero> =
    addSuperHero(newSuperHero).map { ResponseEntity(it, HttpStatus.CREATED) }.suspended()
}