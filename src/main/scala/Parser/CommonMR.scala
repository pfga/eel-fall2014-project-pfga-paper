package Parser

/**
 * Created by preethu19th on 10/2/14.
 */
trait CommonMR {

  var min: Long = _
  var max: Long = _
  var firstRedOp = true

  def checkMinMax(currVal: Long) = {
    if (firstRedOp) {
      min = currVal
      max = currVal
      firstRedOp = false
    } else {
      if (min > currVal) min = currVal
      if (max < currVal) max = currVal
    }
  }
}
