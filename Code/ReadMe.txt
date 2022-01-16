1. COMPILATION (ARBORESCENCE COURANT)
	javac Etat/*.java Utility/*.java Main/*.java

2. EXECUTION (ARBORESCENCE COURANT)
	
	Sans paramètres
		java Main/Main.java
	
	... ou avec paramètres (k = taille du graphe / n = Nombre d'états recherche local*)
		java Main/Main.java [k] [n]
	

* La taille de la population pour l'algorithme génétique
* Le nombre d'états traqué pour Local Beam