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

import com.fasterxml.jackson.databind.ObjectMapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

import fr.paris.lutece.plugins.librarynotifygru.constant.ConstantsLibraryNotifyGru;
import fr.paris.lutece.plugins.librarynotifygru.exception.NotifyGruException;

import org.apache.log4j.Logger;

import java.io.IOException;

import java.util.Map;

import javax.ws.rs.core.MediaType;


/**
 * Problem with header configuration not working at the moment
 */
public class SimpleRestTransport implements IHttpTransportProvider
{
    private static Logger _logger = Logger.getLogger( SimpleRestTransport.class );

    /**
     * {@inheritDoc}
     */
    @Override
    public String doPost( String strUrl, Map<String, String> mapParams, Map<String, String> mapHeadersRequest )
    {
        Client client = Client.create(  );
        WebResource webResource = client.resource( strUrl );
        webResource.type( ConstantsLibraryNotifyGru.CONTENT_FORMAT_TOKEN );

        if ( mapParams != null )
        {
            for ( String strParamKey : mapParams.keySet(  ) )
            {
                webResource = webResource.queryParam( strParamKey, mapParams.get( strParamKey ) );
            }
        }

        WebResource.Builder builder = webResource.accept( MediaType.APPLICATION_JSON_TYPE );

        if ( mapHeadersRequest != null )
        {
            for ( String strHeaderKey : mapHeadersRequest.keySet(  ) )
            {
                builder = builder.header( strHeaderKey, mapHeadersRequest.get( strHeaderKey ) );
            }
        }

        ClientResponse response = builder.post( ClientResponse.class );

        if ( response.getStatus(  ) != Status.OK.getStatusCode(  ) )
        {
            String strError = "LibraryNotifyGru - Error SimpleRestTransport.doPost, status code return " +
                response.getStatus(  );
            _logger.error( strError );
            throw new NotifyGruException( strError );
        }
        else
        {
            return response.getEntity( String.class );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T doPostJSON( String strUrl, Map<String, String> mapParams, Map<String, String> mapHeadersRequest,
        Object json, Class<T> responseJsonClass, ObjectMapper mapper )
    {
        Client client = Client.create(  );
        WebResource webResource = client.resource( strUrl );
        webResource.type( ConstantsLibraryNotifyGru.CONTENT_FORMAT );

        if ( mapParams != null )
        {
            for ( String strParamKey : mapParams.keySet(  ) )
            {
                webResource = webResource.queryParam( strParamKey, mapParams.get( strParamKey ) );
            }
        }

        WebResource.Builder builder = webResource.accept( MediaType.APPLICATION_JSON );

        if ( mapHeadersRequest != null )
        {
            for ( String strHeaderKey : mapHeadersRequest.keySet(  ) )
            {
                builder = builder.header( strHeaderKey, mapHeadersRequest.get( strHeaderKey ) );
            }
        }

        try
        {
            ClientResponse response = builder.post( ClientResponse.class, mapper.writeValueAsString( json ) );

            if ( response.getStatus(  ) != Status.OK.getStatusCode(  ) )
            {
                String strError = "LibraryNotifyGru - Error SimpleRestTransport.doPostJSON, status code return " +
                    response.getStatus(  );
                _logger.error( strError );
                throw new NotifyGruException( strError );
            }
            else
            {
                return mapper.readValue( response.getEntity( String.class ), responseJsonClass );
            }
        }
        catch ( UniformInterfaceException e )
        {
            String strError = "LibraryNotifyGru - Error SimpleRestTransport.doPostJSON, unable to post request with JSON content";
            _logger.error( strError, e );
            throw new NotifyGruException( strError, e );
        }
        catch ( ClientHandlerException e )
        {
            String strError = "LibraryNotifyGru - Error SimpleRestTransport.doPostJSON, unable to post request with JSON content";
            _logger.error( strError, e );
            throw new NotifyGruException( strError, e );
        }
        catch ( IOException e )
        {
            String strError = "LibraryNotifyGru - Error SimpleRestTransport.doPostJSON, unable to post request with JSON content";
            _logger.error( strError, e );
            throw new NotifyGruException( strError, e );
        }
    }
}
