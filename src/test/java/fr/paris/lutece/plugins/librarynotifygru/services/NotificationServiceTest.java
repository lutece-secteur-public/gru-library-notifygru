/*
 * Copyright (c) 2002-2016, Mairie de Paris
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.paris.lutece.plugins.librarynotifygru.business.notifygru.NotifyGruGlobalNotification;
import fr.paris.lutece.plugins.librarynotifygru.rs.service.MockNotificationTransportRest;
import fr.paris.lutece.util.httpaccess.HttpAccessService;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import javax.annotation.Resource;


/**
 * test of NotificationService
 */
@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations = 
{
    "classpath:test-notify-gru.xml"}
 )
public class NotificationServiceTest
{
    @Resource( name = "testNotificationService.api.simpleRest" )
    private NotificationService _notificationServiceApiSimpleRest;
    @Resource( name = "testNotificationService.api.httpAccess" )
    private NotificationService _notificationServiceApiHttpAccess;
    @Resource( name = "testNotificationService.rest.simpleRest" )
    private NotificationService _notificationServiceRestSimpleRest;
    @Resource( name = "testNotificationService.rest.httpAccess" )
    private NotificationService _notificationServiceRestHttpAccess;
    @Resource( name = "testNotificationServiceMock" )
    private NotificationService _notificationServiceMock;
    private NotifyGruGlobalNotification _notification;

    /**
     * Constructor, init the notification JSON
     *
     * @throws JsonParseException exception
     * @throws JsonMappingException exception
     * @throws IOException exception
     */
    public NotificationServiceTest(  ) throws JsonParseException, JsonMappingException, IOException
    {
        ObjectMapper mapper = new ObjectMapper(  );
        mapper.enable( DeserializationFeature.UNWRAP_ROOT_VALUE );
        _notification = mapper.readValue( getClass(  ).getResourceAsStream( "/notification.json" ),
                NotifyGruGlobalNotification.class );

        //Init HttpAccess singleton through NPE exception due of lack of properties access
        try
        {
            HttpAccessService.getInstance(  );
        }
        catch ( Exception e )
        {
            //do nothing
        }
    }

    /**
     * test send nodification with ApiManager Transport and HttpAccess Provider
     */
    @Test
    public void testSendApiManagerHttpAccess(  )
    {
        // can't work on localhost due to lutece-core dependency in library-httpaccess
        _notificationServiceApiHttpAccess.send( _notification, "authKey", "sender" );
    }

    /**
     * test send nodification with ApiManager Transport and SimpleRest Provider
     */
    @Test
    public void testSendApiManagerSimpleRest(  )
    {
        // currently KO due to header problem in the query
        _notificationServiceApiSimpleRest.send( _notification, "authKey", "sender" );
    }

    /**
     * test send nodification with Rest Transport and HttpAccess Provider
     */
    @Test
    public void testSendRestHttpAccess(  )
    {
        _notificationServiceRestHttpAccess.send( _notification, "authKey", "sender" );
    }

    /**
     * test send nodification with Rest Transport and SimpleRest Provider
     */
    @Test
    public void testSendRestSimpleRest(  )
    {
        _notificationServiceRestSimpleRest.send( _notification, "authKey", "sender" );
    }

    /**
     * test send nodification with mock
     */
    @Test
    public void testSendMock(  )
    {
        _notificationServiceMock.send( _notification, "authKey", "sender" );
    }

    /**
     * test send nodification with mock and no spring beans
     */
    @Test
    public void testSendMockNoSpring(  )
    {
        MockNotificationTransportRest mockTransport = new MockNotificationTransportRest(  );
        mockTransport.setNotificationEndPoint( "http://127.0.0.1:9092/lutece/rest/notification/send" );

        //default mockTransport.httpTransport set with simpleRest
        NotificationService notificationServiceNoSpring = new NotificationService( mockTransport );

        notificationServiceNoSpring.send( _notification, "authKey", "sender" );
    }
}
