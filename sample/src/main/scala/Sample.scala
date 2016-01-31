import com.mehmetakiftutuncu.errors.{CommonError, Errors}

import scala.util.Random

/**
  * A sample object to demonstrate the usage of Errors library
  *
  * @author Mehmet Akif Tütüncü
  */
object Sample {
  def main(args: Array[String]) {
    val finalErrors: Errors = (1 to 5).foldLeft(Errors.empty) {
      case (errors, i) =>
        errors ++ isDigit(Random.nextInt(15))
    }

    if (finalErrors.nonEmpty) {
      println("Found these errors: " + finalErrors.toString)
    } else {
      println("Found no errors!")
    }

    val found11: Boolean = finalErrors.exists {
      case CommonError(_, _, "11") => true
      case _                       => false
    }

    if (found11) {
      println("There was an error about number 11 among errors!")
    } else {
      println("There were no errors about number 11 among errors!")
    }
  }

  def isDigit(number: Int): Errors = {
    if (number >= 0 && number < 10) {
      Errors.empty
    } else {
      Errors(CommonError.invalidData.reason("Value was not a digit!").data(number.toString))
    }
  }
}
