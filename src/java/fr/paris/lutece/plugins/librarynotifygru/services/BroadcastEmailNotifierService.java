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

import fr.paris.lutece.plugins.grubusiness.business.notification.BroadcastNotification;
import fr.paris.lutece.plugins.grubusiness.business.notification.EmailAddress;
import fr.paris.lutece.plugins.grubusiness.business.notification.EmailNotification;
import fr.paris.lutece.plugins.grubusiness.business.notification.EnumNotificationType;
import fr.paris.lutece.plugins.grubusiness.business.notification.Event;
import fr.paris.lutece.plugins.grubusiness.business.notification.Notification;
import fr.paris.lutece.plugins.grubusiness.business.notification.NotifyGruResponse;
import fr.paris.lutece.plugins.grubusiness.service.notification.INotifierServiceProvider;
import fr.paris.lutece.plugins.grubusiness.service.notification.NotificationException;
import fr.paris.lutece.portal.service.mail.MailService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class BroadcastEmailNotifierService implements INotifierServiceProvider
{
    private static final String ADRESS_SEPARATOR = ";";
    private static final String PROPERTY_EMAIL_PATTERN = "lutece.email.pattern";
    private static String EMAIL_REGEXP_PATTERN = AppPropertiesService.getProperty( PROPERTY_EMAIL_PATTERN );
    
    
    @Override
    public String getName() {
        return this.getClass( ).getName( );
    }
    
    @Override
    public List<EnumNotificationType> getNotificationTypes( )
    {
	return Arrays.asList(EnumNotificationType.BROADCAST_EMAIL);
    }
    
    @Override
    public NotifyGruResponse process ( Notification notification ) throws NotificationException
    {
	if ( notification.getBroadcastEmail( ) != null && !notification.getBroadcastEmail( ).isEmpty( ) )
        {
            return sendBroadcastEmail( notification );
        }
        
        return null;
    }

    /**
     * send Broadcast email
     * 
     * @param notification
     */
    public NotifyGruResponse sendBroadcastEmail( Notification notification )
    {
	NotifyGruResponse gruResponse = new NotifyGruResponse ( );
	gruResponse.setStatus ( NotifyGruResponse.STATUS_RECEIVED );
        
        EmailNotification gruEmail = null;

            for ( BroadcastNotification notifBroadcast : notification.getBroadcastEmail( ) )
            {
                gruEmail = new EmailNotification( );
                gruEmail.setRecipient( buildEmailAdresses( notifBroadcast.getRecipient( ), gruResponse ) );
                gruEmail.setCc( buildEmailAdresses( notifBroadcast.getCc( ), gruResponse ) );
                gruEmail.setBcc( buildEmailAdresses( notifBroadcast.getBcc( ), gruResponse ) );
                gruEmail.setSenderEmail( notifBroadcast.getSenderEmail( ) );
                gruEmail.setSenderName( notifBroadcast.getSenderName( ) );
                gruEmail.setSubject( notifBroadcast.getSubject( ) );
                gruEmail.setMessage( notifBroadcast.getMessage( ) );
                
                sendEmail( gruEmail );
            }
        
            return gruResponse;
    }

    /**
     * build mail adresses 
     * 
     * @param lstEmailAdress
     * @param gruResponse 
     * @return the mail adresses
     */
    private String buildEmailAdresses( List<EmailAddress> lstEmailAdress, NotifyGruResponse gruResponse )
    {
        StringBuilder strEmailAdresses = new StringBuilder( );

        if ( ( lstEmailAdress != null ) && !lstEmailAdress.isEmpty( ) )
        {
            for ( EmailAddress emailAddress : lstEmailAdress )
            {
        	if ( StringUtils.isEmpty ( emailAddress.getAddress( ) ) 
        		|| !emailAddress.getAddress( ).matches ( EMAIL_REGEXP_PATTERN ) )
                {
        	    	gruResponse.setStatus ( NotifyGruResponse.STATUS_ERROR );
        		Event event = new Event( );
        		event.setMessage ( "Invalid Email address [" + emailAddress.getAddress( )  + "]");
        		event.setStatus ( NotifyGruResponse.STATUS_ERROR  );
        		gruResponse.getErrors ( ).add ( event );
                }
        	else
        	{
        	    // address is valid
                    if ( strEmailAdresses.length( ) > 0 )
                    {
                	strEmailAdresses.append( ADRESS_SEPARATOR );
                    }
                    strEmailAdresses.append( emailAddress.getAddress( ) );
        	}
            }
        }

        return strEmailAdresses.toString( );
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
