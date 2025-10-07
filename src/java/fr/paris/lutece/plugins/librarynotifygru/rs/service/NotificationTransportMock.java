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
package fr.paris.lutece.plugins.librarynotifygru.rs.service;

import fr.paris.lutece.plugins.grubusiness.business.notification.Notification;
import fr.paris.lutece.plugins.grubusiness.business.notification.NotifyGruResponse;
import fr.paris.lutece.portal.service.util.AppLogService;

import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Default NotificationTransport implementation, which not use ApiManager token auth
 */
public final class NotificationTransportMock implements INotificationTransportProvider
{
	private static ObjectMapper _mapper;
	  
    /**
     * Simple Constructor
     */
    public NotificationTransportMock( )
    {
    	_mapper = new ObjectMapper( );
        _mapper.enable( DeserializationFeature.UNWRAP_ROOT_VALUE );
        _mapper.enable( SerializationFeature.INDENT_OUTPUT );
        _mapper.enable( SerializationFeature.WRAP_ROOT_VALUE );
        _mapper.disable( DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES );
        _mapper.setSerializationInclusion( Include.NON_NULL );
    }

    /**
     * Constructor with IHttpTransportProvider parameter
     * 
     * @param httpTransport
     *            the provider to use
     */
    public NotificationTransportMock( IHttpTransportProvider httpTransport )
    {
    }


    
    @Override
    public NotifyGruResponse send( Notification notification )
    {
	AppLogService.info( "LibraryNotifyGru - NotificationTransportMOCK.send() " );
    	
    	if ( AppLogService.isDebugEnabled( ) )
        {
            try
            {
        	AppLogService.debug( "LibraryNotifyGru - NotificationTransportMOCK.send NOTIFICATION:\n" + _mapper.writeValueAsString( notification ) );
            }
            catch( JsonProcessingException e )
            {
        	AppLogService.debug( "LibraryNotifyGru - NotificationTransportMOCK.send query not writeable", e );
            }
        }
    	
    	NotifyGruResponse response = new NotifyGruResponse();
    	response.setStatus( NotifyGruResponse.STATUS_RECEIVED );
    	
    	return response;
    }
}
