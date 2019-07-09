package phenixChallenge.dataProcessing


import java.io.{File, PrintWriter}
import phenixChallenge.dataProcessing.DataUtils._

import org.scalatest.FunSuite


class DataUtilsTest extends FunSuite{



  test("Test calcul ca") {
    val idMagasin = "abcd"
    val date = "20190708"
    val path = "data/reference_prod-" + idMagasin + "_" + date + ".data"
    val idProduct = "3"
    val qte = 10
    val writer = new PrintWriter(new File(path))

    for (i <- 1 to 3) {
      writer.write(i + "|" + 10*i + "\r\n")
    }
    writer.close()

    val actual = calculCaProduct(idMagasin, date, idProduct, qte)
    val expected = 300

    assert(actual == expected)
  }

}
