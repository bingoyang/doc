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
import services.UserInfoBean
import play.api.libs.json.Json

/**
 * test the kitty cat database
 */

@RunWith(classOf[JUnitRunner])
class DBSpec extends Specification {

  "DB" should {
    "work as expected" in new WithApplication {
      //create an instance of the table
      val userInfo = new UserInfoBean(0L,"111","2222","3333","4444","5555","6666",new java.sql.Date(System.currentTimeMillis()),"111","2222","3333","4444","5555","6666","7777","7777","111","2222",1,180,75,"6666",38,"aaaa",new java.sql.Date(System.currentTimeMillis()),new java.sql.Date(System.currentTimeMillis()))
       UserInfoService.insertUserInfo(userInfo)
       val page = UserInfoService.findUserInfos(10,1,Condition("","","","","","",10,1,1))
       println(Json.toJson(page))
    }
  }

}