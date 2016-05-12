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
package fr.paris.lutece.plugins.librarynotifygru.business.notifygru;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import fr.paris.lutece.plugins.librarynotifygru.business.INotification;
import fr.paris.lutece.plugins.librarynotifygru.constant.ConstantsLibraryNotifyGru;


// TODO: Auto-generated Javadoc
/**
 * This is the business class for the object NotifyGruGlobal.
 */
@JsonRootName( value = "notification" )
@JsonPropertyOrder( {"user_guid",
    "customer_id",
    "email",
    "demand_status",
    "crm_status_id",
    "demand_id",
    "demand_type_id",
    "remote_demand_id",
    "demand_max_step",
    "demand_user_current_step",
    "notification_type",
    "notification_date",
    "demand_reference",
    "backoffice_logging",
    "user_email",
    "user_sms",
    "user_dashboard"
} )
public class NotifyGruGlobalNotification implements INotification
{
    /** The _str guid. */
    // Variables declarations 
    private String _strGuid;

    /** The _str email. */
    private String _strEmail;

    /** The _n crm status id. */
    private int _nCrmStatusId;

    /** The _str notification type. */
    private String _strNotificationType;

    /** The _n demand status. */
    private int _nDemandStatus;

    /** The _str demand reference. */
    private String _strDemandReference;

    /** The _n customer id. */
    private int _nCustomerId;

    /** The _ notification date. */
    private Long _notificationDate;

    /** The _n demand id. */
    private int _nDemandId;

    /** The _n remote demand id. */
    private int _nRemoteDemandId;

    /** The _n demand type id. */
    private int _nDemandTypeId;

    /** The _n demand max step. */
    private int _nDemandMaxStep;

    /** The _n demand user current step. */
    private int _nDemandUserCurrentStep;

    /** The user email. */
    @JsonInclude( JsonInclude.Include.NON_NULL )
    private NotifyGruEmailNotification _userEmail;

    /** The user sms. */
    @JsonInclude( JsonInclude.Include.NON_NULL )
    private NotifyGruSMSNotification _userSMS;

    /** The user agent. */
    @JsonInclude( JsonInclude.Include.NON_NULL )
    private NotifyGruAgentNotification _userAgent;

    /** The user guichet. */
    @JsonInclude( JsonInclude.Include.NON_NULL )
    private NotifyGruGuichetNotification _userGuichet;

    /**
     * Instantiates a new notify gru global notification.
     */
    public NotifyGruGlobalNotification(  )
    {
        this._strGuid = ConstantsLibraryNotifyGru.DEFAULT_STRING;
        this._strEmail = ConstantsLibraryNotifyGru.DEFAULT_STRING;
        this._nCrmStatusId = ConstantsLibraryNotifyGru.DEFAULT_INT;
        this._strNotificationType = ConstantsLibraryNotifyGru.DEFAULT_STRING;
        this._nDemandStatus = ConstantsLibraryNotifyGru.DEFAULT_INT;
        this._strDemandReference = ConstantsLibraryNotifyGru.DEFAULT_STRING;
        this._nCustomerId = ConstantsLibraryNotifyGru.DEFAULT_INT;
        this._notificationDate = ConstantsLibraryNotifyGru.DEFAULT_LONG;
        this._nDemandId = ConstantsLibraryNotifyGru.DEFAULT_INT;
        this._nRemoteDemandId = ConstantsLibraryNotifyGru.DEFAULT_INT;
        this._nDemandTypeId = ConstantsLibraryNotifyGru.DEFAULT_INT;
        this._nDemandMaxStep = ConstantsLibraryNotifyGru.DEFAULT_INT;
        this._nDemandUserCurrentStep = ConstantsLibraryNotifyGru.DEFAULT_INT;
    }

    /**
    * Returns the Guid.
    *
    * @return The Guid
    */
    @JsonProperty( "user_guid" )
    public String getGuid(  )
    {
        return _strGuid;
    }

