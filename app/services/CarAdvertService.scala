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
  val staticId = UUID.fromString("287a97e3-930a-40c9-8ac9-be15a5f06d77")
  val carAdvert1 = CarAdvert.createNewFromUUID(staticId)

  val advertList = List(carAdvert1, CarAdvert.createNewDefault(), CarAdvert.createNewDefault())

  def createAdvert(advert: CarAdvert): CarAdvert = {
    // TODO: persist in data store
    advert
  }

  def deleteAdvert(id: UUID): Option[CarAdvert] = {
    // TODO: find and delete in data store
    advertList.find(advert => advert.id.equals(id))
  }

  def updateAdvert(updatedAdvert: CarAdvert): CarAdvert = {
    // TODO: persist in data store
    updatedAdvert
  }

  def getAllAdverts(): Seq[CarAdvert] = {
    advertList
  }

  def getAdvert(id: UUID): Option[CarAdvert] = {
    advertList.find(advert => advert.id.equals(id))
  }

}
