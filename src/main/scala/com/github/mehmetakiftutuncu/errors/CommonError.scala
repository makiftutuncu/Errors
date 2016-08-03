package com.github.mehmetakiftutuncu.errors

import com.github.mehmetakiftutuncu.errors.base.ErrorBase

/**
  * A basic error implementation for most common cases
  *
  * @param name   Name of the error
  * @param reason Some reason as to why the error occurred, can be empty
  * @param data   Some data that caused the error, can be empty
  *
  * @author Mehmet Akif Tütüncü
  */
case class CommonError(name: String, reason: String = "", data: String = "") extends ErrorBase {
  /**
    * Sets a reason to this error
    *
    * @param reason Reason as to why the error occurred
    *
    * @return A copy of this error with given reason
    */
  def reason(reason: String): CommonError = {
    copy(reason = reason)
  }

  /**
    * Sets data to this error
    *
    * @param data Data that caused the error
    *
    * @return A copy of this error with given data
    */
  def data(data: String): CommonError = {
    copy(data = data)
  }

  /**
    * Represents this error as Json formatted String
    *
    * @param includeWhen If set to true, when value of the error will be included in the representation
    *
    * @return Representation of this error
    */
  override def represent(includeWhen: Boolean): String = {
    val nameRepresentation: String   = s""""name":"${name.replaceAll("\"", "\\\\\"")}""""
    val reasonRepresentation: String = if (reason.isEmpty) "" else s""""reason":"${reason.replaceAll("\"", "\\\\\"")}""""
    val dataRepresentation: String   = if (data.isEmpty)   "" else s""""data":"${data.replaceAll("\"", "\\\\\"")}""""
    val whenRepresentation: String   = if (!includeWhen)   "" else s""","when":$when"""

    val items: List[String]           = List(reasonRepresentation, dataRepresentation).filter(_.nonEmpty)
    val contentRepresentation: String = if (items.isEmpty) "" else items.mkString(",", ",", "")

    s"""{$nameRepresentation$contentRepresentation$whenRepresentation}"""
  }
}

/** A container object for some predefined [[com.github.mehmetakiftutuncu.errors.CommonError]]s */
object CommonError {
  def database: CommonError       = CommonError("database")
  def notFound: CommonError       = CommonError("notFound")
  def invalidData: CommonError    = CommonError("invalidData")
  def invalidRequest: CommonError = CommonError("invalidRequest")
  def requestFailed: CommonError  = CommonError("requestFailed")
  def timeout: CommonError        = CommonError("timeout")
  def authorization: CommonError  = CommonError("authorization")
  def authentication: CommonError = CommonError("authentication")
}