    /**
     * Sets the Guid.
     *
     * @param strGuid The Guid
     */
    @JsonProperty( "user_guid" )
    public void setGuid( String strGuid )
    {
        _strGuid = strGuid;
    }

    /**
     * Returns the Email.
     *
     * @return The Email
     */
    @JsonProperty( "email" )
    public String getEmail(  )
    {
        return _strEmail;
    }

    /**
     * Sets the Email.
     *
     * @param strEmail The Email
     */
    @JsonProperty( "email" )
    public void setEmail( String strEmail )
    {
        _strEmail = strEmail;
    }

    /**
     * Returns the CrmStatusId.
     *
     * @return The CrmStatusId
     */
    @JsonProperty( "crm_status_id" )
    public int getCrmStatusId(  )
    {
        return _nCrmStatusId;
    }

    /**
     * Sets the CrmStatusId.
     *
     * @param nCrmStatusId The CrmStatusId
     */
    @JsonProperty( "crm_status_id" )
    public void setCrmStatusId( int nCrmStatusId )
    {
        _nCrmStatusId = nCrmStatusId;
    }

    /**
     * Returns the NotificationType.
     *
     * @return The NotificationType
     */
    @JsonProperty( "notification_type" )
    public String getNotificationType(  )
    {
        return _strNotificationType;
    }

    /**
     * Sets the NotificationType.
     *
     * @param strNotificationType The NotificationType
     */
    @JsonProperty( "notification_type" )
    public void setNotificationType( String strNotificationType )
    {
        _strNotificationType = strNotificationType;
    }

    /**
     * Returns the DemandStatus.
     *
     * @return The DemandStatus
     */
    @JsonProperty( "demand_status" )
    public int getDemandStatus(  )
    {
        return _nDemandStatus;
    }

    /**
     * Sets the DemandStatus.
     *
     * @param nDemandStatus The DemandStatus
     */
    @JsonProperty( "demand_status" )
    public void setDemandStatus( int nDemandStatus )
    {
        _nDemandStatus = nDemandStatus;
    }

    /**
     * Returns the DemandReference.
     *
     * @return The DemandReference
     */
    @JsonProperty( "demand_reference" )
    public String getDemandReference(  )
    {
        return _strDemandReference;
    }

    /**
     * Sets the DemandReference.
     *
     * @param strDemandReference The DemandReference
     */
    @JsonProperty( "demand_reference" )
    public void setDemandReference( String strDemandReference )
    {
        _strDemandReference = strDemandReference;
    }

    /**
     * Returns the CustomerId.
     *
     * @return The CustomerId
     */
    @JsonProperty( "customer_id" )
    public int getCustomerId(  )
    {
        return _nCustomerId;
    }

    /**
     * Sets the CustomerId.
     *
     * @param nCustomerId The CustomerId
     */
    @JsonProperty( "customer_id" )
    public void setCustomerId( int nCustomerId )
    {
        _nCustomerId = nCustomerId;
    }

    /**
     * Returns the NotificationDate.
     *
     * @return The NotificationDate
     */
    @JsonProperty( "notification_date" )
    public Long getNotificationDate(  )
    {
        return _notificationDate;
    }

    /**
     * Sets the NotificationDate.
     *
     * @param notificationDate The NotificationDate
     */
    @JsonProperty( "notification_date" )
    public void setNotificationDate( Long notificationDate )
    {
        _notificationDate = notificationDate;
    }

    /**
     * Returns the DemandId.
     *
     * @return The DemandId
     */
    @JsonProperty( "demand_id" )
    public int getDemandId(  )
    {
        return _nDemandId;
    }

    /**
     * Sets the DemandId.
     *
     * @param nDemandId The DemandId
     */
    @JsonProperty( "demand_id" )
    public void setDemandId( int nDemandId )
    {
        _nDemandId = nDemandId;
    }

    /**
     * Returns the RemoteDemandId.
     *
     * @return The RemoteDemandId
     */
    @JsonProperty( "remote_demand_id" )
    public int getRemoteDemandId(  )
    {
        return _nRemoteDemandId;
    }

