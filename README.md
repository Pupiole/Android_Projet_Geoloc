# Android_Projet_Geoloc
Android_Project_GeoLoc

Notre application est composée de 5 pages :
Une page d'accueil
Lancer partie
Choix du parcours
Edition d'un parcours
Une page de jeu
Carte géolocalisant le joueur
Indice menant à la prochaine balise
Fenêtre modale félicitant le joueur et le renvoyant vers la page d'accueil
Une page d'édition de parcours
Nom du parcours
Liste des balises déjà créées et pouvant être éditées ou supprimées
Ajout d'une nouvelle balise
Création d'une nouvelle balise
Coordonnées de la balise
Texte
Image
Choix des coordonnées
Carte permettant à l'utilisateur de sélectionner une coordonnée
Par défaut, la carte et la balise sont placées là où se trouve l'utilisateur lorsqu'il ouvre cette page
Bouton validant la coordonnées
Recherche d'une adresse

L'utilisateur lance l'application. Il arrive sur la page d'accueil. S'il n'a pas activé sa géolocalisation, une fenêtre modale s'ouvre pour lui demander de l'activer puis l'application quitte. Si la géolocalisation est activé, il peut sélectionner son parcours dans un menu déroulant. Ces parcours sont stockés dans un fichier JSON de la forme :
 Balises {
	Coordonnée : {
		Longitude : double,
		Latitude : double
	},
	Indice : {
		Image : String,
		Texte : String
	}
}
L'utilisateur lance la partie grâce au bouton « Lancer partie ». Il arrive sur une nouvelle page qui est remplie d'une carte. Sa localisation est indiquée par une balise toujours située au centre de la carte. Lorsque l'utilisateur se déplace, la carte se déplace avec lui. En haut de l'écran se trouve un panel qui peut être affiché ou caché à l'aide d'un bouton. Ce panel présente l'indice pour atteindre la prochaine borne, sous forme d'un texte et/ou d'une image. Lorsque l'utilisateur arrive aux environs de la borne (5 mètres environ), elle est validée et un nouvelle indice apparaît (le panel s'ouvre même s'il était fermé). Potentiellement, un symbole flash à l'écran un cours instant pour prévenir l'utilisateur. Lorsque l'utilisateur arrive à la dernière balise, une fenêtre modale apparaît pour le féliciter, et l'invite à revenir sur la page d'accueil (bouton).

L'utilisateur peut créer un nouveau parcours à partir de la page d'accueil, en appuyant sur le bouton « Edition d'un parcours ». Il est envoyé sur une nouvelle page. Il peut indiquer le nom de son parcours et créer une nouvelle balise. Lorsqu'il veut créer une nouvelle balise, une nouvelle page s'affiche. Lorsqu'il veut sélectionner des coordonnées, une carte s'affiche sur une nouvelle page. Elle est centrée sur ses coordonnées actuelles. Il peut sélectionner ses coordonnées en touchant son écran. Il peut afficher une adresse spécifique grâce à une champ texte de recherche. Il peut valider ses coordonnées grâce à un bouton situé en bas à droite. Une fois validé, il retourne sur la page précédente avec ses coordonnées renseignées. Il peut ajouter un texte et une image à partir de ses fichiers. Il ne peut valider sa balise si aucun de ces deux éléments n'est rempli. Lorsqu'il valide sa balise, il est renvoyé sur la page de son parcours en cours d'édition. Il peut alors voir la liste de ses balises, qu'il peut modifier ou supprimer. Il peut continuer à rajouter des balises, ou valdier son parcours.
