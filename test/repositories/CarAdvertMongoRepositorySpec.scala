package repositories

import java.time.Instant
import java.util.{Date, UUID}

import models.adverts.CarAdvert
import models.adverts.Fuel.Diesel
import org.scalatest.FunSpec
import org.scalatest.mockito.MockitoSugar


/**
  * Created by conor on 2017-06-25.
  */
class CarAdvertMongoRepositorySpec extends FunSpec with MockitoSugar {

  val testDB = new TestMongoManager

  describe("CarAdvertMongoRepositorySpec") {
    it("should insert a CarAdvert") {
      val testDB = new TestMongoManager
      val testRepository = new CarAdvertMongoRepository(testDB)
      val id = UUID.randomUUID()
      testRepository.insert(CarAdvert.createNewFromUUID(id))
      assert(testRepository.get(id).nonEmpty)
      shutdown(testDB)
    }

    it("should return empty list when there is no adverts") {
      val testDB = new TestMongoManager
      val testRepository = new CarAdvertMongoRepository(testDB)
      assert(testRepository.getAll().isEmpty)
      shutdown(testDB)
    }

    it("should delete an existing CarAdvert") {
      val testDB = new TestMongoManager
      val testRepository = new CarAdvertMongoRepository(testDB)
      val id = UUID.randomUUID()
      testRepository.insert(CarAdvert.createNewFromUUID(id))
      assert(testRepository.get(id).nonEmpty)
      assert(testRepository.delete(id).nonEmpty)
    }

    it("should not delete an non-existent CarAdvert") {
      val testDB = new TestMongoManager
      val testRepository = new CarAdvertMongoRepository(testDB)
      val id = UUID.randomUUID()
      testRepository.insert(CarAdvert.createNewFromUUID(id))
      assert(testRepository.get(id).nonEmpty)
      val nonExistentId = UUID.randomUUID()
      assert(testRepository.delete(nonExistentId).isEmpty)
    }

    it("should update an existing CarAdvert") {
      val testDB = new TestMongoManager
      val testRepository = new CarAdvertMongoRepository(testDB)
      val id = UUID.randomUUID()
      testRepository.insert(CarAdvert.createNewFromUUID(id))
      val updatedCarAdvert =
        CarAdvert(id,
          "BMW M3",
          Diesel,
          40000,
          isNew = false,
          Some(25000),
          Some(Date.from(Instant.now())))
      assert(testRepository.update(updatedCarAdvert).nonEmpty)
    }

    it("should not update an non-existent CarAdvert") {
      val testDB = new TestMongoManager
      val testRepository = new CarAdvertMongoRepository(testDB)
      val nonExistentAdvert =
        CarAdvert(UUID.randomUUID(),
          "BMW M3",
          Diesel,
          40000,
          isNew = false,
          Some(25000),
          Some(Date.from(Instant.now())))
      assert(testRepository.update(nonExistentAdvert).isEmpty)
    }
  }

  def shutdown(db: TestMongoManager): Unit = {
    db.coll.drop()
    db.db.dropDatabase()
  }

}
