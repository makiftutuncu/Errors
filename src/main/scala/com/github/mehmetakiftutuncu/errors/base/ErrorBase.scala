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
    * @see [[System#currentTimeMillis]]
    */
  val when: Long = System.currentTimeMillis()

  /**
    * Represents this error as Json formatted String
    *
    * @return Representation of this error
    *
    * @see [[com.github.mehmetakiftutuncu.errors.representation.JsonStringErrorRepresenter]]
    */
  def represent(): String

  /**
    * Represents this error using given representer
    *
    * @param representer A [[com.github.mehmetakiftutuncu.errors.representation.ErrorRepresenter]] to represent this error
    *
    * @tparam R Type of the error representation
    *
    * @return Representation of this error
    */
  def represent[R](representer: ErrorRepresenter[R]): R = representer.represent(this)
}
