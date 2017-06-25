package services

import java.time.Instant
import java.util.{Date, UUID}

import models.adverts.CarAdvert
import models.adverts.Fuel.Gasoline
import org.mockito.Mockito._
import org.scalatest.FunSpec
import org.scalatest.mockito.MockitoSugar
import repositories.CarAdvertRepository

/**
  * Created by conor on 2017-06-25.
  */
class CarAdvertServiceSpec extends FunSpec with MockitoSugar {
  val uuid1: UUID = UUID.fromString("19153158-5f1a-4c7a-8fa4-560563304258")
  val uuid2: UUID = UUID.fromString("e85c5b2a-443b-4039-9382-c8746e0253e0")
  val nonExistentUUID: UUID = UUID.fromString("f85c5b2a-443b-4039-9382-c8746e0253e0")

  val advert1 = CarAdvert(uuid1,
    "Audi A4",
    Gasoline,
    240000,
    isNew = false,
    Some(45000),
    Some(Date.from(Instant.now())))
  val advert2 = CarAdvert(uuid2,
    "Audi A4",
    Gasoline,
    240000,
    isNew = false,
    Some(45000),
    Some(Date.from(Instant.now())))

  def initStore(): CarAdvertRepository = {
    val currentStore = List(
      advert1,
      advert2
    )

    val mockRepo = mock[CarAdvertRepository]
    when(mockRepo.get(uuid1)).thenReturn(currentStore.find(carAdvert => carAdvert.id.equals(uuid1)))
    when(mockRepo.get(uuid2)).thenReturn(currentStore.find(carAdvert => carAdvert.id.equals(uuid2)))
    when(mockRepo.get(nonExistentUUID)).thenReturn(currentStore.find(carAdvert => carAdvert.id.equals(nonExistentUUID)))
    when(mockRepo.getAll()).thenReturn(currentStore)
    mockRepo
  }

  describe("CarAdvertService") {
    it("should return a CarAdvert when passed a UUID that exists") {
      val testService = new CarAdvertService(initStore())
      assert(testService.getAdvert(uuid1).contains(advert1))
    }

    it("should return None when passed a UUID that does not exist") {
      val testService = new CarAdvertService(initStore())
      val nonExistentUUID = UUID.fromString("f85c5b2a-443b-4039-9382-c8746e0253e0")

      assert(testService.getAdvert(nonExistentUUID).isEmpty)
    }

    it("should call delete once on the repository") {
      val testRepo = initStore()
      val testService = new CarAdvertService(testRepo)
      testService.deleteAdvert(uuid1)
      verify(testRepo).delete(uuid1)
    }

    it("should call update on the repository with an existing uuid") {
      val testRepo = initStore()
      val testService = new CarAdvertService(testRepo)
      val newCar = CarAdvert.createNewFromUUID(uuid1)
      testService.updateAdvert(newCar)
      verify(testRepo).update(newCar)
    }

    it("should return all existing car adverts") {
      val testService = new CarAdvertService(initStore())
      assert(testService.getAllAdverts().size == 2)
    }

  }

}
