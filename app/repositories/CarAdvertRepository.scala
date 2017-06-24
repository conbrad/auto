package repositories

import java.util.UUID

/**
  * Created by conor on 2017-06-23.
  */
trait CarAdvertRepository {
  def getAll(): Seq[CarAdvertRepository]
  def get(id: UUID): Option[CarAdvertRepository]
  def insert(newCarAdvert: CarAdvertRepository)
  def update(updatedCarAdvert: CarAdvertRepository)
  def delete(id: UUID)
}
