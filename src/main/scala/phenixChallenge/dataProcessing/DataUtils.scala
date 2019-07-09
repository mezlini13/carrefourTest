package phenixChallenge.dataProcessing

import java.io.{File, FileNotFoundException, PrintWriter}
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import phenixChallenge.dataProcessing.ComputeTopHundred.FIXED_NUMBER

import scala.io.Source.fromFile

object DataUtils {


  /** Lire les fichiers des transactions
    *
    * @param dateS la dernière date du calcul
    * @param NbOfDay le nombre des jours du calcul
    * @return Iterator d'un Array des String contenant les transactions
    */
  def readTx(inputPath: String, dateS: String, NbOfDay: Int): Iterator[Array[String]] = {

    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val day = LocalDate.parse(dateS, formatter)
    var allTransactions = Iterator[Array[String]]()

    for (a <- 1 to NbOfDay) {
      val date = day
        .minusDays(a)
        .toString
        .replace("-", "")

      try {
        val txFile = fromFile(inputPath + "/transactions_" + date + ".data")
        val tx = txFile.getLines().map(line => line.split("\\|"))

        allTransactions = allTransactions ++ tx
      } catch {
        case ex: FileNotFoundException => {
          println(inputPath + "/transactions_" + date + ".data not found")
        }
      }
    }

    allTransactions
  }


  /** Calculer et exporter les top 100 pour le CA et les produits
    *
    * @param tx les transactions
    * @param date la dernière date du calcul
    * @param indexDays indice à ajouter pour les calculs d'une semaine
    */
  def computeTopHundred(tx: Iterator[Array[String]], date: String, indexDays: String) {

    // Traitement des données aprés un groupe by id_magasin
    tx.toList
      .groupBy(_(2))
      .foreach(magasin => {

        val idMagasin = magasin._1
        val iterByMagasin = magasin._2

        // Traitement des données spécifiques à un magasin aprés un groupe by id_product
        val product = iterByMagasin
          .groupBy(_(3))
          .map(product => {
            val idProduct = product._1
            val iterByProduct = product._2

            // La quantité du produit
            var qte = 0

            // La date du référentiel des données
            var date = ""

            iterByProduct.map(row =>(row(1), row(4)))
              .foreach(row => {
                qte += row._2.toInt
                date = row._1.slice(0, 8)
              })

            val ca = calculCaProduct(idMagasin, date, idProduct, qte)

            (idProduct, ca, qte)
          })

        // Créer les fichiers des top_100_ventes_<ID_MAGASIN>_YYYYMMDD
        val salesForProduct = product.map(product => (product._1, product._3))

        // Trier les données par quantité
        val salesForProductIter = collection.mutable.LinkedHashMap(salesForProduct.toSeq.sortWith(_._2 > _._2): _*).take(FIXED_NUMBER)

        // Exporter le fichier des top 100 produits
        saveToFile(salesForProductIter, "results/top_100_ventes/top_100_ventes_" + idMagasin + "_" + date + indexDays +".data")

        // Créer les fichiers des top_100_ca_<ID_MAGASIN>_YYYYMMDD
        val caForProduct = product.map(product => (product._1, product._2))

        // Trier les données par revenue
        val caForProductIter = collection.mutable.LinkedHashMap(caForProduct.toSeq.sortWith(_._2 > _._2): _*).take(FIXED_NUMBER)

        // Exporter le fichier des top 100 produits
        saveToFile(caForProductIter, "results/top_100_ca/top_100_ca_" + idMagasin + "_" + date + indexDays +".data")
      }
      )
  }


  /** Calculer le chiffre d'affaires
    *
    * @param idMagasin l'id du magasin
    * @param date la date du calcul
    * @param idProduct l'id du produit
    * @param qte la quantité du produit
    * @return le chiffre d'affaires pour tous les paramètres donnés
    */
  def calculCaProduct(idMagasin: String, date: String, idProduct: String, qte: Int): Float = {

    // get price of specefic product
    val productFile = fromFile("data/reference_prod-" + idMagasin + "_" + date + ".data")

    val ref = productFile.getLines().map(line => line.split("\\|"))
    val price = ref.filter(row => row(0) == idProduct).map(row => row(1)).toList(0).toFloat

    price * qte
  }

  /** Exporter un fichier des résultats
    *
    * @param iter l'iterable contenant les information à exporter
    * @param path la destination du fichier résultat
    */
  def saveToFile(iter: Iterable[(String, Any)], path: String) {

    val writer = new PrintWriter(new File(path))

    iter.foreach(produit => writer.write(produit._1 + "|" + produit._2 + "\r\n"))

    writer.close()

  }

}
