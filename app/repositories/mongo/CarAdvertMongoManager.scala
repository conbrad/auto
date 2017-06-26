package repositories.mongo
import javax.inject.Inject

import com.mongodb.casbah.{MongoClient, MongoCollection, MongoDB}

/**
  * Created by conor on 2017-06-25.
  */
@Inject
class CarAdvertMongoManager extends MongoManager {
  val mongoClient: MongoClient = MongoClient()
  val db: MongoDB = mongoClient("auto")
  val coll: MongoCollection = db("carAdverts")
  override def collection: MongoCollection = coll
}
