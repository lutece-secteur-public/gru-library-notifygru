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



import fr.paris.lutece.plugins.librarynotifygru.business.INotification;
import fr.paris.lutece.plugins.librarynotifygru.constant.ConstantsLibraryNotifyGru;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.httpaccess.HttpAccess;
import fr.paris.lutece.util.httpaccess.HttpAccessException;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;

import java.util.HashMap;
import java.util.Map;




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

        HttpAccess clientHttp = new HttpAccess(  );

        String strUrl = AppPropertiesService.getProperty( ConstantsLibraryNotifyGru.URL_TOKEN );

        Map<String, String> headersRequest = new HashMap<String, String>(  );
        Map<String, String> headersResponse = new HashMap<String, String>(  );
        Map<String, String> params = new HashMap<String, String>(  );

        params.put( ConstantsLibraryNotifyGru.PARAMS_GRANT_TYPE,
            ConstantsLibraryNotifyGru.PARAMS_GRANT_TYPE_VALUE );

        headersRequest.put( ConstantsLibraryNotifyGru.PROPERTY_HEADER_CONTENT_TYPE,
            ConstantsLibraryNotifyGru.CONTENT_FORMAT_TOKEN );

        headersRequest.put( ConstantsLibraryNotifyGru.AUTHORIZATION,
            ConstantsLibraryNotifyGru.TYPE_AUTHENTIFICATION + " "+ strKeyPropertiesCredentials );

        headersRequest.put( ConstantsLibraryNotifyGru.PROPERTY_HEADER_ACCEPT_TYPE,
            ConstantsLibraryNotifyGru.CONTENT_FORMAT );

        String output = "";
        
        UrlValidator urlValidator = new UrlValidator(  );

        if ( urlValidator.isValid( strUrl ) )
        {
        	  try
              { 
                  output = clientHttp.doPost( strUrl, params, null, null, headersRequest, headersResponse );
                  AppLogService.info( "\n TOKEN JSON RESPONSE \n\n\n\n" + output + "\n" );
              }
              catch ( HttpAccessException e )
              {
                  String strError = "LibraryNotifyGru - Error get Token '" + strUrl + "' : ";
                  AppLogService.error( strError + e.getMessage(  ), e );
              }
        }    

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

        HttpAccess clientHttp = new HttpAccess(  );

        Map<String, String> headersRequest = new HashMap<String, String>(  );
        Map<String, String> headersResponse = new HashMap<String, String>(  );

        headersRequest.put( ConstantsLibraryNotifyGru.PROPERTY_HEADER_CONTENT_TYPE,
            ConstantsLibraryNotifyGru.CONTENT_FORMAT_CHARSET );

        headersRequest.put( ConstantsLibraryNotifyGru.PROPERTY_HEADER_ACCEPT_TYPE,
            ConstantsLibraryNotifyGru.CONTENT_FORMAT );

        if ( StringUtils.isNotBlank( strToken ) )
        {
            headersRequest.put( ConstantsLibraryNotifyGru.AUTHORIZATION,
                ConstantsLibraryNotifyGru.TYPE_AUTHENTIFICATION + " " + strToken );
        }

        headersRequest.putAll( headers );

        try
        {
            ObjectMapper mapper = new ObjectMapper(  );
            mapper.enable( SerializationFeature.INDENT_OUTPUT );
            mapper.enable( SerializationFeature.WRAP_ROOT_VALUE );

            String strJsonFlux = mapper.writeValueAsString( notification );

            AppLogService.info( "\n\n FLUX NOTIFICATION " + strJsonFlux + " \n\n" );

            String response = clientHttp.doPostJSON( urlEndPoint, strJsonFlux, headersRequest, headersResponse );

            AppLogService.info( "\n\n\n\n" );

            for ( Map.Entry<String, String> entry : headersResponse.entrySet(  ) )
            {
                AppLogService.info( "\nKey : " + entry.getKey(  ) + " Value : " + entry.getValue(  ) );
            }

            AppLogService.info( "\n\n " + response + "\n\n" );

           

            if ( JSONUtils.mayBeJSON( response ) )
            {
            	 JSONObject strResponseJsonObject = null;
            	
                strResponseJsonObject = (JSONObject) JSONSerializer.toJSON( response );
                
                JSONObject strACKJsonObject = (strResponseJsonObject.containsKey( ConstantsLibraryNotifyGru.END_POINT_ACKNOWLEDGE ))?strResponseJsonObject.getJSONObject( ConstantsLibraryNotifyGru.END_POINT_ACKNOWLEDGE ):null;

                if ( ( strACKJsonObject == null ) ||
                        !strACKJsonObject.containsKey( ConstantsLibraryNotifyGru.END_POINT_SUCCESS_KEY ) ||
                        !strACKJsonObject.getString( ConstantsLibraryNotifyGru.END_POINT_SUCCESS_KEY )
                                                  .equals( ConstantsLibraryNotifyGru.END_POINT_SUCCESS_MSG ) )
                {
                    AppLogService.error( ConstantsLibraryNotifyGru.ERROR_MESSAGE );
                    throw new AppException( "LibraryNotifyGru - invalid response : " + response );
                }
            }
        }
        catch ( Exception e )
        {
            AppLogService.error( "Error sending JSON to Notification EndPoint : " + e.getMessage(  ), e );
            throw new AppException( e.getMessage(  ), e );
        }
    }
}
