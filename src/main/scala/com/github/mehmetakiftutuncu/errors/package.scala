package com.github.mehmetakiftutuncu

import com.github.mehmetakiftutuncu.errors.exceptions.{HasNoValueException, HasNoErrorsException}

/**
  * A package object to provide common behaviour and utilities
  *
  * @author Mehmet Akif Tütüncü
  */
package object errors {
  /**
    * A type alias for [[scala.util.Either]] whose [[scala.util.Left]] is [[com.github.mehmetakiftutuncu.errors.Errors]]
    * and [[scala.util.Right]] is a value, useful for handling errors while accessing a value
    *
    * @tparam V Type of the value in [[com.github.mehmetakiftutuncu.errors.Maybe]]
    */
  type Maybe[V] = Either[Errors, V]

  /** An object to provide constructors via apply methods to [[com.github.mehmetakiftutuncu.errors.Maybe]] */
  object Maybe {
    /**
      * Creates a [[com.github.mehmetakiftutuncu.errors.Maybe]] containing given [[com.github.mehmetakiftutuncu.errors.Errors]]
      *
      * @param errors [[com.github.mehmetakiftutuncu.errors.Maybe]] to contain
      *
      * @tparam V Type of the value in [[com.github.mehmetakiftutuncu.errors.Maybe]]
      *
      * @return A [[com.github.mehmetakiftutuncu.errors.Maybe]] containing given Errors
      */
    def apply[V](errors: Errors): Maybe[V] = {
      Left.apply[Errors, V](errors)
    }

    /**
      * Creates a [[com.github.mehmetakiftutuncu.errors.Maybe]] containing given value
      *
      * @param value Value to contain
      *
      * @tparam V Type of the value in [[com.github.mehmetakiftutuncu.errors.Maybe]]
      *
      * @return A [[com.github.mehmetakiftutuncu.errors.Maybe]] containing given value
      */
    def apply[V](value: V): Maybe[V] = {
      Right.apply[Errors, V](value)
    }
  }

  /**
    * An implicit class to provide additional methods that can be called on [[com.github.mehmetakiftutuncu.errors.Maybe]]
    * instances
    *
    * @param maybe [[com.github.mehmetakiftutuncu.errors.Maybe]] instance on which the methods are being called
    *
    * @tparam V Type of the value in [[com.github.mehmetakiftutuncu.errors.Maybe]]
    */
  implicit class MaybeExtensions[V](maybe: Maybe[V]) {
    /**
      * Safely returns [[com.github.mehmetakiftutuncu.errors.Errors]] in [[com.github.mehmetakiftutuncu.errors.Maybe]]
      *
      * @return [[scala.Some]]([[com.github.mehmetakiftutuncu.errors.Errors]]) if they exist, [[scala.None]] otherwise
      */
    def maybeErrors: Option[Errors] = {
      maybe.left.toOption
    }

    /**
      * Safely returns the value in [[com.github.mehmetakiftutuncu.errors.Maybe]]
      *
      * @return [[scala.Some]](value) if it exists, [[scala.None]] otherwise
      */
    def maybeValue: Option[V] = {
      maybe.right.toOption
    }

    /**
      * Gets the [[com.github.mehmetakiftutuncu.errors.Errors]] in [[com.github.mehmetakiftutuncu.errors.Maybe]]
      *
      * @return [[com.github.mehmetakiftutuncu.errors.Errors]] in [[com.github.mehmetakiftutuncu.errors.Maybe]] if they exist
      */
    @throws[com.github.mehmetakiftutuncu.errors.exceptions.HasNoErrorsException[V]]
    def errors: Errors = {
      maybeErrors.getOrElse(throw HasNoErrorsException(maybe))
    }

    /**
      * Gets the value in [[com.github.mehmetakiftutuncu.errors.Maybe]]
      *
      * @return Value in [[com.github.mehmetakiftutuncu.errors.Maybe]] if it exists
      */
    @throws[com.github.mehmetakiftutuncu.errors.exceptions.HasNoValueException[V]]
    def value: V = {
      maybeValue.getOrElse(throw HasNoValueException(maybe))
    }

    /**
      * Checks whether [[com.github.mehmetakiftutuncu.errors.Maybe]] contains [[com.github.mehmetakiftutuncu.errors.Errors]]
      *
      * @return true if [[com.github.mehmetakiftutuncu.errors.Maybe]] contains [[com.github.mehmetakiftutuncu.errors.Errors]], false otherwise
      */
    def hasErrors: Boolean = {
      maybeErrors.isDefined
    }

    /**
      * Checks whether [[com.github.mehmetakiftutuncu.errors.Maybe]] contains a value
      *
      * @return true if [[com.github.mehmetakiftutuncu.errors.Maybe]] contains a value, false otherwise
      */
    def hasValue: Boolean = {
      maybeValue.isDefined
    }
  }
}
