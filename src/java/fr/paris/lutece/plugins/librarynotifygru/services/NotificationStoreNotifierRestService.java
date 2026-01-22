package fr.paris.lutece.plugins.librarynotifygru.services;

import java.util.Arrays;
import java.util.List;

import fr.paris.lutece.plugins.grubusiness.business.notification.EnumNotificationType;
import fr.paris.lutece.plugins.grubusiness.business.notification.Notification;
import fr.paris.lutece.plugins.grubusiness.business.notification.NotifyGruResponse;
import fr.paris.lutece.plugins.grubusiness.service.notification.INotifierServiceProvider;
import fr.paris.lutece.plugins.grubusiness.service.notification.NotificationException;
import fr.paris.lutece.plugins.librarynotifygru.rs.service.INotificationTransportProvider;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

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
