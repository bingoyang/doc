import auth.User
import com.mongodb.casbah.Imports._
import play.api._
import libs.ws.WS
import se.radley.plugin.salat._
import auth.Permission
import play.api.mvc.EssentialAction


object Global extends GlobalSettings {

  override def onStart(app: Application) {

    if (User.count(DBObject()) == 0) {
      val p =  Permission(pname = "Administrator",pmark = "")
      
      Seq(
        User(username = "yangliubinga@163.com", password = "123",permission = Option.apply(p)),
        User(username = "bob@example.com", password = "secret",permission = Option.apply(p)),
        User(username = "chris@example.com", password = "secret",permission = Option.apply(p))) foreach User.create
    }
  }
  
  override def doFilter(action: EssentialAction) = AuthFilter(action)
}