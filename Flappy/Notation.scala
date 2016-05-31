package my_crazy_bird
import scala.collection.mutable.Set
import scala.collection.mutable.Seq
import scala.collection.mutable.MutableList
import scala.collection.mutable.ArrayBuffer
class Notation {
  def parseNotation(obj: Any): String = {
    obj match {
      case list: java.util.ArrayList[Int] => output(list)
      case string: String => string
    }
  }

  def output(list: java.util.ArrayList[Int]): String = {
    val k = "Wall Coordinate X: " +  list.get(0) +
      " Coordinate Y: " + list.get(1);
    k
  }
}