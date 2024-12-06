package org.ormi.priv.tfa.orderflow.product.registry.service.producer;

import java.util.concurrent.CompletionStage;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.ormi.priv.tfa.orderflow.lib.publishedlanguage.event.ProductRegistered;
import org.ormi.priv.tfa.orderflow.lib.publishedlanguage.event.ProductRegistryEvent;
import org.ormi.priv.tfa.orderflow.lib.publishedlanguage.event.ProductRemoved;
import org.ormi.priv.tfa.orderflow.lib.publishedlanguage.event.ProductUpdated;
import org.ormi.priv.tfa.orderflow.lib.publishedlanguage.event.config.ProductRegistryEventChannelName;
import org.ormi.priv.tfa.orderflow.product.registry.exception.ConsumerCreationException;
import org.ormi.priv.tfa.orderflow.product.registry.exception.ProducerCreationException;
import org.ormi.priv.tfa.orderflow.product.registry.exception.ProductRegistryEventException;

import io.quarkus.logging.Log;
import io.smallrye.reactive.messaging.pulsar.PulsarClientService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * The product registry event producer.
 * Produces events to the product registry event channel so it can be watched on
 * other services.
 */
@ApplicationScoped
public class ProductRegistryEventEmitter {

  /**
   * The Pulsar client service.
   */
  @Inject
  private PulsarClientService pulsarClients;

  /**
   * Event emitter to send events to the read model.
   */
  @Channel("product-registry-event")
  private Emitter<ProductRegistryEvent> eventEmitter;

  /**
   * Project the event.
   * 
   * @param event - the event to project
   * @throws ProductRegistryEventException if an error occurs while projecting the event
   */
  public void emit(ProductRegistryEvent event) throws ProductRegistryEventException {
    Log.debug("Projecting event: " + event.toString());
    if (event instanceof ProductRegistered registered) {
      emitRegisteredProduct(registered);
    } else if (event instanceof ProductUpdated updated) {
      projectUpdatedProduct(updated);
    } else if (event instanceof ProductRemoved removed) {
      emitRemovedProduct(removed);
    }
  }

  /**
   * Emit a product registered event.
   * 
   * @param registered - the event to emit
   * @throws ProductRegistryEventException if an error occurs while emitting the event
   */
  void emitRegisteredProduct(ProductRegistered registered) throws ProductRegistryEventException {
    try {
      eventEmitter.send(registered);
    } catch (Exception e) {
      throw new ProductRegistryEventException("Failed to emit registered product event.", e);
    }
  }

  /**
   * Emit a product updated event.
   * 
   * @param updated - the event to emit
   * @throws ProductRegistryEventException if an error occurs while emitting the event
   */
  void projectUpdatedProduct(ProductUpdated updated) throws ProductRegistryEventException {
    try {
      eventEmitter.send(updated);
    } catch (Exception e) {
      throw new ProductRegistryEventException("Failed to emit updated product event.", e);
    }
  }

  /**
   * Emit a product removed event.
   * 
   * @param removed - the event to project
   * @throws ProductRegistryEventException if an error occurs while emitting the event
   */
  void emitRemovedProduct(ProductRemoved removed) throws ProductRegistryEventException {
    try {
      eventEmitter.send(removed);
    } catch (Exception e) {
      throw new ProductRegistryEventException("Failed to emit removed product event.", e);
    }
  }

  /**
   * Produce the given event with the given correlation id.
   * 
   * @param correlationId - the correlation id
   * @param event         - the event
   * @throws ProductRegistryEventException if an error occurs while producing the event
   */
  public void sink(String correlationId, ProductRegistryEvent event) throws ProductRegistryEventException {
    getEventSinkByCorrelationId(correlationId)
        .thenAccept((producer) -> {
          try {
            producer.newMessage()
                    .value(event)
                    .sendAsync()
                    .whenComplete((msgId, ex) -> {
                      if (ex != null) {
                        throw new ProducerCreationException("Failed to produce event for correlation id: " + correlationId, ex);
                      }
                      Log.debug(String.format("Sinked event with correlation id{%s} in msg{%s}", correlationId, msgId));
                      try {
                        producer.close();
                      } catch (PulsarClientException e) {
                        throw new ProducerCreationException("Failed to close producer", e);
                      }
                    });
          } catch (Exception ex) {
            throw new ProducerCreationException("Error while sending event.", ex);
          }
        });
  }

  /**
   * Create a producer for the given correlation id.
   * 
   * @param correlationId - the correlation id
   * @return the producer
   * @throws ProducerCreationException if an error occurs while creating the producer
   */
  private CompletionStage<Producer<ProductRegistryEvent>> getEventSinkByCorrelationId(String correlationId) throws ProducerCreationException {
    final String channelName = ProductRegistryEventChannelName.PRODUCT_REGISTRY_EVENT.toString();
    final String topic = channelName + "-" + correlationId;
    try {
      return pulsarClients.getClient(channelName)
          .newProducer(Schema.JSON(ProductRegistryEvent.class))
          .producerName(topic)
          .topic(topic)
          .createAsync()
          .exceptionally(ex -> {
            throw new ProducerCreationException("Failed to create producer for correlation id: " + correlationId, ex);
          });
    } catch (Exception ex) {
      throw new ProducerCreationException("Error creating producer.", ex);
    }
  }
}