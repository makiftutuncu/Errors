package com.mehmetakiftutuncu.errors

import com.mehmetakiftutuncu.errors.base.ErrorBase
import com.mehmetakiftutuncu.errors.representation.{ErrorRepresenter, JsonStringErrorRepresenter}

/**
  * An immutable error container to easily represent errors
  *
  * @param errors      A [[scala.collection.immutable.List]] of [[com.mehmetakiftutuncu.errors.base.ErrorBase]]s
  * @param representer Representer defining how errors are represented
  *
  * @tparam R Type of the representer
  *
  * @see [[com.mehmetakiftutuncu.errors.representation.ErrorRepresenter]]
  */
case class Errors[R](errors: List[ErrorBase], representer: ErrorRepresenter[R]) {
  /**
    * Adds given error to current errors
    *
    * @param error Error to add
    *
    * @return A copy of current errors with given error added
    */
  def +(error: ErrorBase): Errors[R] = copy(errors :+ error)

  /**
    * Adds given errors to the current errors
    *
    * @param otherErrors Errors to add
    *
    * @return A copy of current errors with given errors added
    */
  def addAll(otherErrors: ErrorBase*): Errors[R] = copy(errors ++ otherErrors)

  /**
    * Adds errors in another [[com.mehmetakiftutuncu.errors.Errors]] to the current errors
    *
    * @param otherErrors Errors to add
    *
    * @return A copy of current errors with given errors added
    */
  def ++(otherErrors: Errors[R]): Errors[R] = copy(errors ++ otherErrors.errors)

  /**
    * Removes given error from current errors if it exists
    *
    * @param error Error to remove
    *
    * @return A copy of current errors with given error removed
    */
  def -(error: ErrorBase): Errors[R] = copy(errors.filter(_ != error))

  /**
    * Removes given errors from the current errors
    *
    * @param otherErrors Errors to remove
    *
    * @return A copy of current errors with given errors removed
    */
  def removeAll(otherErrors: ErrorBase*): Errors[R] = copy(errors diff otherErrors)

  /**
    * Removes errors in another [[com.mehmetakiftutuncu.errors.Errors]] from the current errors
    *
    * @param otherErrors Errors to remove
    *
    * @return A copy of current errors with given errors remove
    */
  def --(otherErrors: Errors[R]): Errors[R] = copy(errors diff otherErrors.errors)

  /**
    * Checks if there is no error added
    *
    * @return true if there is no error added
    *
    * @see [[com.mehmetakiftutuncu.errors.Errors#nonEmpty]]
    */
  def isEmpty: Boolean = errors.isEmpty

  /**
    * Checks if there is any error added
    *
    * @return true if there any error added
    *
    * @see [[com.mehmetakiftutuncu.errors.Errors#isEmpty]]
    * @see [[com.mehmetakiftutuncu.errors.Errors#hasErrors]]
    */
  def nonEmpty: Boolean = errors.nonEmpty

  /**
    * Checks if there is any error added
    *
    * @return true if there any error added
    *
    * @see [[com.mehmetakiftutuncu.errors.Errors#nonEmpty]]
    */
  def hasErrors: Boolean = nonEmpty

  /**
    * Returns number of errors added
    *
    * @return Number of errors added
    *
    * @see [[com.mehmetakiftutuncu.errors.Errors#numberOfErrors]]
    */
  def size: Int = errors.size

  /**
    * Returns number of errors added
    *
    * @return Number of errors added
    */
  def numberOfErrors: Int = size

  /**
    * Checks whether given error exists in current errors
    *
    * @param error Error to check
    *
    * @return true if given error exists in current errors, false otherwise
    */
  def contains(error: ErrorBase): Boolean = errors.contains(error)

  /**
    * Checks whether an error that satisfies given check exists in current errors
    *
    * @param check A check function from ErrorBase to Boolean
    *
    * @return true if an error that satisfies given check exists in current errors
    */
  def exists(check: (ErrorBase) => Boolean): Boolean = errors.exists(check)

  /**
    * Represents all of added errors using current representer
    *
    * @return Representation of all of added errors
    *
    * @see [[com.mehmetakiftutuncu.errors.representation.ErrorRepresenter]]
    */
  def represent = representer.represent(errors)

  /**
    * Represents all of added errors as [[String]] using current representer
    *
    * @return String representation of all of added errors
    *
    * @see [[com.mehmetakiftutuncu.errors.representation.ErrorRepresenter]]
    */
  override def toString: String = representer.asString(represent)
}

/**
  * Companion object to [[com.mehmetakiftutuncu.errors.Errors]]
  */
object Errors {
  /**
    * Generates an empty [[com.mehmetakiftutuncu.errors.Errors]] with given representer
    *
    * @param representer Representer to use when representing errors
    *
    * @tparam R Type of the representer
    *
    * @return An empty [[com.mehmetakiftutuncu.errors.Errors]] with given representer
    *
    * @see [[com.mehmetakiftutuncu.errors.Errors#empty]]
    */
  def apply[R](representer: ErrorRepresenter[R]): Errors[R] = {
    new Errors(List.empty[ErrorBase], representer)
  }

  /**
    * Generates an [[com.mehmetakiftutuncu.errors.Errors]] with given representer and with all given errors added
    *
    * @param representer Representer to use when representing errors
    * @param errors      Errors to initially add
    *
    * @tparam R Type of the representer
    *
    * @return An [[com.mehmetakiftutuncu.errors.Errors]] with given representer and with all given errors added
    */
  def apply[R](representer: ErrorRepresenter[R], errors: ErrorBase*): Errors[R] = {
    new Errors(List(errors:_*), representer)
  }

  /**
    * Generates an empty [[com.mehmetakiftutuncu.errors.Errors]] with [[com.mehmetakiftutuncu.errors.representation.JsonStringErrorRepresenter]]
    *
    * @return An empty [[com.mehmetakiftutuncu.errors.Errors]] with [[com.mehmetakiftutuncu.errors.representation.JsonStringErrorRepresenter]]
    *
    * @see [[com.mehmetakiftutuncu.errors.Errors#empty()]]
    */
  def apply(): Errors[String] = {
    new Errors(List.empty[ErrorBase], JsonStringErrorRepresenter)
  }

  /**
    * Generates an [[com.mehmetakiftutuncu.errors.Errors]] with [[com.mehmetakiftutuncu.errors.representation.JsonStringErrorRepresenter]] and with all given errors added
    *
    * @param errors Errors to initially add
    *
    * @return An [[com.mehmetakiftutuncu.errors.Errors]] with [[com.mehmetakiftutuncu.errors.representation.JsonStringErrorRepresenter]] and with all given errors added
    */
  def apply(errors: ErrorBase*): Errors[String] = {
    new Errors(List(errors: _*), JsonStringErrorRepresenter)
  }

  /**
    * Generates an empty [[com.mehmetakiftutuncu.errors.Errors]] with [[com.mehmetakiftutuncu.errors.representation.JsonStringErrorRepresenter]]
    *
    * @return An empty [[com.mehmetakiftutuncu.errors.Errors]] with [[com.mehmetakiftutuncu.errors.representation.JsonStringErrorRepresenter]]
    */
  def empty: Errors[String] = apply()

  /**
    * Generates an empty [[com.mehmetakiftutuncu.errors.Errors]] with given representer
    *
    * @param representer Representer to use when representing errors
    *
    * @tparam R Type of the representer
    *
    * @return An empty [[com.mehmetakiftutuncu.errors.Errors]] with given representer
    */
  def empty[R](representer: ErrorRepresenter[R]): Errors[R] = apply(representer)
}