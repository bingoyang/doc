package controllers

import play.api.data._
import play.api.data.Forms._
import play.api.templates._
import views._
import play.api.mvc._
import play.api.mvc.Results._
import jp.t2v.lab.play2.auth._
import play.api.Play._
import jp.t2v.lab.play2.stackc.{ RequestWithAttributes, RequestAttributeKey, StackableController }
import scala.concurrent.{ ExecutionContext, Future }
import ExecutionContext.Implicits.global
import reflect.{ ClassTag, classTag }
import auth.{User => Customer}
import auth.Permission
import org.bson.types.ObjectId
import play.api.Logger

object Authorize extends Controller with LoginLogout with AuthConfigImpl {
  var logger = Logger(this.getClass)

  val loginForm = Form {
    mapping("username" -> email, "password" -> text)(Customer.authenticate)(_.map(u => (u.username, "")))
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

  type Id = ObjectId

  type User = Customer 

  type Authority = Permission

  val idTag: ClassTag[Id] = classTag[Id]

  val sessionTimeoutInSeconds = 3600

  def resolveUser(id: Id)(implicit ctx: ExecutionContext) = Future.successful(Customer.findById(id))

  def loginSucceeded(request: RequestHeader)(implicit ctx: ExecutionContext) = Future.successful(Redirect(routes.Application.index))

  def logoutSucceeded(request: RequestHeader)(implicit ctx: ExecutionContext) = Future.successful(Redirect(routes.Authorize.authenticate))

  def authenticationFailed(request: RequestHeader)(implicit ctx: ExecutionContext) = Future.successful(Redirect(routes.Authorize.authenticate))

  def authorizationFailed(request: RequestHeader)(implicit ctx: ExecutionContext) = Future.successful(Forbidden("no permission"))

  def authorize(user: User, authority: Authority)(implicit ctx: ExecutionContext) = Future.successful((user.permission.get.pname, authority.pname) match {
    case ("Administrator", _)       => true
    case ("NormalUser", "NormalUser") => true
    case _                        => false
  })

}
