package com.karumi.superhero.data.common

import arrow.core.Option
import arrow.core.nonFatalOrThrow
import arrow.fx.IO
import arrow.fx.extensions.io.applicativeError.raiseError
import arrow.fx.handleErrorWith
import com.karumi.superhero.domain.exceptions.DomainError
import java.util.logging.Level
import java.util.logging.Logger

inline fun <reified A> Throwable.logError(): IO<A> =
  IO.effect {
    Logger.getLogger(A::class.qualifiedName).log(Level.SEVERE, message, this)
  }.followedBy(IO.raiseError(nonFatalOrThrow()))

inline fun <E : DomainError, reified A> E.logEffect(noinline f: suspend () -> A): IO<A> =
  IO.effect(f).handleErrorWith(Throwable::logError).handleErrorWith { raiseError() }

inline infix fun <reified A, E : DomainError> (suspend () -> A).or(e: E): IO<A> =
  e.logEffect(this)

infix fun <E : DomainError, A> IO<Option<A>>.or(e: E): IO<A> =
  flatMap { it.fold({ IO.raiseError<A>(e) }, { IO.just(it) }) }