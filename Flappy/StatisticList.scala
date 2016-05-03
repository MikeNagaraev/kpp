package my_crazy_bird

import scala.collection.JavaConversions
import scala.collection.mutable.MutableList

class StatisticList {
  val tempList: MutableList[Int] = MutableList()

  def addEl(element: Int) {
    tempList.+=(element)
  }

  def ret(): java.util.List[Int] = {
    JavaConversions.mutableSeqAsJavaList(tempList)
  }

  def maxElem(): Int = {
    var i = 0;
    var max = 0;
    for (i <- 0 until tempList.size) {
      if (tempList(i) > max) {
        max = tempList(i);
      }
    }
    max
  }

  def averageElem(): Int = {
    var avElem = 0;
    for (i <- 0 until tempList.size) {
      avElem += tempList(i);
    }
    avElem/tempList.size
  }
}
  
  
  
  