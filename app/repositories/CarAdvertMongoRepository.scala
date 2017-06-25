package repositories
import java.util.{Date, UUID}
import javax.inject.Inject

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.{MongoCollection, MongoConnection}
import models.adverts.{CarAdvert, Fuel}

/**
  * Created by conor on 2017-06-24.
  */
@Inject
class CarAdvertMongoRepository extends CarAdvertRepository {
  import CarAdvertMongoRepository._
  override def getAll(): Seq[CarAdvert] = {
    collection.find.flatMap(convertBack).toSeq
  }

  override def get(id: UUID): Option[CarAdvert] = {
    val query = MongoDBObject("id" -> id.toString)
    CarAdvertMongoRepository.collection.findOne(query) match {
      case Some(carAdvert) => convertBack(carAdvert)
      case _ => None
    }
  }

  override def insert(newCarAdvert: CarAdvert): Unit = {
    val mongoObject = buildMongoObject(newCarAdvert)
    collection.insert(mongoObject)
  }

  override def update(updatedCarAdvert: CarAdvert): Option[CarAdvert] = {
    val query = MongoDBObject("id" -> updatedCarAdvert.id)
    collection.findAndModify(query, buildMongoObject(updatedCarAdvert)) match {
      case Some(updated) => convertBack(updated)
      case _ => None
    }
  }

  override def delete(id: UUID): Option[CarAdvert] = {
    val query = MongoDBObject("id" -> id)
    collection.findAndRemove(query) match {
      case Some(deleted) => convertBack(deleted)
      case _ => None
    }
  }
}

object CarAdvertMongoRepository {
  private val SERVER = "localhost"
  private val DATABASE = "auto"
  private val COLLECTION = "carAdverts"
  val connection = MongoConnection(SERVER)
  val collection: MongoCollection = connection(DATABASE)(COLLECTION)

  def buildMongoObject(carAdvert: CarAdvert) = {
    val builder = MongoDBObject.newBuilder
    builder += "id" -> carAdvert.id.toString
    builder += "title" -> carAdvert.title
    builder += "fuel" -> carAdvert.fuel.value
    builder += "price" -> carAdvert.price
    builder += "isNew" -> carAdvert.isNew
    builder += "mileage" -> carAdvert.mileage
    builder += "firstRegistration" -> carAdvert.firstRegistration
    builder.result
  }

  def convertBack(mongoCarAdvert: DBObject): Option[CarAdvert] = {
    val id = mongoCarAdvert.getAs[String]("id").getOrElse(throw new Exception)
    val title = mongoCarAdvert.getAs[String]("title").getOrElse(throw new Exception)
    val fuelName = mongoCarAdvert.getAs[String]("fuel").getOrElse(throw new Exception)
    val price = mongoCarAdvert.getAs[Int]("price").getOrElse(throw new Exception)
    val isNew = mongoCarAdvert.getAs[Boolean]("isNew").getOrElse(throw new Exception)
    val mileage = mongoCarAdvert.getAs[Int]("mileage")
    val firstRegistration = mongoCarAdvert.getAs[Date]("firstRegistration")
    val fuel: Fuel = Fuel.findByName(fuelName).getOrElse(throw new Exception)

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
