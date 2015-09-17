import org.junit.runner.RunWith
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.DB
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.test._
import play.api.test.Helpers._
import models._
import services.UserInfoService
import services.Condition

/**
 * test the kitty cat database
 */

@RunWith(classOf[JUnitRunner])
class DBSpec extends Specification {

  "DB" should {
    "work as expected" in new WithApplication {
      //create an instance of the table

      DB.withSession { implicit s: Session =>
          //Users.create(User(None,"yangliubinga@163.com","123"))
          UserInfoService.findUserInfos(1,1, new Condition("","","","","",""))
      }
    }
  }

}