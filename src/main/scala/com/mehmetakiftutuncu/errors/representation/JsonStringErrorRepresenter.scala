package com.mehmetakiftutuncu.errors.representation

import com.mehmetakiftutuncu.errors.base.ErrorBase

/**
  * An error representer for representing errors as Json formatted [[String]]s,
  * since all errors represent themselves as Json formatted [[String]]s by default,
  * this representer just calls {{{represent()}}} on all errors.
  *
  * @author Mehmet Akif Tütüncü
  */
object JsonStringErrorRepresenter extends ErrorRepresenter[String] {
  /**
    * Represents given error as Json object formatted [[String]]
    *
    * @param error Error to represent
    *
    * @return Representation of given error
    */
  override def represent(error: ErrorBase): String = error.represent()

  /**
    * Represents all of given errors
    *
    * @param errors Errors to represent
    *
    * @return Representation of given errors
    */
  override def represent(errors: List[ErrorBase]): String = errors.map(represent).mkString("[", ",", "]")

  /**
    * Coverts given representation to a [[String]]
    *
    * @param representation Representation to convert
    *
    * @return [[String]] representation of given representation, in this case just itself
    */
  override def asString(representation: String): String = representation
}
