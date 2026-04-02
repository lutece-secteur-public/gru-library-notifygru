/*
 * Copyright (c) 2002-2026, City of Paris
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.paris.lutece.plugins.grubusiness.business.notification.EnumNotificationType;
import fr.paris.lutece.plugins.grubusiness.business.notification.Event;
import fr.paris.lutece.plugins.grubusiness.business.notification.Notification;
import fr.paris.lutece.plugins.grubusiness.business.notification.NotifyGruResponse;
import fr.paris.lutece.plugins.grubusiness.service.notification.INotifierServiceProvider;
import fr.paris.lutece.plugins.grubusiness.service.notification.NotificationException;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;


/**
 * NotificationService service
 */
@ApplicationScoped
public class NotificationService
{
    private List<INotifierServiceProvider> _notifiers;
	private Instance<INotifierServiceProvider> _notifiersInstance;
	
	public NotificationService( ){}

    @Inject
    public NotificationService( Instance<INotifierServiceProvider> notifiersInstance ) 
    {
        this._notifiersInstance = notifiersInstance;
    }

	@PostConstruct
	public void init( )
	{
		_notifiers = _notifiersInstance.stream( )
				.filter( INotifierServiceProvider::isEnabled )
				.toList( );
	}

    /**
     * call the registred notifiers
     * 
     * @param notification
     * @throws NotificationException
     */
    public NotifyGruResponse send( Notification notification ) throws NotificationException
    {
	NotifyGruResponse consolidatedResponse = new NotifyGruResponse ( );
	consolidatedResponse.setStatus( NotifyGruResponse.STATUS_RECEIVED );

	boolean isSent = false;
	
	for ( INotifierServiceProvider notifyer : _notifiers )
	{
	    NotifyGruResponse response = notifyer.process( notification );
	    
	    if ( response != null )
	    {
		isSent = true;
		
        	consolidatedResponse.getErrors ( ).addAll( response.getErrors ( ) );
        	consolidatedResponse.getWarnings( ).addAll( response.getWarnings( ) );
        	    
        	if ( !NotifyGruResponse.STATUS_RECEIVED.equals( response.getStatus ( ) ) )
        	{
        		consolidatedResponse.setStatus ( response.getStatus ( ) ); 
        	}
	    }
	}
	
	if (!isSent)
	{
	    consolidatedResponse.setStatus( NotifyGruResponse.STATUS_ERROR );
	    Event error = new Event( );
	    error.setMessage ( "Please provide a notifier for this notification type");
	    error.setStatus ( NotifyGruResponse.STATUS_ERROR );
	    consolidatedResponse.getErrors ( ).add( error );
	    
	    return consolidatedResponse;
	}

	return consolidatedResponse;
    }
    
    /**
     * get NotificationTypes list from Notifiers
     * 
     * @return the list
     */
    public List<EnumNotificationType> getNotificationTypesFromNotifiers( )
    {
	List<EnumNotificationType> list = new ArrayList<> ( );
	
	for ( INotifierServiceProvider notifier : _notifiers )
	{
	    list.addAll ( notifier.getNotificationTypes ( ) );
	}
	
	return list;
    }
}
