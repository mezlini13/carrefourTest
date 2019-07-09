package phenixChallenge.dataGeneration

import java.io.{File, PrintWriter}
import java.text.SimpleDateFormat
import java.util.Date

object DataGenerationUtils {

  val MAX_QTE = 100
//  val NBR_PRODUIT = 100
  val NBR_TX = 500
//  val NBR_Mag = 100
//  val NBR_ROWS_TX = 1000


  /** Générer une chaine aléatoire
    *
    * @param length la taille de la chaine
    * @return chaine aléatoire de taille length
    */
  def randomString(length: Int): String = {
    val r = new scala.util.Random
    r.alphanumeric.take(length).mkString.toLowerCase
  }


  /** Générer une liste aléatoire des magasins
    *
    * @return une liste de string aléatoires des magasins
    */
  def getRandomMagasinList(nbrMagasins : Int): List[String] = {

    var listmagasins: List[String] = Nil
    for (i <- 1 to nbrMagasins) {

      var magasin =
        randomString(8) + "-" +
          randomString(4) + "-" +
          randomString(4) + "-" +
          randomString(4) + "-" +
          randomString(12)

      listmagasins = listmagasins ::: List(magasin)
    }
    listmagasins
  }


  /** Générer les transactions
    *
    * @param path la destination du fichier
    * @param listMag la liste des magasins
    * @param date la dernière date du calcul
    */
  def generateTx(path: String, nbrRowsTx: Int, nbrProduct: Int, listMag: List[String], date: String): Unit = {

    val writer = new PrintWriter(new File(path))

    for (i <- 1 to nbrRowsTx) {

      val r = new scala.util.Random

      val dateFormat = new SimpleDateFormat("'T'HHmmssZ")
      val hourWithTZ = new Date(System.currentTimeMillis() * r.nextInt(10))
      val thisDate = date + dateFormat.format(hourWithTZ)

      val txId = r.nextInt(NBR_TX)
      val magasin = listMag(r.nextInt(listMag.length))
      val produit = r.nextInt(nbrProduct)
      val qte = r.nextInt(MAX_QTE)

      writer.write(txId + "|" + thisDate + "|" + magasin + "|" + produit + "|" + qte + "\r\n")
    }
    writer.close()
  }


  /** Générer les reférences
    *
    * @param path la destination des fichiers
    */
  def generateRef(path: String, nbrProduct: Int): Unit = {
    val writer = new PrintWriter(new File(path))
    for (i <- 1 to nbrProduct) {
      val r = new scala.util.Random
      val prix= r.nextInt(1000) * 0.1
      writer.write(i + "|" + prix + "\r\n")
    }
    writer.close()
  }

}
