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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.paris.lutece.plugins.grubusiness.business.notification.EnumNotificationType;
import fr.paris.lutece.plugins.grubusiness.business.notification.Event;
import fr.paris.lutece.plugins.grubusiness.business.notification.Notification;
import fr.paris.lutece.plugins.grubusiness.business.notification.NotifyGruResponse;
import fr.paris.lutece.plugins.grubusiness.service.notification.INotifierServiceProvider;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.httpaccess.HttpAccess;

public class StatisticsNotifierService implements INotifierServiceProvider
{
    private static final String URL_WS_STATS = "notifications.url.ws.statistics";

    /** constructor */
    public StatisticsNotifierService()
    {
    }

    @Override
    public List<EnumNotificationType> getNotificationTypes( )
    {
	return new ArrayList<> ( ) ;
    }

    /**
     * Send Notification to statistics
     * 
     * @param n
     * @throws Exception
     */
    public NotifyGruResponse process( Notification notification )
    {
	AppLogService.info( " \n \n GRU NOTIFIER - sendNotification( NotificationDTO notification ) \n \n" );

	return doProcess( AppPropertiesService.getProperty( URL_WS_STATS ), notification );
    }

    /**
     * Call web service rest using GET method
     * 
     * @param strWsUrl
     *            the web service URL
     * 
     * @param notification
     *            the parameters
     * @return the response
     * @throws Exception
     * @throws CRMException
     */
    private NotifyGruResponse doProcess( String strWsUrl, Notification notification )
    {
	ObjectMapper mapper = new ObjectMapper();
	HttpAccess clientHttp = new HttpAccess();
	Map<String, String> mapHeadersResponse = new HashMap<String, String>();
	Map<String, String> mapHeadersRequest = new HashMap<String, String>();
	mapHeadersRequest.put( "ACCEPT", "application/json" );

	NotifyGruResponse gruResponse = new NotifyGruResponse ( );
	gruResponse.setStatus ( NotifyGruResponse.STATUS_RECEIVED );

	try
	{
	    String strJSON = mapper.writeValueAsString( notification );
	    String strResponseJSON = clientHttp.doPostJSON( strWsUrl, strJSON, mapHeadersRequest, mapHeadersResponse );

	    AppLogService.debug ( strResponseJSON );
	}
	catch ( Exception e )
	{
	    String strError = "Error connecting to '" + strWsUrl + "' : ";
	    AppLogService.error( strError + e.getMessage(), e );

	    gruResponse.setStatus ( NotifyGruResponse.STATUS_ERROR );
	    Event event = new Event( );
	    event.setMessage ( e.getMessage ( ) );
	    event.setStatus ( NotifyGruResponse.STATUS_ERROR  );
	    gruResponse.getErrors ( ).add ( event );
	}

	return gruResponse;
    }

    @Override
    public String getName() {
	return this.getClass( ).getName( );
    }
}
