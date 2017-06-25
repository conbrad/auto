package repositories.mongo

import com.mongodb.casbah.{MongoCollection, MongoConnection}

object MongoManager {
  private val SERVER = "localhost"
  private val DATABASE = "auto"
  private val COLLECTION = "carAdverts"
  val connection = MongoConnection(SERVER)
  val collection: MongoCollection = connection(DATABASE)(COLLECTION)
}