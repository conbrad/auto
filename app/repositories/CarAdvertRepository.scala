package repositories

import java.util.UUID

import com.google.inject.ImplementedBy
import models.adverts.CarAdvert

/**
  * Created by conor on 2017-06-23.
  */
@ImplementedBy(classOf[CarAdvertMongoRepository])
trait CarAdvertRepository {
  def getAll: Seq[CarAdvert]
  def get(id: UUID): Option[CarAdvert]
  def insert(newCarAdvert: CarAdvert)
  def update(updatedCarAdvert: CarAdvert): Option[CarAdvert]
  def delete(id: UUID): Option[CarAdvert]
}
