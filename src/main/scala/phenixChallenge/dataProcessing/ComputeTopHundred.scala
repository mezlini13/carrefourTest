package phenixChallenge.dataProcessing

import DataUtils._


object ComputeTopHundred {

  val FIXED_NUMBER = 100

  def main(args: Array[String]) {

    val t0 = System.nanoTime()
    val date = args(0)
    val inputPath = args(1)

    //get and process transactions data of "20170514"
    val txOneDay = readTx(inputPath, date, 1)
    computeTopHundred(txOneDay, date, "")

    //get and process transactions data from "20170508" to "20170514"
    val txAllDays = readTx(inputPath, date, 7)
    computeTopHundred(txAllDays, date, "-J7")

    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0)/1000000000 + "s")

  }


}
