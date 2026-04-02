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

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.jboss.weld.junit5.EnableWeld;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.paris.lutece.plugins.grubusiness.business.notification.Notification;
import fr.paris.lutece.plugins.grubusiness.business.notification.NotifyGruResponse;
import fr.paris.lutece.plugins.grubusiness.service.notification.NotificationException;
import jakarta.inject.Inject;

/**
 * test of NotificationService
 */
@EnableWeld
@TestInstance( Lifecycle.PER_CLASS )
public class NotificationServiceTest
{
    @WeldSetup
    WeldInitiator weld = WeldInitiator.from( NotificationService.class, MockNotifierService.class ).build( );

    @Inject
    private NotificationService _notificationService;

    private Notification _notification;

    @BeforeAll
    void setUp( ) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper( );
        mapper.enable( DeserializationFeature.UNWRAP_ROOT_VALUE );
        _notification = mapper.readValue( getClass( ).getResourceAsStream( "/notification.json" ), Notification.class );
    }

    /**
     * test send nodification with mock
     * 
     * @throws NotificationException
     */
    @Test
    public void testSendNotification( ) throws NotificationException
    {
        NotifyGruResponse response = _notificationService.send( _notification );
        assertNotNull( response );
    }

    /**
     * test send nodification with mock and no CDI beans
     * 
     * @throws NotificationException
     */
    @Test
    public void testMockNotifier( ) throws NotificationException
    {
        MockNotifierService mock = new MockNotifierService( );

        mock.process( _notification );
    }
}
