package com.epam.onlinepharmacy.email;

import com.epam.onlinepharmacy.exceptions.ApplicationException;
import com.epam.onlinepharmacy.main.AbstractProgramConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;
import javax.mail.Transport;
import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * This class stores static method that works with e-mail.
 *
 * @author Nikita
 * @version 1.0
 * @since 19.10.2018
 */
public final class SendingEmail {

    /**
     * Logger for debug.
     */
    private static final Logger LOGGER;

    static {
        LOGGER = LogManager.getLogger(SendingEmail.class);
    }

    /**
     * Private default constructor.
     */
    private SendingEmail() {
        // This constructor is intentionally empty.
        // Nothing special is needed here.
    }

    /**
     * This method sends message on the e-mail address.
     *
     * @param email   value of the user email
     * @param message value of the message
     * @param subject value of the subject
     * @throws ApplicationException throw MessagingException
     */
    public static void send(final String email,
                            final String subject,
                            final String message)
            throws ApplicationException {

        final Properties properties = new Properties();
        final MimeMessage mes;
        final String debugString = " Message sent from "
                + AbstractProgramConstant.EMAIL + " to " + email;

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.mail.ru");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(properties,
                new Authenticator() {
                    /**
                     * {@inheritDoc}
                     * */
                    @Override
                    protected PasswordAuthentication
                    getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                AbstractProgramConstant.EMAIL,
                                AbstractProgramConstant.EMAIL_PASSWORD);
                    }
                });

        mes = new MimeMessage(session);

        try {

            mes.setFrom(AbstractProgramConstant.EMAIL);
            mes.addRecipients(Message.RecipientType.TO, email);
            mes.setSubject(subject);
            mes.setText(message);

            Transport.send(mes);

            LOGGER.log(Level.DEBUG, debugString);

        } catch (MessagingException e) {
            throw new ApplicationException(e.getMessage());
        }


    }

}
