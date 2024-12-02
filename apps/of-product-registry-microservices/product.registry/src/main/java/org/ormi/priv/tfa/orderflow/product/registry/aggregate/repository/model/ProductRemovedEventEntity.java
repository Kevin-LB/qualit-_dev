package org.ormi.priv.tfa.orderflow.product.registry.aggregate.repository.model;

/**
 * Représente une entité d'événement de produit supprimé.
 */
public class ProductRemovedEventEntity extends ProductRegistryEventEntity {

  /**
   * Le type de l'événement, indiquant qu'il s'agit d'un événement "ProductRemoved".
   */
  static final String EVENT_TYPE = "ProductRemoved";

  /**
   * Payload for the event.
   */
  public static class Payload {
    /**
     * The id of the product.
     */
    public String productId;
  }

  /**
   * The payload for the event.
   */
  public Payload payload;

  /**
   * Récupère le type d'événement pour cette entité.
   *
   * @return le type d'événement, qui est toujours {@code "ProductRemoved"}.
   */
  @Override
  public String getEventType() {
    return EVENT_TYPE;
  }
  
}
