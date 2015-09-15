package auth

import java.util.Date
import com.mongodb.casbah.Imports._
import com.novus.salat._
import com.novus.salat.dao._
import mongoContext._
import play.api.Play.current
import play.api.libs.functional.syntax._
import play.api.libs.json._
import se.radley.plugin.salat._
import se.radley.plugin.salat.Binders._
import play.api.Logger

case class User(
  id: ObjectId = new ObjectId,
  username: String ,
  password: String ,
  added: Date = new Date(),
  updated: Option[Date] = None,
  permission: Option[Permission] = None
)

object User extends  ModelCompanion[User, ObjectId] with UserJson {
  
  val logger = Logger(this.getClass) 
  
  def collection = mongoCollection("users")
  
  val dao = new SalatDAO[User, ObjectId](collection) {}

  // Indexes
  collection.ensureIndex(DBObject("username" -> 1), "username", unique = true)
  
  // Queries
  def findOneByUsername(username: String): Option[User] = dao.findOne(MongoDBObject("username" -> username))
  
  def authenticate(username: String, password: String): Option[User] = {
    dao.findOne(DBObject("username" -> username, "password" -> password))
  }
  
  def create(user:User) = dao.insert(t = user)
  
  def findById(id:ObjectId): Option[User] = dao.findOneByID(id)
  
}

/**
 * Trait used to convert to and from json
 */
trait UserJson {

  implicit val userJsonWrite = new Writes[User] {
    def writes(u: User): JsValue = {
      Json.obj(
        "id" -> u.id,
        "username" -> u.username,
        "added" -> u.added,
        "updated" -> u.updated,
        "permission" -> u.permission
      )
    }
  }
  implicit val userJsonRead = (
    (__ \ 'id).read[ObjectId] ~
    (__ \ 'username).read[String] ~
    (__ \ 'password).read[String] ~
    (__ \ 'added).read[Date] ~
    (__ \ 'updated).readNullable[Date]~
    (__ \ 'permission).readNullable[Permission]
  )(User.apply _)
}

