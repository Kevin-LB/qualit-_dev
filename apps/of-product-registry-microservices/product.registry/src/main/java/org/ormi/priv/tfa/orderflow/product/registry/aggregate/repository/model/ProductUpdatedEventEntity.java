package org.ormi.priv.tfa.orderflow.product.registry.aggregate.repository.model;

/**
 * Représente une entité d'événement de produit mis à jour.
 */
public class ProductUpdatedEventEntity extends ProductRegistryEventEntity {

  /**
   * Le type de l'événement, indiquant qu'il s'agit d'un événement "ProductUpdated".
   */
  static final String EVENT_TYPE = "ProductUpdated";

  /**
   * Payload for the event.
   */
  public static class Payload {
    /**
     * The id of the product.
     */
    public String productId;
    /**
     * The name of the product.
     */
    public String name;
    /**
     * The description of the product.
     */
    public String productDescription;
  }

  /**
   * The payload for the event.
   */
  public Payload payload;

  /**
   * Récupère le type d'événement pour cette entité.
   *
   * @return le type d'événement, qui est toujours {@code "ProductUpdated"}.
   */
  @Override
  public String getEventType() {
    return EVENT_TYPE;
  }
}
