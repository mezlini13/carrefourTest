  package phenixChallenge.dataGeneration


  import DataGenerationUtils._
  import org.scalatest.FunSuite

  import scala.io.Source.fromFile

  class DataGenerationUtilsTest extends FunSuite {

    test("Test random string generation") {

      val length = 5

      val actual = randomString(length).length
      val expected = 5

      assert(actual == expected)
    }


    test("Test random list magasins generation") {

      val nbrmagasins = 3
      val listMagasins = getRandomMagasinList(nbrmagasins)
      val actualNbMag = listMagasins.size
      val expectedNbMag = 3

      val actualSizeMag = listMagasins.map(_.split("-").toList.map(_.length))
      val expectedSizeMag = List(List(8, 4, 4, 4, 12), List(8, 4, 4, 4, 12), List(8, 4, 4, 4, 12))

      assert(actualNbMag == expectedNbMag)
      assert(actualSizeMag == expectedSizeMag)

    }


    test("Test transaction file generation") {
      val path = "data"
      val nbRows = 100
      val nbProduct = 10
      val listMag = getRandomMagasinList(3)
      val date = "20190708"

      generateTx(path + "/transactions_" + date + ".data", nbRows, nbProduct, listMag, date)

      val txFile = fromFile(path + "/transactions_" + date + ".data")

      val actualNbElm = txFile.getLines().map(line => line.split("\\|")).toList.head.length
      val expectedNbElm = 5

      assert(actualNbElm == expectedNbElm)

    }
  }

