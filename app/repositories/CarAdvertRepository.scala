package repositories

import java.util.UUID
import javax.inject.Inject

import com.google.inject.ImplementedBy

/**
  * Created by conor on 2017-06-23.
  */
@ImplementedBy(classOf[CarAdvertInMemoryRepository])
trait CarAdvertRepository {
  def getAll(): Seq[CarAdvertRepository]
  def get(id: UUID): Option[CarAdvertRepository]
  def insert(newCarAdvert: CarAdvertRepository)
  def update(updatedCarAdvert: CarAdvertRepository)
  def delete(id: UUID)
}
