package services

import javax.inject.Inject

import models.adverts.CarAdvert
import repositories.CarAdvertRepository

/**
  * Created by conor on 2017-06-24.
  */
@Inject
class CarAdvertService @Inject() (repository: CarAdvertRepository) {
  def updateAdvert(updatedAdvert: CarAdvert): Boolean = {
    true
  }

  def getAllAdverts(): Seq[CarAdvert] = {
    List()
  }
}
