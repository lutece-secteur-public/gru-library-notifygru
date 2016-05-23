/*
 * Copyright (c) 2002-2015, Mairie de Paris
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
import com.fasterxml.jackson.databind.SerializationFeature;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import fr.paris.lutece.plugins.librarynotifygru.business.INotification;
import fr.paris.lutece.plugins.librarynotifygru.constant.ConstantsLibraryNotifyGru;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


// TODO: Auto-generated Javadoc
/**
 * The Class sendNotificationAsJson.
 *
 * @author root
 */
public class SendNotificationAsJson implements IsendNotificationAsJson
{
    /** The Constant BEAN_NOTIFICATION_SERVICE. */
    private static final String BEAN_NOTIFICATION_SERVICE = "library-notifygru.clientNotification";

    /** The _singleton. */
    private static SendNotificationAsJson _singleton;

    /** The _str token. */
    private String _strToken;

    /**
     * Instance.
     *
     * @return the send notification as json
     */
    public static SendNotificationAsJson instance(  )
    {
        if ( _singleton == null )
        {
            try
            {
                _singleton = SpringContextService.getBean( BEAN_NOTIFICATION_SERVICE );
            }
            catch ( Exception e )
            {
                AppLogService.error( "Error when instantiating library NotificationService instance" +
                    e.getMessage(  ), e );
            }
        }

        return _singleton;
    }

    /* (non-Javadoc)
     * @see fr.paris.lutece.plugins.librarynotifygru.services.IsendNotificationAsJson#getToken(java.lang.String)
     */
    @Override
    public String getToken( String strKeyPropertiesCredentials )
    {
        AppLogService.info( 
            "\n*************************************************************** TOKEN REQUEST **********************************************************************\n" );
        AppLogService.info( "\n PROPERTIES CREDENTIALS   : " + strKeyPropertiesCredentials + "\n" );
        AppLogService.info( "\n TOKEN URL " + ConstantsLibraryNotifyGru.URL_TOKEN + "  : " +
            AppPropertiesService.getProperty( ConstantsLibraryNotifyGru.URL_TOKEN ) + "\n" );

        Client client = Client.create(  );

        WebResource webResource = client.resource( AppPropertiesService.getProperty( 
                    ConstantsLibraryNotifyGru.URL_TOKEN ) );

        javax.ws.rs.core.MultivaluedMap<String, String> params = new com.sun.jersey.core.util.MultivaluedMapImpl(  );
        params.add( ConstantsLibraryNotifyGru.PARAMS_GRANT_TYPE, ConstantsLibraryNotifyGru.PARAMS_GRANT_TYPE_VALUE );

        ClientResponse response = webResource.type( ConstantsLibraryNotifyGru.CONTENT_FORMAT_TOKEN )
                                             .header( HttpHeaders.AUTHORIZATION,
                ConstantsLibraryNotifyGru.TYPE_AUTHENTIFICATION + " " +
                AppPropertiesService.getProperty( strKeyPropertiesCredentials ) ).accept( MediaType.APPLICATION_JSON )
                                             .post( ClientResponse.class, params );

        String output = response.getEntity( String.class );

        JSONObject strResponseApiManagerJsonObject = null;

        if ( JSONUtils.mayBeJSON( output ) )
        {
            strResponseApiManagerJsonObject = (JSONObject) JSONSerializer.toJSON( output );

            if ( ( strResponseApiManagerJsonObject != null ) &&
                    strResponseApiManagerJsonObject.has( ConstantsLibraryNotifyGru.PARAMS_ACCES_TOKEN ) )
            {
                AppLogService.info( "\n TOKEN JSON RESPONSE \n\n\n\n" + strResponseApiManagerJsonObject.toString( 2 ) +
                    "\n" );

                _strToken = (String) strResponseApiManagerJsonObject.get( ConstantsLibraryNotifyGru.PARAMS_ACCES_TOKEN );
                AppLogService.info( "\n TOKEN \n\n\n\n" + _strToken + "\n" );
            }
        }

        AppLogService.info( 
            "\n*************************************************************** END TOKEN REQUEST **********************************************************************\n" );

        return _strToken;
    }

