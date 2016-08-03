package com.github.mehmetakiftutuncu.errors.exceptions

import com.github.mehmetakiftutuncu.errors.Maybe

/**
  * A custom exception to be thrown when trying to access the value in a [[com.github.mehmetakiftutuncu.errors.Maybe]]
  * when it actually contains [[com.github.mehmetakiftutuncu.errors.Errors]], not a value
  *
  * @param maybe [[com.github.mehmetakiftutuncu.errors.Maybe]] on which the value is tried to be accessed
  *
  * @tparam V Type of the value in [[com.github.mehmetakiftutuncu.errors.Maybe]]
  *
  * @author Mehmet Akif Tütüncü
  */
case class HasNoValueException[V](maybe: Maybe[V]) extends NoSuchElementException(s"""Maybe "${maybe.left.get}" wasn't a value but an Errors. Make sure you check it with "hasValue" method first or use "maybeValue" method to access the value!""")
