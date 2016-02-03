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
    * @return Representation of this error
    */
  override def represent(): String = s"""{"name":"$name","when":$when}"""
}

/** A container object for some predefined [[com.github.mehmetakiftutuncu.errors.SimpleError]]s */
object SimpleError {
  def database       = SimpleError("database")
  def invalidData    = SimpleError("invalidData")
  def invalidRequest = SimpleError("invalidRequest")
  def requestFailed  = SimpleError("requestFailed")
  def timeout        = SimpleError("timeout")
  def authorization  = SimpleError("authorization")
  def authentication = SimpleError("authentication")
}
