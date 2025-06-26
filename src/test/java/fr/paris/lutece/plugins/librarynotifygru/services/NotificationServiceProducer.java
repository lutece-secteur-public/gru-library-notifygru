/*
 * Copyright (c) 2002-2025, City of Paris
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

import fr.paris.lutece.plugins.librarynotifygru.rs.service.MockNotificationTransportRest;
import fr.paris.lutece.plugins.librarynotifygru.rs.service.NotificationTransportApiManagerRest;
import fr.paris.lutece.plugins.librarynotifygru.rs.service.NotificationTransportRest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

@ApplicationScoped
public class NotificationServiceProducer 
{
	@Produces
    @ApplicationScoped
    @Named( "testNotificationServiceMock" )
    public NotificationService produceTestNotificationServiceMock( )
    {
		MockNotificationTransportRest mockTransport = new MockNotificationTransportRest( );
        mockTransport.setNotificationEndPoint( "http://127.0.0.1:9092/lutece/rest/notification/send" );
        return new NotificationService( mockTransport );
    }

	@Produces
    @ApplicationScoped
    @Named( "testNotificationService.api.httpAccess" )
    public NotificationService produceTestNotificationServiceApi( )
    {
		NotificationTransportApiManagerRest notifApiManagerRest = new NotificationTransportApiManagerRest( );
        notifApiManagerRest.setNotificationEndPoint( "http://127.0.0.1:9092/lutece/rest/notification/send" );
        notifApiManagerRest.setApiManagerEndPoint( "http://127.0.0.1:9092/lutece/rest/api/token" );
		return new NotificationService( notifApiManagerRest );
    }

	@Produces
    @ApplicationScoped
    @Named( "testNotificationService.rest.httpAccess" )
    public NotificationService produceTestNotificationServiceRest( )
    {
		NotificationTransportRest notifRest = new NotificationTransportRest( );
		notifRest.setNotificationEndPoint( "http://127.0.0.1:9092/lutece/rest/notification/send" );
		return new NotificationService( notifRest );
    }
}
