package controllers

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.reflect.ClassTag
import scala.reflect.classTag
import jp.t2v.lab.play2.auth._
import jp.t2v.lab.play2.stackc.RequestAttributeKey
import jp.t2v.lab.play2.stackc.RequestWithAttributes
import jp.t2v.lab.play2.stackc.StackableController
import models.{ User => Customer }
import play.api._
import play.api.Logger
import play.api.Play._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import play.api.mvc.Results._
import play.api.templates._
import views._
import models.Users
import play.api.db.slick.DB

object Authorize extends Controller with LoginLogout with AuthConfigImpl {
  var logger = Logger(this.getClass)
  
  val loginForm = Form {
    mapping("username" -> email, "password" -> text)(Users.authenticate)(_.map(u => (u.username, "")))
      .verifying("Invalid email or password", result => result.isDefined)
  }

  def login = Action { implicit request =>
    Ok(views.html.login(loginForm))
  }

  def logout = Action.async { implicit request =>
    gotoLogoutSucceeded.map(_.flashing("success" -> "You've been logged out"))
  }

  def authenticate = Action.async { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(html.login(formWithErrors))),
      user => gotoLoginSucceeded(user.get.id))
  }
}

trait AuthConfigImpl extends AuthConfig {

  type Id = Option[Int]

  type User = Customer 

  type Authority = Int

  val idTag: ClassTag[Id] = classTag[Id]

  val sessionTimeoutInSeconds = 3600

  def resolveUser(id: Option[Int])(implicit ctx: ExecutionContext) = {
    Future.successful(DB.withSession { implicit session: play.api.db.slick.Config.driver.simple.Session =>
      Users.findById(id)
    })
  }

  def loginSucceeded(request: RequestHeader)(implicit ctx: ExecutionContext) = Future.successful(Redirect(routes.Application.index))

  def logoutSucceeded(request: RequestHeader)(implicit ctx: ExecutionContext) = Future.successful(Redirect(routes.Authorize.authenticate))

  def authenticationFailed(request: RequestHeader)(implicit ctx: ExecutionContext) = Future.successful(Redirect(routes.Authorize.authenticate))

  def authorizationFailed(request: RequestHeader)(implicit ctx: ExecutionContext) = Future.successful(Forbidden("no permission"))

  def authorize(user: User, authority: Authority)(implicit ctx: ExecutionContext) = Future.successful(true)

}
