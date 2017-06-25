import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.libs.json.{JsObject, JsValue, Json}
import play.api.test._
import play.api.test.Helpers._

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  * For more information, consult the wiki.
  */
@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {

  "Application" should {

    "send 404 on a bad request" in new WithApplication {
      route(FakeRequest(GET, "/boum")) must beSome.which(status(_) == NOT_FOUND)
    }

    "render the index page" in new WithApplication {
      val home = route(FakeRequest(GET, "/")).get

      status(home) must equalTo(OK)
      contentType(home) must beSome.which(_ == "text/html")
      contentAsString(home) must contain("Your new application is ready.")
    }

    "insert new CarAdvert from endpoint" in new WithApplication {
      val newCarAdvert: JsValue = Json.parse(
        """
          {
          	"title":"N/A",
          	"fuel":"gasoline",
          	"price":0,
          	"isNew":true,
          	"mileage":10000,
          	"firstRegistration":"1990-02-01"
          }
      """)
      val Some(result) = route(FakeRequest(POST, "/create")
        .withJsonBody(
          newCarAdvert
        ))

      status(result) must equalTo(OK)
      contentAsString(result).contains("id")
    }

    "update a CarAdvert with any id should result in OK 200" in new WithApplication {
      val newCarAdvert: JsValue = Json.parse(
        """
          {
            "id": "1",
          	"title":"N/A",
          	"fuel":"gasoline",
          	"price":0,
          	"isNew":true,
          	"mileage":10000,
          	"firstRegistration":"1990-02-01"
          }
      """)
      val Some(result) = route(FakeRequest(POST, "/create")
        .withJsonBody(
          newCarAdvert
        ))

      status(result) must equalTo(OK)
    }

    "create a CarAdvert with unknown fuel type should result in BadRequest 400" in new WithApplication {
      val newCarAdvert: JsValue = Json.parse(
        """
          {
            "id": "1",
          	"title":"N/A",
          	"fuel":"beer",
          	"price":0,
          	"isNew":true,
          	"mileage":10000,
          	"firstRegistration":"1990-02-01"
          }
      """)
      val Some(result) = route(FakeRequest(POST, "/create")
        .withJsonBody(
          newCarAdvert
        ))

      status(result) must equalTo(BAD_REQUEST)
    }
  }
}
