// ktlint-disable filename
package com.karumi.superhero.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

sealed class DomainError(msg: String) : RuntimeException(msg)

@ResponseStatus(HttpStatus.NOT_FOUND)
object NotFound : DomainError("Superhero not found")

object DbStorageError : DomainError("Database connection error")