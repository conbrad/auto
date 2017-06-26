package repositories.mongo

import com.google.inject.ImplementedBy
import com.mongodb.casbah.MongoCollection

/**
  * Created by conor on 2017-06-25.
  */
@ImplementedBy(classOf[CarAdvertMongoManager])
trait MongoManager {
  def collection: MongoCollection
}
