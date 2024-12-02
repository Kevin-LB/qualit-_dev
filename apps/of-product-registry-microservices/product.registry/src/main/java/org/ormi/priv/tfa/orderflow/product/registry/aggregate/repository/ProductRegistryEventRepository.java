package org.ormi.priv.tfa.orderflow.product.registry.aggregate.repository;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

import org.ormi.priv.tfa.orderflow.lib.event.sourcing.store.EventStore;
import org.ormi.priv.tfa.orderflow.product.registry.aggregate.repository.model.ProductRegistryEventEntity;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.panache.common.Sort;

/**
 * Référentiel pour la gestion des événements du registre de produits.
 * Implémente à la fois {@link EventStore} et {@link PanacheMongoRepository}, permettant
 * de stocker et de récupérer des événements spécifiques au registre de produits.
 */
@ApplicationScoped
public class ProductRegistryEventRepository
    implements EventStore<ProductRegistryEventEntity>,
    PanacheMongoRepository<ProductRegistryEventEntity> {

  /**
   * Sauvegarde un événement dans le référentiel.
   *
   * @param event l'événement à sauvegarder.
   */
  @Override
  public void saveEvent(ProductRegistryEventEntity event) {
    persist(event);
  }

  /**
   * Récupère une liste d'événements à partir d'un identifiant de racine d'agrégation
   * et d'une version de départ.
   *
   * @param aggregateRootId l'identifiant de la racine d'agrégation des événements.
   * @param startingVersion la version minimale des événements à inclure.
   * @return une liste des entités d'événements correspondant aux critères.
   */
  @Override
  public List<ProductRegistryEventEntity> findEventsByAggregateRootIdAndStartingVersion(
      String aggregateRootId,
      long startingVersion) {
    return find(
        "aggregateRootId = ?1 and version > ?2",
        aggregateRootId,
        startingVersion,
        Sort.by("version"))
        .list();
  }
}