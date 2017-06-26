package services

import java.util.UUID
import javax.inject.Inject

import models.adverts.CarAdvert
import repositories.CarAdvertRepository

/**
  * Created by conor on 2017-06-24.
  */
@Inject
class CarAdvertService @Inject() (repository: CarAdvertRepository) {

  def createAdvert(advert: CarAdvert): CarAdvert = {
    repository.insert(advert)
    advert
  }

  def deleteAdvert(id: UUID): Option[CarAdvert] = {
    repository.delete(id)
  }

  def updateAdvert(updatedAdvert: CarAdvert): CarAdvert = {
    repository.update(updatedAdvert)
    updatedAdvert
  }

  def getAllAdverts(sortBy: Option[String]): Seq[CarAdvert] = {
    sortBy match {
      case Some(field) => CarAdvert.sortByField(repository.getAll, field)
      case _ => repository.getAll
    }
  }

  def getAdvert(id: UUID): Option[CarAdvert] = {
    repository.get(id)
  }

}
