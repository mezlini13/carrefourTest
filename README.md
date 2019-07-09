# Projet-Carrefour

Ce projet permet de générer des fichiers de transactions et des fichiers référentiels pour vérifier les contraintes imposées par le projet

Il permet également de déterminer, chaque jour, les 100 produits qui ont les meilleures ventes et ceux qui génèrent le plus gros Chiffre d'Affaire par magasin sur les 7 derniers jours.

Le projet contient deux classes principales : GenerateData et ComputeTopHundred.

### GenerateData:

Cette classe permet de générer les fichiers de transactions et les fichiers de réferéntiels produits : transactions_YYYYMMDD.data, reference_prod-ID_MAGASIN_YYYYMMDD.data

Elle prend comme arguments le path, la date de début et le nombre de jour a générer. Les principales méthodes sont generateTx et generateRef :

generateTx : Pour créer les fichiers transactions_YYYYMMDD.data contenant les données des transactions. Chaque ligne du fichier est une transaction, elle est sous la forme txId|datetime|magasin|produit|qte .

generateRef : Pour créer les fichiers reference_prod-ID_MAGASIN_YYYYMMDD.data contenant les données référentiels. Une ligne du fichier est sous la forme produit|prix

où:

txId : id de transaction (nombre) datetime : date et heure au format ISO 8601 magasin : UUID identifiant le magasin produit : id du produit (nombre) qte : quantité (nombre) prix : prix du produit en euros

La méthode getRandomMagasinId renvoi un id_magasin (exp 72a2876c-bc8b-4f35-8882-8d661fac2606) en utilisant la fonction randomString. randomString prend en paramètre la taille de la chaine de caractère a générer d'une facon aléatoire, par exemple un id_magasin une concaténation d'un ensemble de chaines séparées par le caractère "-" tout en respectant la taille de chaque chaine.

### ComputeTopHundred:

Cette classe permet de répondre au besoin fonctionnelle du projet en générant les fichiers : top_100_ventes_<ID_MAGASIN>YYYYMMDD.data,top_100_ca<ID_MAGASIN>_YYYYMMDD.data, top_100_ventes_<ID_MAGASIN>YYYYMMDD-J7.data, top_100_ca<ID_MAGASIN>_YYYYMMDD-J7.data

Elle prend en paramètre la date du process, et le nombre de jour X a traité. Pour rèpondre au besoin du projet il faut mettre X a 1 puis a 7.

computeTopHundred : cette fonction traite les données par id_magasin puis par id_produit pour calculer les 100 produits qui ont les meilleures ventes et ceux qui génèrent le plus gros Chiffre d'Affaire par magasin.


## Exécution:

#### 1. Pour générer les données: 

Utile pour génerer un grand volume de données afin de tester la capacité de l'application à traiter une grande volumeterie.

java -Xmx512m -cp target/PhenixChallenge-1.0-SNAPSHOT-jar-with-dependencies.jar phenixChallenge.dataGeneration.GenerateData {date} {nbrJour} {path} {nbRows} {nbProduct} {nbMagasin}


#### 2. Pour lancer le traitement:

java -Xmx512m -jar target/PhenixChallenge-1.0-SNAPSHOT-jar-with-dependencies.jar {date} {data}