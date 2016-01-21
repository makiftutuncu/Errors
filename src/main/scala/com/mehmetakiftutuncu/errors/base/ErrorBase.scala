package com.mehmetakiftutuncu.errors.base

import com.mehmetakiftutuncu.errors.representation.RepresenterBase

/**
  * A base trait for errors
  *
  * @author Mehmet Akif Tütüncü
  */
trait ErrorBase {
  /**
    * Represents this error using given representer
    *
    * @param representer Representer to represent this error
    *
    * @tparam R Type of the representer
    *
    * @return Representation of this error
    */
  def represent[R](representer: RepresenterBase[R]): R = representer.represent(this)
}
