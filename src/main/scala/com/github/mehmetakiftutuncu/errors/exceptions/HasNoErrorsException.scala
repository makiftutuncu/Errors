package com.github.mehmetakiftutuncu.errors.exceptions

import com.github.mehmetakiftutuncu.errors.Maybe

/**
  * A custom exception to be thrown when trying to access the errors in a [[com.github.mehmetakiftutuncu.errors.Maybe]]
  * when it actually contains a value, not an [[com.github.mehmetakiftutuncu.errors.Errors]]
  *
  * @param maybe [[com.github.mehmetakiftutuncu.errors.Maybe]] on which the errors are tried to be accessed
  *
  * @tparam V Type of the value in [[com.github.mehmetakiftutuncu.errors.Maybe]]
  *
  * @author Mehmet Akif Tütüncü
  */
case class HasNoErrorsException[V](maybe: Maybe[V]) extends NoSuchElementException(s"""Maybe "${maybe.right.get}" wasn't an Errors but a value. Make sure you check it with "hasErrors" method first or use "maybeErrors" method to access the Errors!""")
