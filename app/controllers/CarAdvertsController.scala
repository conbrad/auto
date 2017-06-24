package controllers

import java.util.{Date, UUID}
import javax.inject.Inject

import models.adverts.{CarAdvert, Fuel}
import play.api.libs.json._
import play.api.mvc.{Action, Controller}
import services.CarAdvertService
import FieldExtractor._

/**
  * Created by conor on 2017-06-23.
  */
class CarAdvertsController @Inject()(carAdvertService: CarAdvertService) extends Controller {

  def createAdvert = Action { request =>
    val json: JsValue = request.body.asJson.get
    val title: String = getTitle(json)
    val fuel: Fuel = getFuel(json)
    val price: Int = getPrice(json)
    val isNew: Boolean = getIsNew(json)
    val mileage: Option[Int] = getMileage(json)
    val firstRegistration: Option[Date] = getFirstRegistration(json)
    val newCarAdvert = CarAdvert.createNew(title, fuel, price, isNew, mileage, firstRegistration)

    Ok(Json.toJson(carAdvertService.createAdvert(newCarAdvert)))
  }

  def deleteAdvert(id: UUID) = Action { request =>
    Ok(Json.toJson(carAdvertService.deleteAdvert(id)))
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

    Ok(Json.toJson(carAdvertService.updateAdvert(updatedAdvert)))
  }

  def getAllAdverts = Action { request =>
    Ok(Json.toJson(carAdvertService.getAllAdverts()))
  }

  def getAdvert(id: UUID) = Action { request =>
    Ok(Json.toJson(carAdvertService.getAdvert(id)))
  }
}
