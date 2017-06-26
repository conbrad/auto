package models.adverts

import java.util.{Date, UUID}

import models.adverts.Fuel.Gasoline
import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
  * Created by conor on 2017-06-23.
  */

case class CarAdvert(id: UUID,
                     title: String,
                     fuel: Fuel,
                     price: Int,
                     isNew: Boolean = false,
                     mileage: Option[Int],
                     firstRegistration: Option[Date])

object CarAdvert {

  import Fuel.FuelFormatter

  def createNewFromUUID(id: UUID): CarAdvert = {
    new CarAdvert(id, "N/A", Gasoline, 0, true, None, None)
  }

  def createNewDefault(): CarAdvert = {
    new CarAdvert(UUID.randomUUID(), "N/A", Gasoline, 0, true, None, None)
  }

  def createNew(title: String,
                fuel: Fuel,
                price: Int,
                isNew: Boolean,
                mileage: Option[Int],
                firstRegistration: Option[Date]): CarAdvert = {
    new CarAdvert(UUID.randomUUID(), title, fuel, price, false, None, None)
  }

  implicit val carAdvertFormatter: Format[CarAdvert] = (
      (__ \ "id").format[UUID] and
      (__ \ "title").format[String] and
      (__ \ "fuel").format[Fuel] and
      (__ \ "price").format[Int] and
      (__ \ "isNew").format[Boolean] and
      (__ \ "mileage").format[Option[Int]] and
      (__ \ "firstRegistration").format[Option[Date]]
    ) (CarAdvert.apply, unlift(CarAdvert.unapply))

  // TODO: this is not reading/writing optional mileage and firstRegistration fields properly
  implicit def optionFormat[T: Format]: Format[Option[T]] = new Format[Option[T]] {
    override def reads(json: JsValue): JsResult[Option[T]] = json.validateOpt[T]

    override def writes(o: Option[T]): JsValue = o match {
      case Some(t) ⇒ implicitly[Writes[T]].writes(t)
      case None ⇒ JsNull
    }
  }
}
