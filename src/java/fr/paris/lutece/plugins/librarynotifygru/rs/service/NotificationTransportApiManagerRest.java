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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.paris.lutece.plugins.grubusiness.business.notification.Notification;
import fr.paris.lutece.plugins.grubusiness.business.notification.NotifyGruResponse;
import fr.paris.lutece.plugins.librarynotifygru.exception.NotifyGruException;
import fr.paris.lutece.plugins.librarynotifygru.services.HttpAccessTransport;
import fr.paris.lutece.plugins.librarynotifygru.services.INotificationTransportProvider;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import org.apache.commons.httpclient.HttpStatus;

/**
 *
 */
public final class NotificationTransportApiManagerRest extends AbstractNotificationTransportRest implements INotificationTransportProvider
{
    /** The Constant PARAMS_ACCES_TOKEN. */
    private static final String PARAMS_ACCES_TOKEN = "access_token";
    private static final String PARAMS_VALIDITY = "expires_in";
    private static final String TYPE_AUTHENTIFICATION = "Bearer";

    /** The Constant PARAMS_GRANT_TYPE. */
    private static final String PARAMS_GRANT_TYPE = "grant_type";

    /** The Constant PARAMS_GRANT_TYPE_VALUE. */
    private static final String PARAMS_GRANT_TYPE_VALUE = "client_credentials";
    private static Logger _logger = Logger.getLogger( NotificationTransportApiManagerRest.class );

    /** URL for REST service apiManager */
    private String _strApiManagerEndPoint;
    private String _strApiManagerCredentials;
    
    /** store current token properties */
    String _strToken;
    LocalDateTime _dateExpiration;

    /**
     * Simple Constructor
     */
    public NotificationTransportApiManagerRest( )
    {
        super( );
        this.setHttpTransport( new HttpAccessTransport( ) );
    }

    /**
     * setter of apiManagerEndPoint
     * 
     * @param strApiManagerEndPoint
     *            value to use
     */
    public void setApiManagerEndPoint( String strApiManagerEndPoint )
    {
        this._strApiManagerEndPoint = strApiManagerEndPoint;
    }

    /**
     * Sets the API Manager credentials
     * 
     * @param strApiManagerCredentials
     *            the API Manager credentials
     */
    public void setApiManagerCredentials( String strApiManagerCredentials )
    {
        this._strApiManagerCredentials = strApiManagerCredentials;
    }

    /**
     * Gets the current security token if valid, 
     * or refresh token if token expiration will occur in less than one minute
     * 
     * @return the token
     */
    private String getToken( )
    {
        if ( StringUtils.isEmpty(_strToken ) || _dateExpiration == null
                || LocalDateTime.now( ).isAfter( _dateExpiration.minusMinutes( 1 ) ) )
        {
            refreshToken( );
        }

        return _strToken;
    }

    /**
     * refresh the security token from API Manager
     * 
     */
    private void refreshToken( )
    {
        _logger.debug( "LibraryNotifyGru - NotificationTransportApiManagerRest.getToken with URL_TOKEN property [" + _strApiManagerEndPoint + "]" );

        Map<String, String> mapHeadersRequest = new HashMap<>( );
        Map<String, String> mapParams = new HashMap<>( );

        mapParams.put( PARAMS_GRANT_TYPE, PARAMS_GRANT_TYPE_VALUE );

        mapHeadersRequest.put( HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON );
        mapHeadersRequest.put( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED );
        mapHeadersRequest.put( HttpHeaders.AUTHORIZATION, TYPE_AUTHENTIFICATION + " " + _strApiManagerCredentials );

        String strJson = getHttpTransport( ).doPost( _strApiManagerEndPoint, mapParams, mapHeadersRequest );

        ObjectMapper mapper = new ObjectMapper( );
        mapper.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );

        try
        {
            JsonNode jsonNode = mapper.readTree( strJson );

            JsonNode jsonTokenNode = jsonNode.get( PARAMS_ACCES_TOKEN );

            // new token value
            _strToken = jsonTokenNode.textValue( );

            JsonNode jsonValidityNode = jsonNode.get( PARAMS_VALIDITY );
            int nbSecondsValidityTime = jsonValidityNode.asInt( 0 );

            _dateExpiration = LocalDateTime.now( ).plusSeconds( nbSecondsValidityTime );
        }
        catch ( JsonProcessingException e )
        {
            _logger.error( "LibraryNotifyGru - NotificationTransportApiManagerRest.getToken invalid response [" + strJson + "]" );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void addAuthentication( Map<String, String> mapHeadersRequest )
    {
        String strToken = getToken( );

        if ( StringUtils.isNotBlank( strToken ) )
        {
            mapHeadersRequest.put( HttpHeaders.AUTHORIZATION, TYPE_AUTHENTIFICATION + " " + strToken );
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public NotifyGruResponse catchNotifyGruException( Notification notification, NotifyGruException e )
    {
        if ( e.getResponseStatus( ) == HttpStatus.SC_UNAUTHORIZED )
        {
            refreshToken( );
        
            return send( notification, false );
        }
        else
        {
            throw e;
        }
    }
    
}
