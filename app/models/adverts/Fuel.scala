package models.adverts

import play.api.libs.json.{JsValue, _}


/**
  * Created by conor on 2017-06-23.
  */
sealed trait Fuel
case object Gasoline extends Fuel
case object Diesel extends Fuel

object Fuel {
  implicit object FuelFormatter extends Format[Fuel] {
    def writes(fuel: Fuel) = fuel match {
      case Gasoline => Json.toJson("Gasoline")
      case Diesel => Json.toJson("Diesel")
    }

    def reads(json: JsValue) = {
      (json \ "fuel").as[String] match {
        case "gasoline" => JsSuccess(Gasoline)
        case "diesel" => JsSuccess(Diesel)
      }
    }
  }
}