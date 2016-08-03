package com.github.mehmetakiftutuncu.errors.representation

import com.github.mehmetakiftutuncu.errors.base.ErrorBase

/**
  * A base trait for representing errors
  *
  * @tparam R Type of the error representation
  *
  * @author Mehmet Akif Tütüncü
  */
trait ErrorRepresenter[R] {
  /**
    * Represents given error
    *
    * @param error       Error to represent
    * @param includeWhen If set to true, when value of the error will be included in the representation
    *
    * @return Representation of given error
    */
  def represent(error: ErrorBase, includeWhen: Boolean): R

  /**
    * Represents all of given errors
    *
    * @param errors      Errors to represent
    * @param includeWhen If set to true, when value of the error will be included in the representation
    *
    * @return Representation of given errors
    */
  def represent(errors: List[ErrorBase], includeWhen: Boolean): R

  /**
    * Coverts given representation to a [[scala.Predef.String]]
    *
    * @param representation Representation to convert
    *
    * @return [[scala.Predef.String]] representation of given representation
    */
  def asString(representation: R): String
}
