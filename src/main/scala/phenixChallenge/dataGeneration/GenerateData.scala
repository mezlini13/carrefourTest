package phenixChallenge.dataGeneration

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import DataGenerationUtils._

object GenerateData {


  def main(args: Array[String]): Unit = {

    val dateS = args(0)
    val nbDays = args(1).toInt
    val path = args(2)
    val nbrRowsTx = args(3).toInt
    val nbrProduct = args(4).toInt
    val nbrMagasin = args(5).toInt

    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val runDay = LocalDate.parse(dateS, formatter)
    val listMagasin = getRandomMagasinList(nbrMagasin)

    for (i <- 1 to nbDays) {

      val date = runDay
        .minusDays(i)
        .toString
        .replace("-", "")

      generateTx(path + "/transactions_" + date + ".data", nbrRowsTx, nbrProduct, listMagasin, date)

      for (mag <- listMagasin) {
        generateRef(path + "/reference_prod-" + mag + "_" + date + ".data", nbrProduct)
      }
    }
  }
}
