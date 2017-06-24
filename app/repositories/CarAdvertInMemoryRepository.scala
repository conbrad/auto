package repositories
import java.util.UUID
import javax.inject.Inject

/**
  * Created by conor on 2017-06-24.
  */
@Inject
class CarAdvertInMemoryRepository extends CarAdvertRepository {
  override def getAll(): Seq[CarAdvertRepository] = ???

  override def get(id: UUID): Option[CarAdvertRepository] = ???

  override def insert(newCarAdvert: CarAdvertRepository): Unit = ???

  override def update(updatedCarAdvert: CarAdvertRepository): Unit = ???

  override def delete(id: UUID): Unit = ???
}
