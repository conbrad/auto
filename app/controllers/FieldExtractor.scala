package controllers

import java.util.{Date, UUID}

import models.adverts.{Diesel, Gasoline}
import play.api.libs.json.{JsDefined, JsValue}

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
    (json \ "fuel").get.as[String] match {
      case "gasoline" => Gasoline
      case "diesel" => Diesel
    }
  }

  def getMileage(json: JsValue): Option[Int] = {
    (json \ "mileage") match {
      case JsDefined(value) => Some(value.as[Int])
      case _ => None
    }
  }

  def getFirstRegistration(json: JsValue): Option[Date] = {
    (json \ "firstRegistration") match {
      case JsDefined(value) => Some(value.as[Date])
      case _ => None
    }
  }
}
