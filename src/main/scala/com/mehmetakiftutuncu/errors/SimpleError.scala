package com.mehmetakiftutuncu.errors

import com.mehmetakiftutuncu.errors.base.ErrorBase

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
