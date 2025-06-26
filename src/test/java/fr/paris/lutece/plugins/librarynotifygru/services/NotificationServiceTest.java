/*
 * Copyright (c) 2002-2021, City of Paris
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

import java.io.IOException;

import org.jboss.weld.junit5.EnableWeld;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.paris.lutece.plugins.grubusiness.business.notification.Notification;
import fr.paris.lutece.plugins.librarynotifygru.rs.service.MockNotificationTransportRest;
import fr.paris.lutece.util.httpaccess.HttpAccessService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

/**
 * test of NotificationService
 */
@EnableWeld
@TestInstance( Lifecycle.PER_CLASS )
public class NotificationServiceTest
{
    @Inject
    @Named( "testNotificationService.api.httpAccess" )
    private NotificationService _notificationServiceApiHttpAccess;

    @Inject
    @Named( "testNotificationService.rest.httpAccess" )
    private NotificationService _notificationServiceRestHttpAccess;

    @Inject
    @Named( "testNotificationServiceMock" )
    private NotificationService _notificationServiceMock;

    private Notification _notification;
    private MockWebServer mockWebServer;
    
    @BeforeEach
    public void setUp( ) throws IOException
    {
        this.mockWebServer = new MockWebServer( );
        this.mockWebServer.start( 9092 );
        this.mockWebServer.enqueue( new MockResponse( )
        		.setBody( "{\"acknowledge\":{\"status\":\"received\"}}" )
                .setHeader( "Content-Type", "application/json" ) );
    }

    @AfterEach
    public void tearDown( ) throws IOException
    {
        this.mockWebServer.shutdown( );
    }

    /**
     * Constructor, init the notification JSON
     *
     * @throws JsonParseException
     *             exception
     * @throws JsonMappingException
     *             exception
     * @throws IOException
     *             exception
     */
    public NotificationServiceTest( ) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper( );
        mapper.enable( DeserializationFeature.UNWRAP_ROOT_VALUE );
        _notification = mapper.readValue( getClass( ).getResourceAsStream( "/notification.json" ), Notification.class );

        // Init HttpAccess singleton through NPE exception due of lack of properties access
        try
        {
            HttpAccessService.getInstance( );
        }
        catch( Exception e )
        {
            // do nothing
        }
    }

    /**
     * test send nodification with ApiManager Transport and HttpAccess Provider
     */
    @Test
    public void testSendApiManagerHttpAccess( )
    {
        // can't work on localhost due to lutece-core dependency in library-httpaccess
        _notificationServiceApiHttpAccess.send( _notification );
    }

    /**
     * test send nodification with Rest Transport and HttpAccess Provider
     */
    @Test
    public void testSendRestHttpAccess( )
    {
        _notificationServiceRestHttpAccess.send( _notification );
    }

    /**
     * test send nodification with mock
     */
    @Test
    public void testSendMock( )
    {
        _notificationServiceMock.send( _notification );
    }

    /**
     * test send nodification with mock and no CDI beans
     */
    @Test
    public void testSendMockNoCdi( )
    {
        MockNotificationTransportRest mockTransport = new MockNotificationTransportRest( );
        mockTransport.setNotificationEndPoint( "http://127.0.0.1:9092/lutece/rest/notification/send" );

        // default mockTransport.httpTransport set with simpleRest
        NotificationService notificationServiceNoSpring = new NotificationService( mockTransport );

        notificationServiceNoSpring.send( _notification );
    }
}