    /* (non-Javadoc)
     * @see fr.paris.lutece.plugins.librarynotifygru.services.IsendNotificationAsJson#send(fr.paris.lutece.plugins.librarynotifygru.business.INotification, java.lang.String, java.util.Map)
     */
    @Override
    public void send( INotification notification, String strToken, Map<String, String> headers )
    {
        String url = null;
        send( notification, strToken, headers, url );
    }

    /* (non-Javadoc)
     * @see fr.paris.lutece.plugins.librarynotifygru.services.IsendNotificationAsJson#send(fr.paris.lutece.plugins.librarynotifygru.business.INotification, java.lang.String)
     */
    @Override
    public void send( INotification notification, String strToken )
    {
        Map<String, String> headers = new HashMap<String, String>(  );
        send( notification, strToken, headers );
    }

    /* (non-Javadoc)
     * @see fr.paris.lutece.plugins.librarynotifygru.services.IsendNotificationAsJson#send(fr.paris.lutece.plugins.librarynotifygru.business.INotification)
     */
    @Override
    public void send( INotification notification )
    {
        String strToken = null;
        send( notification, strToken );
    }

    @Override
    public void send( INotification notification, String strToken, Map<String, String> headers, String url )
    {
        Client client = Client.create(  );

        String urlEndPoint;

        UrlValidator urlValidator = new UrlValidator(  );

        if ( urlValidator.isValid( url ) )
        {
            urlEndPoint = url;
        }
        else
        {
            urlEndPoint = AppPropertiesService.getProperty( ConstantsLibraryNotifyGru.URL_NOTIFICATION_ENDPOINT );
        }

        WebResource webResource = client.resource( urlEndPoint );

        WebResource.Builder request = webResource.type( ConstantsLibraryNotifyGru.CONTENT_FORMAT )
                                                 .accept( MediaType.APPLICATION_JSON );

        if ( StringUtils.isNotBlank( strToken ) )
        {
            request.header( HttpHeaders.AUTHORIZATION, ConstantsLibraryNotifyGru.TYPE_AUTHENTIFICATION + " " +
                strToken );
        }

        //             
        //                
        for ( Map.Entry<String, String> header : headers.entrySet(  ) )
        {
            request.header( header.getKey(  ), header.getValue(  ) );
        }

        try
        {
            ObjectMapper mapper = new ObjectMapper(  );
            mapper.enable( SerializationFeature.INDENT_OUTPUT );
            mapper.enable( SerializationFeature.WRAP_ROOT_VALUE );

            String strJsonFlux = mapper.writeValueAsString( notification );

            AppLogService.info( "\n\n FLUX NOTIFICATION " + strJsonFlux + " \n\n" );

            ClientResponse response = request.post( ClientResponse.class, strJsonFlux );

            if ( ( response != null ) &&
                    ( ( response.getStatus(  ) != Response.Status.OK.getStatusCode(  ) ) &&
                    ( response.getStatus(  ) != Response.Status.CREATED.getStatusCode(  ) ) &&
                    ( response.getStatus(  ) != Response.Status.ACCEPTED.getStatusCode(  ) ) ) )
            {
                //Constants.ERROR_MESSAGE + response.getStatus(  )
                AppLogService.error( ConstantsLibraryNotifyGru.ERROR_MESSAGE + response.getStatus(  ) );
                throw new AppException( ( ( "invalid response : " + response ) == null ) ? " response is null"
                                                                                         : ( " response code = " +
                    response.getStatus(  ) + " expected response code = 20x" ) );
            }
        }
        catch ( Exception e )
        {
            AppLogService.error( "Error sending JSON to Notification EndPoint : " + e.getMessage(  ), e );
            throw new AppException( e.getMessage(  ), e );
        }
    }
}
