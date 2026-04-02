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

import java.util.Arrays;
import java.util.List;

import fr.paris.lutece.plugins.grubusiness.business.notification.EnumNotificationType;
import fr.paris.lutece.plugins.grubusiness.business.notification.Notification;
import fr.paris.lutece.plugins.grubusiness.business.notification.NotifyGruResponse;
import fr.paris.lutece.plugins.grubusiness.service.notification.INotifierServiceProvider;
import fr.paris.lutece.plugins.grubusiness.service.notification.NotificationException;
import fr.paris.lutece.plugins.librarynotifygru.rs.service.INotificationTransportProvider;
import fr.paris.lutece.plugins.librarynotifygru.rs.service.NotificationTransportApiManagerRest;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.literal.NamedLiteral;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class NotificationStoreNotifierRestService implements INotifierServiceProvider
{
    private static final String NAME = "NotificationStoreRestNotifyer";
    private static final String PROPERTY_NOTIFIER_ENABLED = "library-notifygru.notifier.notificationStore.enabled";

    @Override
    public NotifyGruResponse process( Notification notification ) throws NotificationException
    {
	return send( notification );
    }

    @Override
    public String getName( )
    {
	return NAME;
    }
    
    @Override
    public List<EnumNotificationType> getNotificationTypes( )
    {
	return Arrays.asList(EnumNotificationType.MYDASHBOARD, EnumNotificationType.CUSTOMER_EMAIL, 
		EnumNotificationType.BROADCAST_EMAIL, EnumNotificationType.SMS, 
		EnumNotificationType.BACKOFFICE);
    }
    
    /** transport provider */
    private INotificationTransportProvider _transportProvider;

 
    /**
     * Constructor with INotificationTransportProvider in parameters
     * 
     * @param transportProvider
     *            INotificationTransportProvider
     */
    public NotificationStoreNotifierRestService( INotificationTransportProvider transportProvider )
    {
        super( );
        this._transportProvider = transportProvider;
    }

    @Inject
    public NotificationStoreNotifierRestService(
            Instance<INotificationTransportProvider> providers,
            @ConfigProperty( name = "library-notifygru.notificationStoreNotifierRestService.transportProviderBeanName",
                    defaultValue = NotificationTransportApiManagerRest.BEAN_NAME ) String providerName )
    {
        super( );
        this._transportProvider = providers
                .select( NamedLiteral.of( providerName ) )
                .get( );
    }

    /**
     * setter of transportProvider parameter
     * 
     * @param transportProvider
     *            INotificationTransportProvider
     */
    public void setTransportProvider( INotificationTransportProvider transportProvider )
    {
        this._transportProvider = transportProvider;
    }

    /**
     * Send a Notification GRU can throw AppException in case of problem with the JSON request
     *
     * @param notification
     *            the notification
     * @return the notifyGruResponse
     */
    public NotifyGruResponse send( Notification notification ) throws NotificationException
    {
        return _transportProvider.send( notification );
    }

    @Override
    public boolean isEnabled( )
    {
        return AppPropertiesService.getPropertyBoolean( PROPERTY_NOTIFIER_ENABLED, false );
    }
}
