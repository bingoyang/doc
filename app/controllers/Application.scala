package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index())
  }
  def creative = Action {
    Ok(views.html.creative("广告创意管理"))
  }
}