import org.specs2.mutable._
import play.api.db.slick.DB
import play.api.db.slick.Config.driver.simple._
import play.api.test._
import play.api.test.Helpers._
import models._
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.junit.runners.Suite
import play.api.libs.concurrent.Execution.Implicits.defaultContext


/**
 * test the kitty cat database
 */

@RunWith(classOf[JUnitRunner])
class DBSpec extends Specification {

  "DB" should {
    "work as expected" in new WithApplication {
      //create an instance of the table
      val users = Users.users

      DB.withSession { implicit s: Session =>
        val testKitties = Seq(
          User(2, "black","123"))
        users.insertAll(testKitties: _*)
        users.list must equalTo(testKitties)
      }
    }

    "select the correct testing db settings by default" in new WithApplication(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
      DB.withSession { implicit s: Session =>
        s.conn.getMetaData.getURL must startWith("jdbc:h2:mem:play-test")
      }
    }

    "use the default db settings when no other possible options are available" in new WithApplication {
      DB.withSession { implicit s: Session =>
        s.conn.getMetaData.getURL must equalTo("jdbc:h2:mem:play")
      }
    }
  }

}