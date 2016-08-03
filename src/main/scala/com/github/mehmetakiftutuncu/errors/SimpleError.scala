package com.github.mehmetakiftutuncu.errors

import com.github.mehmetakiftutuncu.errors.base.ErrorBase

/**
  * A simple error implementation for really simple and trivial cases
  *
  * @param name Name of the error
  *
  * @author Mehmet Akif Tütüncü
  */
case class SimpleError(name: String) extends ErrorBase {
  /**
    * Represents this error as Json formatted String
    *
    * @param includeWhen If set to true, when value of the error will be included in the representation
    *
    * @return Representation of this error
    */
  override def represent(includeWhen: Boolean): String = {
    val nameRepresentation: String = s""""name":"${name.replaceAll("\"", "\\\\\"")}""""
    val whenRepresentation: String = if (!includeWhen) "" else s""","when":$when"""

    s"""{$nameRepresentation$whenRepresentation}"""
  }
}

/** A container object for some predefined [[com.github.mehmetakiftutuncu.errors.SimpleError]]s */
object SimpleError {
  def database: SimpleError       = SimpleError("database")
  def notFound: SimpleError       = SimpleError("notFound")
  def invalidData: SimpleError    = SimpleError("invalidData")
  def invalidRequest: SimpleError = SimpleError("invalidRequest")
  def requestFailed: SimpleError  = SimpleError("requestFailed")
  def timeout: SimpleError        = SimpleError("timeout")
  def authorization: SimpleError  = SimpleError("authorization")
  def authentication: SimpleError = SimpleError("authentication")
}
