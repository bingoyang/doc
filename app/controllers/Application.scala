package controllers

import play.api._
import play.api.mvc._
import models.UserInfos

object Application extends Controller {

  def index = Action {
    Ok(views.html.index())
  }
  def touserinfo = Action {
    Ok(views.html.userinfo("用户信息管理"))
  }
  
  def userinfolist = Action {
    Ok(views.html.userinfo("用户信息管理"))
  }
}