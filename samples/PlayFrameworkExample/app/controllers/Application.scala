package controllers

import com.github.mehmetakiftutuncu.errors.{CommonError, Errors, Maybe}
import play.api.http.ContentTypes
import play.api.mvc._

class Application extends Controller {
  def index = Action {
    Ok(views.html.index())
  }

  def divide(n1: Int, n2: Int) = Action {
    val maybeResult: Maybe[Int] = performDivision(n1, n2)

    if (maybeResult.hasErrors) {
      BadRequest(maybeResult.errors.represent(JsonErrorRepresenter)).as(ContentTypes.JSON)
    } else {
      Ok(maybeResult.value.toString)
    }
  }

  private def performDivision(n1: Int, n2: Int) = Maybe[Int] {
    val errors: Errors = {
      if (n2 == 0) {
        Errors(CommonError.invalidData.reason("Cannot divide by 0!"))
      } else {
        Errors.empty
      } ++ if (n1 < 0) {
        CommonError.invalidData.reason("Dividing a negative number").data(n1.toString)
      } else {
        Errors.empty
      }
    }

    if (errors.hasErrors) {
      Maybe(errors)
    } else {
      Maybe(n1 / n2)
    }
  }
}
