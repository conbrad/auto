package models.adverts

import play.api.libs.json.{JsValue, _}


/**
  * Created by conor on 2017-06-23.
  */

sealed case class Fuel(value: String)

object Fuel {
  object Gasoline extends Fuel("gasoline")
  object Diesel extends Fuel("diesel")

  val fuelTypes: Seq[Fuel] = Seq(Gasoline, Diesel)

  def findByName(name: String): Option[Fuel] = {
    fuelTypes.find(fuelType => fuelType.value == name)
  }

  implicit object FuelFormatter extends Format[Fuel] {
    def writes(fuel: Fuel) = Json.toJson(fuel.value)

    def reads(json: JsValue) = {
      Fuel.findByName((json \ "fuel").as[String]) match {
        case Some(fuel) => JsSuccess(fuel)
        case _ => JsError("Unknown fuel type")
      }
    }
  }
}
