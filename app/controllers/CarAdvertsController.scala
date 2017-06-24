package controllers

import java.util.{Date, UUID}
import javax.inject.Inject

import models.adverts.{CarAdvert, Diesel, Fuel, Gasoline}
import play.api.libs.json._
import play.api.libs.json.Json.toJson
import play.api.mvc.{Action, Controller}
import services.CarAdvertService

/**
  * Created by conor on 2017-06-23.
  */
class CarAdvertsController @Inject() (carAdvertService: CarAdvertService) extends Controller {
  def test = Action {
    Ok(toJson("Test"))
  }

  def createAdvert = Action { request =>
    val json: JsValue = request.body.asJson.get
    val title: String = getTitle(json)
    val fuel: Fuel = getFuel(json)
    val price: Int = getPrice(json)
    val isNew: Boolean = getIsNew(json)
    val mileage: Option[Int] = getMileage(json)
    val firstRegistration: Option[Date] = getFirstRegistration(json)
    val newCarAdvert = CarAdvert.createNew(title, fuel, price, isNew, mileage, firstRegistration)

    println(json)
    Ok(Json.toJson(newCarAdvert))
  }

  def updateAdvert = Action { request =>
    val json: JsValue = request.body.asJson.get
    val id: UUID = getUUID(json)
    val title: String = getTitle(json)
    val fuel: Fuel = getFuel(json)
    val price: Int = getPrice(json)
    val isNew: Boolean = getIsNew(json)
    val mileage: Option[Int] = getMileage(json)
    val firstRegistration: Option[Date] = getFirstRegistration(json)
    val updatedAdvert = CarAdvert.createNew(title, fuel, price, isNew, mileage, firstRegistration)

    if(carAdvertService.updateAdvert(updatedAdvert)) {
      Ok("Updated")
    } else {
      Ok("Update failed")
    }
  }

  def getAllAdverts = Action { request =>
    Ok(Json.toJson(carAdvertService.getAllAdverts()))
  }

  // Helpers

  private def getUUID(json: JsValue) = {
    (json \ "id").get.as[UUID]
  }


  private def getTitle(json: JsValue) = {
    (json \ "title").get.as[String]
  }

  private def getPrice(json: JsValue) = {
    (json \ "price").get.as[Int]
  }

  private def getIsNew(json: JsValue) = {
    (json \ "isNew").get.as[Boolean]
  }

  private def getFuel(json: JsValue) = {
    (json \ "fuel").get.as[String] match {
      case "gasoline" => Gasoline
      case "diesel" => Diesel
    }
  }

  private def getMileage(json: JsValue): Option[Int] = {
    (json \ "mileage") match {
      case JsDefined(value) => Some(value.as[Int])
      case _ => None
    }
  }

  private def getFirstRegistration(json: JsValue): Option[Date] = {
    (json \ "firstRegistration") match {
      case JsDefined(value) => Some(value.as[Date])
      case _ => None
    }
  }
}
