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
  def reason(reason: String): CommonError = copy(reason = reason)

  /**
    * Sets data to this error
    *
    * @param data Data that caused the error
    *
    * @return A copy of this error with given data
    */
  def data(data: String): CommonError = copy(data = data)

  /**
    * Represents this error as Json formatted String
    *
    * @return Representation of this error
    */
  override def represent(): String = {
    val reasonString = if (reason.isEmpty) "" else s""""reason":"$reason""""
    val dataString   = if (data.isEmpty)   "" else s""""data":"$data""""

    val items         = List(reasonString, dataString).filter(_.nonEmpty)
    val contentPrefix = if (items.isEmpty) "" else ","
    val content       = items.mkString(",")

    s"""{"name":"$name"$contentPrefix$content,"when":$when}"""
  }
}

/** A container object for some predefined [[com.github.mehmetakiftutuncu.errors.CommonError]]s */
object CommonError {
  def database       = CommonError("database")
  def invalidData    = CommonError("invalidData")
  def invalidRequest = CommonError("invalidRequest")
  def requestFailed  = CommonError("requestFailed")
  def timeout        = CommonError("timeout")
  def authorization  = CommonError("authorization")
  def authentication = CommonError("authentication")
}
