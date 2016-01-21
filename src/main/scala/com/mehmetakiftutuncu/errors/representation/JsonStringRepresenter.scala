package com.mehmetakiftutuncu.errors.representation

import com.mehmetakiftutuncu.errors.base.ErrorBase
import com.mehmetakiftutuncu.errors.common.CommonError

/**
  * An error representer for representing errors as Json formatted [[String]]s
  *
  * @author Mehmet Akif Tütüncü
  */
object JsonStringRepresenter extends RepresenterBase[String] {
  /**
    * Represents given error as Json object formatted [[String]]
    *
    * @param error Error to represent
    *
    * @return Representation of given error
    */
  override def represent(error: ErrorBase): String = error match {
    case commonError: CommonError => {
      val content: String = commonError match {
        case CommonError(_, "", "")       => ""
        case CommonError(_, reason, "")   => s""","reason":"$reason""""
        case CommonError(_, "", data)     => s""","data":"$data""""
        case CommonError(_, reason, data) => s""","reason":"$reason","data":"$data""""
      }

      s"""{"name":"${commonError.name}"$content}"""
    }

    case _ => super.represent(error)
  }

  /**
    * Represents all of given errors as Json array formatted [[String]]
    *
    * @param errors Errors to represent
    *
    * @return Representation of given errors
    */
  override def represent(errors: List[ErrorBase]): String = {
    errors.map(represent).mkString("[", ",", "]")
  }

  /**
    * Coverts given representation to a [[String]]
    *
    * @param representation Representation to convert
    *
    * @return [[String]] representation of given representation, in this case just itself
    */
  override def asString(representation: String): String = representation
}
