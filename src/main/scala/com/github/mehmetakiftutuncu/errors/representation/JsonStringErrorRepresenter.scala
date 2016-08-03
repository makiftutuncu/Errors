package com.github.mehmetakiftutuncu.errors.representation

import com.github.mehmetakiftutuncu.errors.base.ErrorBase

/**
  * An error representer for representing errors as Json formatted [[scala.Predef.String]]s,
  * since all errors represent themselves as Json formatted [[scala.Predef.String]]s by default,
  * this representer just calls
  *
  * {{{
  *   represent()
  * }}}
  *
  * on all errors.
  *
  * @author Mehmet Akif Tütüncü
  */
object JsonStringErrorRepresenter extends ErrorRepresenter[String] {
  /**
    * Represents given error as Json object formatted [[scala.Predef.String]]
    *
    * @param error       Error to represent
    * @param includeWhen If set to true, when value of the error will be included in the representation
    *
    * @return Representation of given error
    */
  override def represent(error: ErrorBase, includeWhen: Boolean): String = {
    error.represent(includeWhen)
  }

  /**
    * Represents all of given errors
    *
    * @param errors      Errors to represent
    * @param includeWhen If set to true, when value of the error will be included in the representation
    *
    * @return Representation of given errors
    */
  override def represent(errors: List[ErrorBase], includeWhen: Boolean): String = {
    errors.map(represent(_, includeWhen)).mkString("[", ",", "]")
  }

  /**
    * Coverts given representation to a [[scala.Predef.String]]
    *
    * @param representation Representation to convert
    *
    * @return [[scala.Predef.String]] representation of given representation, in this case just itself
    */
  override def asString(representation: String): String = {
    representation
  }
}
