package MapReduceJobs.GeneratePopulationMR

trait SortUtils {
  // Trait to have common code for both Mapper and Reducer
  var topListLength = 10
  var filledIdx = 0
  var topList = Array.ofDim[(Double, String)](topListLength)

  def setNumOfCrossover() = {

  }

  def fillTopList(rank: Double, nodeId: String, numOutLinks: Long = -1) = {
    var i = 0
    var cmpIp = (rank, nodeId)

    // Filling the array while maintaining decreasing order
    // if a null value is present in the array, the input can be
    // placed at that i index.
    while (i < topListLength && topList(i) != null) {
      if (cmpIp._1 > topList(i)._1) {
        val tmp = topList(i)
        topList(i) = cmpIp
        cmpIp = tmp
      }
      i = i + 1
    }
    if (i < topListLength) topList(i) = cmpIp
  }
}