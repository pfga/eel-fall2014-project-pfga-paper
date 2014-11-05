package FuzzyTimeSeries

import org.apache.hadoop.conf.Configuration

object Trial extends App {
  val startTime = System.nanoTime()
  val ftsIpFileName = "src/main/resources/input_fts_2.txt"
  val (annualRecords, ll, ul, numOfElements) = HelperUtils.HelperFunctions
    .readReduceOp(new Configuration(), ftsIpFileName)
  println(ll, ul, numOfElements)
  val numInd = 1
  val p = Array.ofDim[FuzzyIndividual](numInd)
  (0 until numInd).foreach { i =>
    val f = new FuzzyIndividual()
    f.setUniverse(ul, ll, numOfElements)
    f.initializeFuzzySet(annualRecords, 3)
    f.forecastValues()
    println(f.mse)
    p(i) = f
  }

  val stopTime = System.nanoTime()

  println((Runtime.getRuntime.totalMemory() - Runtime.getRuntime.freeMemory()) / 1024 / 1024)
  println((stopTime - startTime) / 1000000000)
}