    /**
     * Sets the RemoteDemandId.
     *
     * @param nRemoteDemandId The RemoteDemandId
     */
    @JsonProperty( "remote_demand_id" )
    public void setRemoteDemandId( int nRemoteDemandId )
    {
        _nRemoteDemandId = nRemoteDemandId;
    }

    /**
     * Gets the demand type id.
     *
     * @return the demand type id
     */
    @JsonProperty( "demand_type_id" )
    public int getDemandTypeId(  )
    {
        return _nDemandTypeId;
    }

    /**
     * Sets the demand type id.
     *
     * @param nDemandTypeId the new demand type id
     */
    @JsonProperty( "demand_type_id" )
    public void setDemandTypeId( int nDemandTypeId )
    {
        _nDemandTypeId = nDemandTypeId;
    }

    /**
     * Returns the DemandMaxStep.
     *
     * @return The DemandMaxStep
     */
    @JsonProperty( "demand_max_step" )
    public int getDemandMaxStep(  )
    {
        return _nDemandMaxStep;
    }

    /**
     * Sets the DemandMaxStep.
     *
     * @param nDemandMaxStep The DemandMaxStep
     */
    @JsonProperty( "demand_max_step" )
    public void setDemandMaxStep( int nDemandMaxStep )
    {
        _nDemandMaxStep = nDemandMaxStep;
    }

    /**
     * Returns the DemandUserCurrentStep.
     *
     * @return The DemandUserCurrentStep
     */
    @JsonProperty( "demand_user_current_step" )
    public int getDemandUserCurrentStep(  )
    {
        return _nDemandUserCurrentStep;
    }

    /**
     * Sets the DemandUserCurrentStep.
     *
     * @param nDemandUserCurrentStep The DemandUserCurrentStep
     */
    @JsonProperty( "demand_user_current_step" )
    public void setDemandUserCurrentStep( int nDemandUserCurrentStep )
    {
        _nDemandUserCurrentStep = nDemandUserCurrentStep;
    }

    /**
     * Gets the user email.
     *
     * @return the user email
     */
    @JsonProperty( "user_email" )
    public NotifyGruEmailNotification getUserEmail(  )
    {
        return _userEmail;
    }

    /**
     * Sets the user email.
     *
     * @param userEmail the new user email
     */
    @JsonProperty( "user_email" )
    public void setUserEmail( NotifyGruEmailNotification userEmail )
    {
        this._userEmail = userEmail;
    }

    /**
     * Gets the user sms.
     *
     * @return the user sms
     */
    @JsonProperty( "user_sms" )
    public NotifyGruSMSNotification getUserSMS(  )
    {
        return _userSMS;
    }

    /**
     * Sets the user sms.
     *
     * @param userSMS the new user sms
     */
    @JsonProperty( "user_sms" )
    public void setUserSMS( NotifyGruSMSNotification userSMS )
    {
        this._userSMS = userSMS;
    }

    /**
     * Gets the user agent.
     *
     * @return the user agent
     */
    @JsonProperty( "backoffice_logging" )
    public NotifyGruAgentNotification getUserAgent(  )
    {
        return _userAgent;
    }

    /**
     * Sets the user agent.
     *
     * @param userAgent the new user agent
     */
    @JsonProperty( "backoffice_logging" )
    public void setUserAgent( NotifyGruAgentNotification userAgent )
    {
        this._userAgent = userAgent;
    }

    /**
     * Gets the user guichet.
     *
     * @return the user guichet
     */
    @JsonProperty( "user_dashboard" )
    public NotifyGruGuichetNotification getUserGuichet(  )
    {
        return _userGuichet;
    }

    /**
     * Sets the user guichet.
     *
     * @param userGuichet the new user guichet
     */
    @JsonProperty( "user_dashboard" )
    public void setUserGuichet( NotifyGruGuichetNotification userGuichet )
    {
        this._userGuichet = userGuichet;
    }
}
