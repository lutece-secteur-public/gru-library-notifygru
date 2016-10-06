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

import fr.paris.lutece.plugins.librarynotifygru.NotifyGruConstants;
import fr.paris.lutece.plugins.librarynotifygru.exception.NotifyGruException;
import fr.paris.lutece.util.httpaccess.HttpAccess;
import fr.paris.lutece.util.httpaccess.HttpAccessException;

import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;


/**
 * IHttpTransportProvider which use library-httpaccess
 */
public class HttpAccessTransport implements IHttpTransportProvider
{
    private static Logger _logger = Logger.getLogger( HttpAccessTransport.class );

    /**
     * {@inheritDoc}
     */
    @Override
    public String doPost( String strUrl, Map<String, String> mapParams, Map<String, String> mapHeadersRequest )
    {
        HttpAccess clientHttp = new HttpAccess(  );
        Map<String, String> mapHeadersResponse = new HashMap<String, String>(  );

        String strOutput = StringUtils.EMPTY;

        try
        {
            strOutput = clientHttp.doPost( strUrl, mapParams, null, null, mapHeadersRequest, mapHeadersResponse );
        }
        catch ( HttpAccessException e )
        {
            String strError = "LibraryNotifyGru - Error HttpAccessTransport.doPost on URL [" + strUrl + "] : ";
            _logger.error( strError + e.getMessage(  ), e );
            throw new NotifyGruException( strError, e );
        }

        return strOutput;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T doPostJSON( String strUrl, Map<String, String> mapParams, Map<String, String> mapHeadersRequest,
        Object json, Class<T> responseJsonClass, ObjectMapper mapper )
    {
        HttpAccess clientHttp = new HttpAccess(  );
        Map<String, String> mapHeadersResponse = new HashMap<String, String>(  );
        mapHeadersRequest.put( HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON );
        mapHeadersRequest.put( HttpHeaders.CONTENT_TYPE, NotifyGruConstants.CONTENT_FORMAT_CHARSET );

        try
        {
            String strJSON = mapper.writeValueAsString( json );
            String strResponseJSON = clientHttp.doPostJSON( strUrl, strJSON, mapHeadersRequest, mapHeadersResponse );
            T oResponse = mapper.readValue( strResponseJSON, responseJsonClass );

            return oResponse;
        }
        catch ( HttpAccessException e )
        {
            String strError = "LibraryNotifyGru - Error HttpAccessTransport.doPostJSON on URL [" + strUrl + "] : ";
            _logger.error( strError + e.getMessage(  ), e );
            throw new NotifyGruException( strError, e );
        }
        catch ( IOException e )
        {
            String strError = "LibraryNotifyGru - Error HttpAccessTransport.doPostJSON on JSON manipulation :";
            _logger.error( strError + e.getMessage(  ), e );
            throw new NotifyGruException( strError, e );
        }
    }
}
