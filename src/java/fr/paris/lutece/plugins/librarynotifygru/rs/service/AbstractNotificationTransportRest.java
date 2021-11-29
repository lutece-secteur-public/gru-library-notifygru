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

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import fr.paris.lutece.plugins.grubusiness.business.notification.Notification;
import fr.paris.lutece.plugins.grubusiness.business.notification.NotifyGruResponse;
import fr.paris.lutece.plugins.librarynotifygru.exception.NotifyGruException;
import fr.paris.lutece.plugins.librarynotifygru.services.IHttpTransportProvider;
import fr.paris.lutece.plugins.librarynotifygru.services.INotificationTransportProvider;
import fr.paris.lutece.util.httpaccess.InvalidResponseStatus;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.httpclient.HttpStatus;

/**
 *
 */
abstract class AbstractNotificationTransportRest implements INotificationTransportProvider
{
    private static ObjectMapper _mapper;
    private static Logger _logger = Logger.getLogger( AbstractNotificationTransportRest.class );

    static
    {
        _mapper = new ObjectMapper( );
        _mapper.enable( DeserializationFeature.UNWRAP_ROOT_VALUE );
        _mapper.enable( SerializationFeature.INDENT_OUTPUT );
        _mapper.enable( SerializationFeature.WRAP_ROOT_VALUE );
        _mapper.disable( DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES );

        _mapper.setSerializationInclusion( Include.NON_NULL );
    }

    /** HTTP transport provider */
    private IHttpTransportProvider _httpTransport;

    /** URL for REST service notification */
    private String _strNotificationEndPoint;

    /**
     * setter of notificationEndPoint
     * 
     * @param strNotificationEndPoint
     *            value to use
     */
    public void setNotificationEndPoint( String strNotificationEndPoint )
    {
        this._strNotificationEndPoint = strNotificationEndPoint;
    }

    /**
     * setter of httpTransport
     * 
     * @param httpTransport
     *            IHttpTransportProvider to use
     */
    public void setHttpTransport( IHttpTransportProvider httpTransport )
    {
        this._httpTransport = httpTransport;
    }

    /**
     * @return the httpTransport
     */
    protected IHttpTransportProvider getHttpTransport( )
    {
        return _httpTransport;
    }

    /**
     * add specific authentication to request
     * 
     * @param mapHeadersRequest
     *            map of headers to add
     */
    protected abstract void addAuthentication( Map<String, String> mapHeadersRequest );

    /**
     * Method to be overriden to do some extra treatment when a NotifyGruException is throwned
     * default : rethrow the exception
     * 
     * @param mapHeadersRequest
     *            map of headers to add
     */
    public NotifyGruResponse catchNotifyGruException( Notification notification, NotifyGruException e ) {
        throw e;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NotifyGruResponse send( Notification notification )
    {
        return send( notification, true );
    }
    
    /**
     * send notification
     */
    public NotifyGruResponse send( Notification notification, boolean bCatchNotifyGruException )
    {
        _logger.debug( "LibraryNotifyGru - AbstractNotificationTransportRest.send() with endPoint [" + _strNotificationEndPoint + "]" );
        logJson( "Notification", notification );

        Map<String, String> mapHeadersRequest = new HashMap<>( );
        Map<String, String> mapParams = new HashMap<>( );

        // add transport authentication headers 
        addAuthentication( mapHeadersRequest );

        // send request
        try 
        {
            NotifyGruResponse response = _httpTransport.doPostJSON( _strNotificationEndPoint, mapParams, mapHeadersRequest, 
                    notification, NotifyGruResponse.class, _mapper );
            
            logJson( "Response", response );

            if ( ( response == null ) 
                    || (  !NotifyGruResponse.STATUS_RECEIVED.equals( response.getStatus( ) )
                       && !NotifyGruResponse.STATUS_ERROR.equals( response.getStatus( ) ) ) )
            {
                String strError = "LibraryNotifyGru - AbstractNotificationTransportRest.send - Error JSON response is null";

                if ( response != null )
                {
                    strError = "LibraryNotifyGru - AbstractNotificationTransportRest.send invalid response : " + response.getStatus( ) ;
                }

                _logger.error( strError );
                throw new NotifyGruException( strError );
            }

            return response;
        }
        catch ( NotifyGruException e )
        {
            if ( bCatchNotifyGruException )
            {
                // do some extra treatment (if implemented)
                return catchNotifyGruException( notification, e );
            }
            else
            {
                _logger.error( e.getMessage( ) );
                throw e;
            }
        }
    }

    /**
     * log for debug
     * 
     * @param strAction
     * @param obj 
     */    
    private static void logJson( String strAction, Object obj )
    {
        if ( _logger.isDebugEnabled( ) )
        {
            try
            {
                _logger.debug( "LibraryNotifyGru - AbstractNotificationTransportRest.send " + strAction +" :\n" + _mapper.writeValueAsString( obj ) );
            }
            catch( JsonProcessingException e )
            {
                _logger.debug( "LibraryNotifyGru - AbstractNotificationTransportRest.send " + strAction +" not writeable", e );
            }
        }
    }
}
