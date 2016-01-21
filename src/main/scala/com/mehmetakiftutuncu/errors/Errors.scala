package com.mehmetakiftutuncu.errors

import com.mehmetakiftutuncu.errors.base.ErrorBase
import com.mehmetakiftutuncu.errors.representation.{JsonStringRepresenter, RepresenterBase}

/**
  * An immutable error container to easily represent errors
  *
  * @param errors      A [[scala.collection.immutable.List]] of [[com.mehmetakiftutuncu.errors.base.ErrorBase]]s
  * @param representer Representer defining how errors are represented
  *
  * @tparam R Type of the representer
  *
  * @see [[com.mehmetakiftutuncu.errors.representation.RepresenterBase]]
  */
case class Errors[R](errors: List[ErrorBase], representer: RepresenterBase[R]) {
  /**
    * Adds given error to current errors
    *
    * @param error Error to add
    *
    * @return A copy of current errors with given error added
    */
  def +(error: ErrorBase): Errors[_] = copy(errors :+ error)

  /**
    * Adds given errors to the current errors
    *
    * @param otherErrors Errors to add
    *
    * @return A copy of current errors with given errors added
    */
  def addAll(otherErrors: ErrorBase*): Errors[_] = copy(errors ++ otherErrors)

  /**
    * Adds errors in another [[com.mehmetakiftutuncu.errors.Errors]] to the current errors
    *
    * @param otherErrors Errors to add
    *
    * @return A copy of current errors with given errors added
    */
  def ++(otherErrors: Errors[_]): Errors[_] = copy(errors ++ otherErrors.errors)

  /**
    * Removes given error from current errors if it exists
    *
    * @param error Error to remove
    *
    * @return A copy of current errors with given error removed
    */
  def -(error: ErrorBase): Errors[_] = copy(errors.filter(_ != error))

  /**
    * Removes given errors from the current errors
    *
    * @param otherErrors Errors to remove
    *
    * @return A copy of current errors with given errors removed
    */
  def removeAll(otherErrors: ErrorBase*): Errors[_] = copy(errors diff otherErrors)

  /**
    * Removes errors in another [[com.mehmetakiftutuncu.errors.Errors]] from the current errors
    *
    * @param otherErrors Errors to remove
    *
    * @return A copy of current errors with given errors remove
    */
  def --(otherErrors: Errors[_]): Errors[_] = copy(errors diff otherErrors.errors)

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
    * @see [[com.mehmetakiftutuncu.errors.representation.RepresenterBase]]
    */
  def represent = representer.represent(errors.toList)

  /**
    * Represents all of added errors as [[String]] using current representer
    *
    * @return String representation of all of added errors
    *
    * @see [[com.mehmetakiftutuncu.errors.representation.RepresenterBase]]
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
  def apply[R](representer: RepresenterBase[R]): Errors[R] = {
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
  def apply[R](representer: RepresenterBase[R], errors: ErrorBase*): Errors[R] = {
    new Errors(List(errors:_*), representer)
  }

  /**
    * Generates an empty [[com.mehmetakiftutuncu.errors.Errors]] with [[com.mehmetakiftutuncu.errors.representation.JsonStringRepresenter]]
    *
    * @return An empty [[com.mehmetakiftutuncu.errors.Errors]] with [[com.mehmetakiftutuncu.errors.representation.JsonStringRepresenter]]
    *
    * @see [[com.mehmetakiftutuncu.errors.Errors#empty()]]
    */
  def apply(): Errors[String] = {
    new Errors(List.empty[ErrorBase], JsonStringRepresenter)
  }

  /**
    * Generates an [[com.mehmetakiftutuncu.errors.Errors]] with [[com.mehmetakiftutuncu.errors.representation.JsonStringRepresenter]] and with all given errors added
    *
    * @param errors Errors to initially add
    *
    * @return An [[com.mehmetakiftutuncu.errors.Errors]] with [[com.mehmetakiftutuncu.errors.representation.JsonStringRepresenter]] and with all given errors added
    */
  def apply(errors: ErrorBase*): Errors[String] = {
    new Errors(List(errors:_*), JsonStringRepresenter)
  }

  /**
    * Generates an empty [[com.mehmetakiftutuncu.errors.Errors]] with [[com.mehmetakiftutuncu.errors.representation.JsonStringRepresenter]]
    *
    * @return An empty [[com.mehmetakiftutuncu.errors.Errors]] with [[com.mehmetakiftutuncu.errors.representation.JsonStringRepresenter]]
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
  def empty[R](representer: RepresenterBase[R]): Errors[R] = apply(representer)
}
