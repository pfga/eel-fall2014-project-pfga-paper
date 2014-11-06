package FuzzyTimeSeries

import scala.collection.{mutable => m}

class FuzzyIndividual {
  val delim = " "
  val mseDelim = "\u0001"
  var chromosome: Array[Int] = _
  var annualRecords: Array[AnnualRecord] = _
  val discourseMap = m.Map[String, Int]()
  var mse: Double = 0.0

  override def toString() = {
    s"${chromosome.mkString(delim)}$mseDelim$mse"
  }

  def generateUniverse(ul: Int, ll: Int, numOfElements: Int) = {
    val u = Array.ofDim[Int](numOfElements + 2)
    u(0) = ll
    u(numOfElements + 1) = ul
    val divisions = (ul - ll + 1) / numOfElements

    (1 to numOfElements).foreach { i =>
      val oldLl = u(i - 1) + 1
      val newLl = ll + (i * divisions)
      val random = Math.random()
      val limitDiff = newLl - oldLl + 1
      val randInterval = (random * limitDiff).toInt
      val universeElem = oldLl + randInterval

      //set an random entry as interval to universe
      u(i) = universeElem

      //set discourseMap according to the newly set interval
      setDiscourseMap(i, u(i), u(i - 1))
    }
    //set the last discourseMap
    setDiscourseMap(numOfElements + 1, u(numOfElements + 1), u(numOfElements))

    chromosome = u
  }

  /**
   * To initialize universe with already generated data
   * @param ipStr
   */

  def setUniverse(ipStr: String) = {
    val ipCols = ipStr.split(mseDelim, 2)
    val intervalStr = ipCols(0)
    annualRecords = Array.empty
    discourseMap.empty
    var prevVal = 0
    chromosome = intervalStr.split(delim).zipWithIndex.map { ipCols =>
      val (s, idx) = ipCols
      val currVal = s.toInt
      if (idx > 0)
        setDiscourseMap(idx, prevVal, currVal)
      prevVal = currVal
      currVal
    }
  }

  def setDiscourseMap(i: Int, u1: Int, u2: Int) = {
    discourseMap("A" + (i - 1)) = Math.ceil((u1 + u2) / 2).toInt
  }

  def initializeFuzzySet(ars: Array[(String, Int)], order: Int) {
    val arMap = m.Map[String, String]()
    val lfrgQueue = m.Queue[String]()

    annualRecords = Array.ofDim[AnnualRecord](ars.length)

    ars.zipWithIndex.foreach { opCols =>
      val ((timeSlot, events), idx) = opCols
      val rec = AnnualRecord(timeSlot, events)
      val currFuzzyStr = "A" + (ceilSearch(rec.events) + 1)
      rec.fuzzySet = currFuzzyStr

      val lfrg = lfrgQueue.mkString(",").replaceAll("#,", "")

      if (!lfrg.isEmpty) rec.flrgLH = lfrg
      if (lfrgQueue.isEmpty) (1 to order).foreach { i => lfrgQueue.enqueue("#")}

      lfrgQueue.dequeue()
      lfrgQueue.enqueue(currFuzzyStr)

      if (!lfrg.isEmpty) {
        if (arMap.contains(lfrg)) {
          val rHVal = arMap(lfrg)
          if (!rHVal.split(",").contains(currFuzzyStr)) {
            arMap(lfrg) = rHVal + "," + currFuzzyStr
          }
        } else {
          arMap(lfrg) = currFuzzyStr
        }
      }
      annualRecords(idx) = rec
    }
    annualRecords.foreach { rec => rec.flrgRH = arMap.getOrElse(rec.flrgLH, "")}
  }

  def ceilSearch(x: Int, low: Int = 0, high: Int = chromosome.length): Int = {
    val mid: Int = (low + high) / 2
    if (chromosome(mid) == x) mid
    else if (chromosome(mid) < x) {
      if (mid + 1 <= high && x <= chromosome(mid + 1)) mid
      else ceilSearch(x, mid + 1, high)
    }
    else {
      if (mid - 1 >= low && x > chromosome(mid - 1)) mid - 1
      else ceilSearch(x, low, mid - 1)
    }
  }

  def forecastValues() = {
    val (sqSums, numOfFcVals) = annualRecords.foldLeft(0.0, 0) { (mseCols, rec) =>
      val (sqSums, numOfFcVals) = mseCols
      val (sum, n) = rec.flrgRH.split(",").foldLeft(0, 0) { (cols, s) =>
        val (sum, n) = cols
        (sum + discourseMap.getOrElse(s, 0), n + 1)
      }
      if (n > 0) {
        rec.fcEvents = sum / n
      }
      if (rec.flrgRH.isEmpty)
        (sqSums, numOfFcVals)
      else
        (sqSums + Math.pow(rec.fcEvents - rec.events, 2), numOfFcVals + 1)
    }
    mse = Math.pow(sqSums / numOfFcVals, 0.5)
  }
}