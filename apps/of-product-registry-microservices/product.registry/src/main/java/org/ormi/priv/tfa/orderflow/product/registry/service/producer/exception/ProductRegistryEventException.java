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
 * Exception de base pour les erreurs liées aux événements de registre de produit.
 */
public class ProductRegistryEventException extends Exception {

    /**
     * Constructeur avec un message d'erreur.
     * 
     * @param message - Le message d'erreur
     */
    public ProductRegistryEventException(String message) {
        super(message);
    }

    /**
     * Constructeur avec un message d'erreur et une cause.
     * 
     * @param message - Le message d'erreur
     * @param cause   - La cause de l'exception
     */
    public ProductRegistryEventException(String message, Throwable cause) {
        super(message, cause);
    }
}