package controllers

import java.util.{Date, UUID}

import models.adverts.{Fields, Fuel}
import play.api.libs.json.{JsDefined, JsNull, JsValue}

/**
  * Created by conor on 2017-06-24.
  */

// TODO: Do not use "get" it's not safe

object FieldExtractor {
  def getUUID(json: JsValue) = {
    (json \ Fields.id).get.as[UUID]
  }

  def getTitle(json: JsValue) = {
    (json \ Fields.title).get.as[String]
  }

  def getPrice(json: JsValue) = {
    (json \ Fields.price).get.as[Int]
  }

  def getIsNew(json: JsValue) = {
    (json \ Fields.isNew).get.as[Boolean]
  }

  def getFuel(json: JsValue) = {
    val name = (json \ Fields.fuel).getOrElse(JsNull).as[String]
    Fuel.findByName(name)
  }

  def getMileage(json: JsValue): Option[Int] = {
    json \ Fields.mileage match {
      case JsDefined(value) => Some(value.as[Int])
      case _ => None
    }
  }

  def getFirstRegistration(json: JsValue): Option[Date] = {
    json \ Fields.firstRegistration match {
      case JsDefined(value) => Some(value.as[Date])
      case _ => None
    }
  }
}
