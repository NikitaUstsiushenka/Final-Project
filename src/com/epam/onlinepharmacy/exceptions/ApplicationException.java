package com.epam.onlinepharmacy.exceptions;

/**
 * Class exception "ApplicationException".
 *
 * @author Nikita
 * @since 11.09.2018
 * @version 1.0
 * */
public final class ApplicationException extends Exception {

    /**
     * Public default constructor.
     * */
    public ApplicationException() {

        super();

    }

    /**
     * Public initialize constructor.
     *
     * @param throwable value of the object Throwable
     * */
    public ApplicationException(final Throwable throwable) {

        super(throwable);

    }

    /**
     * Public initialize constructor.
     *
     * @param newMessage new value of the exception message
     * */
    public ApplicationException(final String newMessage) {

        super(newMessage);

    }

    /**
     * Public initialize constructor.
     *
     * @param newMessage new value of the exception message
     * @param throwable new value of the object Throwable
     * */
    public ApplicationException(final String newMessage,
                                final Throwable throwable) {

        super(newMessage, throwable);

    }

}
