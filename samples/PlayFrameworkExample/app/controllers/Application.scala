package controllers

import com.github.mehmetakiftutuncu.errors.{CommonError, Errors}
import play.api.http.ContentTypes
import play.api.mvc._

class Application extends Controller {
  def index = Action {
    Ok(views.html.index())
  }

  def divide(n1: Int, n2: Int) = Action {
    var errors = Errors.empty

    if (n2 == 0) {
      errors += CommonError.invalidData.reason("Cannot divide by 0!")
    }

    if (n1 < 0) {
      errors += CommonError.invalidData.reason("Dividing a negative number").data(n1.toString)
    }

    if (errors.hasErrors) {
      BadRequest(errors.represent(JsonErrorRepresenter)).as(ContentTypes.JSON)
    } else {
      Ok((n1 / n2).toString)
    }
  }
}
