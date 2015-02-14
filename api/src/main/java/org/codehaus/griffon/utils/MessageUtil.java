package org.codehaus.griffon.utils;

import java.util.ResourceBundle;
  
/**
 * Class for retrieve the messages from ResourceBundle.
 *
 * @author Rajmahendra Hegde <rajmahendra@gmail.com>
 */
public enum MessageUtil {

    /**
     * Instance of MessageUtil
     */
    properties;

    /**
     * The bundle.
     */
    private final ResourceBundle bundle;

    /**
     * Instantiates a new messages.
     */
    private MessageUtil() {
        bundle = ResourceBundle.getBundle("ResourceBundle");
    }

    /**
     * Gets the key value.
     *
     * @param key the key
     * @return the key value
     */
    public String getKeyValue(final String key) {
        return bundle.getString(key);
    }

    /**
     * Gets the message.
     *
     * @param key the key
     * @return the message
     */
    public String getMessage(final String key) {
        return bundle.getString("message." + key);
    }

    /**
     * Gets the metadata value.
     *
     * @param key the key
     * @return the message
     */
    public String getMetadataValue(final String key) {
        return bundle.getString("metadata." + key);
    }

    /**
     * Gets the message.
     *
     * @param key  the key
     * @param args the args
     * @return the message
     */
    public String getMessage(final String key, final Object... args) {
        return String.format(bundle.getString("message." + key), args);

    }
}
