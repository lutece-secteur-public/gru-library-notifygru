/*
 * Copyright (c) 2002-2015, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.librarynotifygru.services;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.plugins.grubusiness.business.notification.EmailNotification;
import fr.paris.lutece.plugins.grubusiness.business.notification.EnumNotificationType;
import fr.paris.lutece.plugins.grubusiness.business.notification.Event;
import fr.paris.lutece.plugins.grubusiness.business.notification.Notification;
import fr.paris.lutece.plugins.grubusiness.business.notification.NotifyGruResponse;
import fr.paris.lutece.plugins.grubusiness.service.notification.INotifierServiceProvider;
import fr.paris.lutece.plugins.grubusiness.service.notification.NotificationException;
import fr.paris.lutece.portal.service.mail.MailService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

public class EmailNotifierService implements INotifierServiceProvider
{
    private static final String ADRESS_SEPARATOR = ";";
    private static final String PROPERTY_EMAIL_PATTERN = "lutece.email.pattern";
    private static String EMAIL_REGEXP_PATTERN = AppPropertiesService.getProperty( PROPERTY_EMAIL_PATTERN );
    
    /** constructor */
    EmailNotifierService( )
    {
    }

    @Override
    public List<EnumNotificationType> getNotificationTypes( )
    {
	return Arrays.asList( EnumNotificationType.CUSTOMER_EMAIL );
    }
    
    @Override
    public String getName() {
        return this.getClass( ).getName( );
    }
    
    @Override
    public NotifyGruResponse process ( Notification notification ) throws NotificationException
    {
	if ( notification.getEmailNotification( ) != null )
        {
            return sendEmail( notification );
        }
        
        return null;
    }
    
    /**
     * send Email
     * 
     * @param notification
     * @return 
     */
    public NotifyGruResponse sendEmail( Notification notification )
    {
	NotifyGruResponse consolidatedResponse = new NotifyGruResponse ( );
	consolidatedResponse.setStatus ( NotifyGruResponse.STATUS_RECEIVED );
	
	
	String recipient = notification.getEmailNotification( ).getRecipient ( );
        if ( StringUtils.isEmpty ( recipient ) || !recipient.matches ( EMAIL_REGEXP_PATTERN ) )
        {
    		consolidatedResponse.setStatus ( NotifyGruResponse.STATUS_ERROR );
		Event event = new Event( );
		event.setMessage ( "Invalid Email address" );
		event.setStatus ( NotifyGruResponse.STATUS_ERROR  );
		consolidatedResponse.getErrors ( ).add ( event );
        }
        else
        {
            EmailNotification gruEmail = new EmailNotification( );
            EmailNotification notifEmail = notification.getEmailNotification( );
            
            gruEmail.setRecipient( notifEmail.getRecipient( ) );
            gruEmail.setCc( notifEmail.getCc( ) );
            gruEmail.setBcc( notifEmail.getCci( ) );
            gruEmail.setSenderEmail( notifEmail.getSenderEmail( ) );
            gruEmail.setSenderName( notifEmail.getSenderName( ) );
            gruEmail.setSubject( notifEmail.getSubject( ) );
            gruEmail.setMessage( notifEmail.getMessage( ) );

            sendEmail( gruEmail );
        }
        
        return consolidatedResponse;
    }

    /**
     * Send Email
     * 
     * @param email
     */
    public void sendEmail( EmailNotification email )
    {
        MailService.sendMailHtml( email.getRecipient( ), email.getCc( ), email.getCci( ), email.getSenderName( ), email.getSenderEmail( ), email.getSubject( ),
                email.getMessage( ) );
    }


}
