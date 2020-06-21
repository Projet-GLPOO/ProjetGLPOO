# ProjetGLPOO

## AUTHORS:
	-Rayan Kanawati
	-Paul Michaux
	-Alexandre Piga
	-David Touitou
	-David Xia

## Outils de Développement

# IDE
IntelliJ

# Base de données
H2

## Lien GitHub

https://github.com/Projet-GLPOO/ProjetGLPOO

## Lien Trello

https://trello.com/b/0Oz8VOIx/projet-glpoo

## Description

Ce projet consistait en la réalisation d'une "chatroom" permettant un échange écrit entre plusieurs participants. 
Cette application met en place une base de donnée (via le logiciel H2), un serveur et des clients (le tout local).
Elle met en également en place des interfaces graphiques permettant une navigation plus intuitive.


## Comment fonctionne-t-elle ?

Tout d'abord, il vous faudra lancé la base de donnée H2 après avoir mis le fichier au bon endroit (C:\\Users\\"nom d'utilisateur"\\"le fichier.mv.db"). 
L'utilisateur est "sa" et le mot de passe est "glpoo".

Quand vous lancez l'application, vous arrivez sur une interface vous demandant un nom d'utilisateur et un mot de passe (username: Guest /// mot de passe: glpoo).
Une fois connecté, vous pouvez choisir un groupe pour commencer à discuter avec d'autres utilisateurs (connectés en local), ou créer un nouveau groupe (cette fonctionnalité ne fonctionne que du coté de la base de donnée et nécessite de cliquer sur le bouton de rafraichissement).
Bien entendu, seuls les utilisateurs faisant partis du groupe pourront voir les messages que vous envoyez.
Vous pouvez également supprimer les messages que vous avez envoyé via le bouton de suppression des messages. Cela sera instantané de votre coté et dans la base de données, mais nécessitera un rafraichissement de la part de vos interlocuteurs (double clic sur le groupe ayant contenu le message).

Pour connecter plusieurs clients, il vous suffit de rentrer différents username/mot de passe sur l'interface de connexion qui ne se ferme pas automatiquement.

## Problèmes et bugs connus

	-Faire attention à mettre son fichier "h2-1.4.200.jar" (ressemble à ça et ce trouve dans votre dossier d'installation d'H2 "C:\Program FIles (x86)\H2\bin\h2-1.4.200.jar").
		Dans l'IDE Intelij : file -> Project structure -> Librarie et remplacer ou ajouter le fichier .jar
	-Impossibilité de fermer les inputs, outputs, et sockets parce que l'architecture de notre programme ne le permet pas. Cela crée donc un bug lorsqu'un client est fermé et que le serverur reste ouvert.
	-Possibilité de créer un groupe sans participants. Nous aurions pu créer un trigger afin d'empêcher cela (controle sur la table GROUPES et PARTICIPANTSGROUPE).
	-Possibilité de créer un groupe sans nom. Bug provenant d'H2 car la contraite "not null" est bien présente pour ce paramètre.
	-Possibilité de créer un groupe sans limite de participants à la différence de ce qui avit été prévue
	-Possibilité de se connecter de façon simultanée au compte d'un même utilisateur.
	-Nécessité de cliquer plusieurs fois sur login, car bug sporadique d'input.
	-Le script Maven ne fonctionne pas dû à un warning lors de la compilation.
