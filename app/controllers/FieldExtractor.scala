package controllers

import java.util.{Date, UUID}

import models.adverts.Fuel
import play.api.libs.json.{JsDefined, JsNull, JsValue}

/**
  * Created by conor on 2017-06-24.
  */
object FieldExtractor {
  def getUUID(json: JsValue) = {
    (json \ "id").get.as[UUID]
  }


  def getTitle(json: JsValue) = {
    (json \ "title").get.as[String]
  }

  def getPrice(json: JsValue) = {
    (json \ "price").get.as[Int]
  }

  def getIsNew(json: JsValue) = {
    (json \ "isNew").get.as[Boolean]
  }

  def getFuel(json: JsValue) = {
    val name = (json \ "fuel").getOrElse(JsNull).as[String]
    Fuel.findByName(name)
  }

  def getMileage(json: JsValue): Option[Int] = {
    json \ "mileage" match {
      case JsDefined(value) => Some(value.as[Int])
      case _ => None
    }
  }

  def getFirstRegistration(json: JsValue): Option[Date] = {
    json \ "firstRegistration" match {
      case JsDefined(value) => Some(value.as[Date])
      case _ => None
    }
  }
}
