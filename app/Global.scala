import play.api._
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick.DB
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.EssentialAction
import models._
import models.Users.users
import play.api.Play.current


object Global extends GlobalSettings {

  override def onStart(app: Application) {
      InitialData.insert()
  }
  
  override def doFilter(action: EssentialAction) = AuthFilter(action)
  
 object InitialData {

  def insert(): Unit = {
    DB.withSession { implicit s =>
        if (Users.count == 0) {
          val rows = Seq(
            User(1, "yangliubinga@163.com", "123"))
          Users.users.insertAll(rows: _*)
        }
      }
    }
  }
}