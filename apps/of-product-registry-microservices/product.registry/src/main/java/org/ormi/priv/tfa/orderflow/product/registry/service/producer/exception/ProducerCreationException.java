package org.ormi.priv.tfa.orderflow.product.registry.service.producer.exception;

/**
 * Exception spécifique pour les erreurs liées à la création de consommateurs.
 */
public class ConsumerCreationException extends ProductRegistryEventException {

    /**
     * Constructeur avec un message d'erreur.
     * 
     * @param message - Le message d'erreur
     */
    public ConsumerCreationException(String message) {
        super(message);
    }

    /**
     * Constructeur avec un message d'erreur et une cause.
     * 
     * @param message - Le message d'erreur
     * @param cause   - La cause de l'exception
     */
    public ConsumerCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
/**
 * Exception spécifique pour les erreurs liées à la création de producteurs.
 */
public class ProducerCreationException extends ProductRegistryEventException {

    /**
     * Constructeur avec un message d'erreur.
     * 
     * @param message - Le message d'erreur
     */
    public ProducerCreationException(String message) {
        super(message);
    }

    /**
     * Constructeur avec un message d'erreur et une cause.
     * 
     * @param message - Le message d'erreur
     * @param cause   - La cause de l'exception
     */
    public ProducerCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}