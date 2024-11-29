https://iuto.gitlab.cloud0.openrichmedia.org/quali-dev-order-flow/material/fr/

## Tâche 1 : Questions

1. Quels sont les principaux domaines métiers de l'application Order flow ?

> Il y a 3 domaines :
> * Les domaines principaux (Panier d'achats, ...)
> * Les supporting domaines (Registre des produits, ...)
> * Les domaines génériques (Notification, ...)

2. Comment les microservices sont-ils conçus pour implémenter les domaines métiers ?

> Les microservices sont implémentés en utilisant un message broker pour faciliter la communication asynchrone entre eux. 
>
> Le projet ne suit pas une nomenclature classique avec des “contrôleurs”. À la place, des classes appelées “Ressources” sont utilisées pour structurer les interactions.
>
> Le modèle CQRS (Command Query Responsibility Segregation) est adopté, ce qui permet de séparer les opérations d’écriture et de lecture. Chaque microservice peut publier des messages sur le broker, en générant un identifiant unique pour garantir l’intégrité des échanges et identifier l’émetteur. Cette architecture favorise une communication scalable et découplée, idéale pour les domaines complexes.


3. Quelles sont les responsabilités des conteneurs de code apps/of-api-gateway, apps/of-product-registry-microservices/product.registry, apps/of-product-registry-microservices/product.registry.read, libs/event-sourcing, libs/published-language ?

> apps/of-api-gateway : Ce conteneur agit comme une passerelle centralisée pour orchestrer les requêtes et les réponses entre les clients et les différents microservices.
>
> apps/of-product-registry-microservices/product.registry : Il s’agit d’un microservice dédié à la gestion des produits. Il est responsable des opérations de mutation, comme la création, la mise à jour et la suppression des produits. Ce service gère également la logique métier liée aux produits.
>
> apps/of-product-registry-microservices/product.registry.read : Ce microservice est spécialisé dans les opérations de lecture des informations produits. Il est optimisé pour fournir des données rapidement et peut être conçu pour des requêtes complexes tout en restant découplé des mutations grâce au modèle CQRS.
>
> libs/event-sourcing : Une bibliothèque support qui implémente le pattern Event Sourcing. Elle enregistre toutes les transitions d’état d’un objet sous forme d’événements immuables, créant ainsi un historique complet des modifications. Cela permet une reconstitution précise de l’état d’un objet à n’importe quel moment dans le temps.
>
> libs/published-language : Cette bibliothèque vise à normaliser et unifier le langage utilisé par les différents microservices. Elle garantit que tous les microservices utilisent les mêmes termes et concepts métier, facilitant ainsi la communication et la compréhension à l’échelle du système.


## Tâche 2 : Questions

Quels sont les concepts principaux utilisés dans l'application Order flow ?

> Les concepts principaux utilisés dans l’application Order Flow sont les suivants :
>
> Domaines métiers et agrégation :
> L’application est organisée autour de domaines métiers, où chaque domaine traite des données en fonction de sa logique métier via des chemins d’agrégation.
>
> Matérialisation des logiques métiers :
> Les logiques métiers sont explicitement définies et intégrées dans l’application pour assurer une gestion claire des règles et processus à chaque étape.
>
> Enregistrement des événements (transitions d’état) :
>Les changements d’état des objets sont enregistrés sous forme d’événements immuables, permettant de suivre l’historique et de reconstituer l’état à tout moment.
>
> Architecture des événements :
>L’architecture des événements permet une communication asynchrone entre microservices, en transmettant des informations via des événements déclenchés par des changements dans le système.
>
>Séparation des responsabilités entre commandes et requêtes (CQRS) :
>Le modèle CQRS sépare les opérations de modification (commandes) et de lecture (requêtes), optimisant ainsi les performances et la scalabilité du système.

Comment les concepts principaux sont-ils implémentés dans les microservices ?

> Quarkus : Framework utilisé pour la gestion des dépendances et l’implémentation des microservices, assurant la couche applicative.
> Pulsar : Utilisé pour la communication entre les microservices, facilitant le découpage et l’intégration des services via un message broker.
> MongoDB : Base de données utilisée pour la gestion des données persistantes des microservices.
> Event Sourcing : Gère les événements et leurs transitions, garantissant la traçabilité et la reconstitution des états des objets.
> Gradle : Outil de gestion des dépendances et de construction du projet, facilitant l’automatisation des tâches.


Que fait la bibliothèque libs/event-sourcing ? Comment est-elle utilisée dans les microservices (relation entre métier et structure du code) ?

> La bibliothèque libs/event-sourcing implémente le pattern Event Sourcing pour enregistrer toutes les transitions d’état sous forme d’événements, permettant aux microservices de maintenir un historique immuable des changements. Elle est utilisée dans chaque microservice pour garantir que les objets métiers sont suivis à travers leurs différentes versions.

Comment l'implémentation actuelle de l'event-sourcing assure-t-elle la fiabilité des états internes de l'application ?

> La fiabilité des états internes est assurée par l’utilisation de l’aggregate ID, qui permet de relier chaque événement à un objet spécifique, ainsi que le numéro de version, garantissant la cohérence et l’intégrité des transitions d’état à chaque modification.