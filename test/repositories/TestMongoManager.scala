package repositories

import com.mongodb.casbah.{MongoClient, MongoCollection, MongoDB}
import repositories.mongo.MongoManager

/**
  * Created by conor on 2017-06-25.
  */
class TestMongoManager extends MongoManager {
  val mongoClient: MongoClient = MongoClient()
  val db: MongoDB = mongoClient("testAuto")
  val coll: MongoCollection = db("carAdverts")
  override def collection: MongoCollection = coll
}
