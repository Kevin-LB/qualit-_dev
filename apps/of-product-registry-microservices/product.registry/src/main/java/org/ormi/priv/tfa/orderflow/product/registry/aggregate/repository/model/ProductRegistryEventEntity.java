package org.ormi.priv.tfa.orderflow.product.registry.aggregate.repository.model;

import org.bson.types.ObjectId;
import io.quarkus.mongodb.panache.common.MongoEntity;

/**
 * Classe abstraite représentant une entité d'événement de registre de produit.
 * Cette classe est utilisée comme modèle de base pour différents types d'événements de registre de produit.
 * Elle définit les propriétés communes à tous les événements de registre de produit.
 */
@MongoEntity(collection = "product_registry_events")
public abstract class ProductRegistryEventEntity {

  /**
   * L'identifiant unique de l'entité d'événement dans la base de données.
   */
  public ObjectId id;

  /**
   * L'identifiant unique de l'événement.
   */
  public String eventId;

  /**
   * Le type de l'événement, initialisé par la méthode {@link #getEventType()}.
   */
  public String eventType = getEventType();

  /**
   * L'identifiant de la racine d'agrégation liée à cet événement.
   */
  public String aggregateRootId;

  /**
   * La version de l'événement, utilisée pour gérer la version des entités.
   */
  public long version;

  /**
   * L'horodatage de l'événement (généralement sous forme de timestamp Unix).
   */
  public long timestamp;

  /**
   * Méthode abstraite permettant d'obtenir le type d'événement.
   * Les sous-classes doivent implémenter cette méthode pour définir le type d'événement spécifique.
   *
   * @return le type d'événement sous forme de chaîne de caractères.
   */
  abstract String getEventType();
}