package com.github.mehmetakiftutuncu.errors.base

import com.github.mehmetakiftutuncu.errors.representation.ErrorRepresenter

/**
  * A base trait for errors
  *
  * @author Mehmet Akif Tütüncü
  */
trait ErrorBase {
  /**
    * Timestamp of the time when this error occurred.
    *
    * @see [[java.lang.System#currentTimeMillis]]
    */
  val when: Long = System.currentTimeMillis()

  /**
    * Represents this error as Json formatted String
    *
    * @param includeWhen If set to true, when value of the error will be included in the representation
    *
    * @return Representation of this error
    *
    * @see [[com.github.mehmetakiftutuncu.errors.representation.JsonStringErrorRepresenter]]
    */
  def represent(includeWhen: Boolean): String

  /**
    * Represents this error using given representer
    *
    * @param representer A [[com.github.mehmetakiftutuncu.errors.representation.ErrorRepresenter]] to represent this error
    * @param includeWhen If set to true, when value of the error will be included in the representation
    *
    * @tparam R Type of the error representation
    *
    * @return Representation of this error
    */
  def represent[R](representer: ErrorRepresenter[R], includeWhen: Boolean): R = representer.represent(this, includeWhen)
}
