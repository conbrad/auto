package repositories
import java.util.{Date, UUID}
import javax.inject.Inject

import com.mongodb.casbah.Imports._
import models.adverts.{CarAdvert, Fields, Fuel}
import repositories.mongo.MongoManager
import CarAdvertMongoRepository._

/**
  * Created by conor on 2017-06-24.
  */
@Inject
class CarAdvertMongoRepository @Inject() (mongoClient: MongoManager) extends CarAdvertRepository {

  override def getAll(): Seq[CarAdvert] = {
    mongoClient.collection.find.flatMap(convertBack).toSeq
  }

  override def get(id: UUID): Option[CarAdvert] = {
    val query = MongoDBObject(Fields.id -> id.toString)
    mongoClient.collection.findOne(query) match {
      case Some(carAdvert) => convertBack(carAdvert)
      case _ => None
    }
  }

  override def insert(newCarAdvert: CarAdvert): Unit = {
    val mongoObject = buildMongoObject(newCarAdvert)
    mongoClient.collection.insert(mongoObject)
  }

  override def update(updatedCarAdvert: CarAdvert): Option[CarAdvert] = {
    val query = MongoDBObject(Fields.id -> updatedCarAdvert.id.toString)
    mongoClient.collection.findAndModify(query, buildMongoObject(updatedCarAdvert)) match {
      case Some(updated) => convertBack(updated)
      case _ => None
    }
  }

  override def delete(id: UUID): Option[CarAdvert] = {
    val query = MongoDBObject(Fields.id -> id.toString)
    mongoClient.collection.findAndRemove(query) match {
      case Some(deleted) => convertBack(deleted)
      case _ => None
    }
  }
}

object CarAdvertMongoRepository {
  def buildMongoObject(carAdvert: CarAdvert) = {
    val builder = MongoDBObject.newBuilder
    builder += Fields.id -> carAdvert.id.toString
    builder += Fields.title -> carAdvert.title
    builder += Fields.fuel -> carAdvert.fuel.value
    builder += Fields.price -> carAdvert.price
    builder += Fields.isNew -> carAdvert.isNew
    builder += Fields.mileage -> carAdvert.mileage
    builder += Fields.firstRegistration -> carAdvert.firstRegistration
    builder.result
  }

  def convertBack(mongoCarAdvert: DBObject): Option[CarAdvert] = {
    val id = mongoCarAdvert.getAs[String](Fields.id).getOrElse(throw new Exception)
    val title = mongoCarAdvert.getAs[String](Fields.title).getOrElse(throw new Exception)
    val fuelName = mongoCarAdvert.getAs[String](Fields.fuel).getOrElse(throw new Exception)
    val price = mongoCarAdvert.getAs[Int](Fields.price).getOrElse(throw new Exception)
    val isNew = mongoCarAdvert.getAs[Boolean](Fields.isNew).getOrElse(throw new Exception)
    val mileage = mongoCarAdvert.getAs[Int](Fields.mileage)
    val firstRegistration = mongoCarAdvert.getAs[Date](Fields.firstRegistration)
    val fuel: Fuel = Fuel.findByName(fuelName).getOrElse(throw new Exception("No fuel with name " + fuelName))

    Some(CarAdvert(
      UUID.fromString(id),
      title,
      fuel,
      price,
      isNew,
      mileage,
      firstRegistration)
    )
  }
}
