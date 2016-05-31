package my_crazy_bird

import scala.collection.JavaConversions
import scala.collection.mutable.MutableList

class StatisticList {
  val tempList: MutableList[Int] = MutableList()

  def addEl(element: Int) {
    tempList.+=(element)
  }

  def ret(): java.util.List[Int] = {
    val tempArray: MutableList[Int] = for (temp <- tempList) yield temp
    JavaConversions.mutableSeqAsJavaList(tempArray)
  }

  def maxElem(): Int = {
    var max = 0
    for (i <- 0 until tempList.size if tempList(i) > max)
      yield max = tempList(i)
    max
  }

  def averageElem(): Int = {
    var avElem = 0;
    for (i <- 0 until tempList.size)
      yield avElem += tempList(i)
    avElem / tempList.size
  }
}
  
  
  
  