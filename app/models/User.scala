package models

import scala.concurrent.ExecutionContext
import play.api.db.slick.iteratees.SlickPlayIteratees._
import play.api.db.slick.Config.driver
import play.api.db.slick.Config.driver.simple._
import play.api.Logger
import play.api._
import play.api.db.slick.DB
import play.api.Play.current

case class User(id: Option[Int] = None, username: String,password: String)

/** Mapping of columns to the row object */
class UsersTB(tag: Tag) extends Table[User](tag, "admin_user") {
  def id   = column[ Option[Int]   ]("id",O.AutoInc, O.PrimaryKey)
  def username = column[String]("username")
  def password = column[String]("password")
  def * = (id, username,password) <> (User.tupled, User.unapply)
}

object Users {
  /** Base query for the table */
  object users extends TableQuery(new UsersTB(_))
  
  def findOneByUsername(username: String)(implicit s: Session): Option[User] = users.filter(_.username === username).list.headOption
  
  def authenticate(username: String, password: String): Option[User] = {
    
    DB.withSession{implicit session: play.api.db.slick.Config.driver.simple.Session =>
       users.filter(_.username === username).filter(_.password === password).list.headOption
    }
  }
  def create(user:User)(implicit s: Session) : Option[Int]= (users returning users.map(_.id)) += user
  
  def findById(id:Option[Int])(implicit s: Session): Option[User] = users.filter(_.id === id).list.headOption
  
  def count(implicit s: Session): Int = Query(users.length).first
}