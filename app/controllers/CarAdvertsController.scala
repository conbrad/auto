package controllers

import play.api.libs.json.Json.toJson
import play.api.mvc.{Action, Controller}

/**
  * Created by conor on 2017-06-23.
  */
class CarAdvertsController extends Controller {
  def test = Action {
    Ok(toJson("Test"))
  }
}
