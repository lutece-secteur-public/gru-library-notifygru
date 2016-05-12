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


// TODO: Auto-generated Javadoc
/**
 * This is the business class for the object EmailNotification.
 */
@JsonRootName( value = "user_email" )
@JsonPropertyOrder( {"sender_email",
    "sender_name",
    "recipient",
    "subject",
    "message",
    "cc",
    "cci"
} )
public class NotifyGruEmailNotification
{
    /** The _str sender name. */
    // Variables declarations 
    private String _strSenderName;

    /** The _str sender email. */
    private String _strSenderEmail;

    /** The _str recipient. */
    private String _strRecipient;

    /** The _str subject. */
    private String _strSubject;

    /** The _str message. */
    private String _strMessage;

    /** The _str cc. */
    @JsonInclude( JsonInclude.Include.NON_NULL )
    private String _strCc;

    /** The _str cci. */
    @JsonInclude( JsonInclude.Include.NON_NULL )
    private String _strCci;

    /**
     * Returns the SenderName.
     *
     * @return The SenderName
     */
    @JsonProperty( "sender_name" )
    public String getSenderName(  )
    {
        return _strSenderName;
    }

    /**
     * Sets the SenderName.
     *
     * @param strSenderName The SenderName
     */
    @JsonProperty( "sender_name" )
    public void setSenderName( String strSenderName )
    {
        _strSenderName = strSenderName;
    }

    /**
     * Returns the SenderEmail.
     *
     * @return The SenderEmail
     */
    @JsonProperty( "sender_email" )
    public String getSenderEmail(  )
    {
        return _strSenderEmail;
    }

    /**
     * Sets the SenderEmail.
     *
     * @param strSenderEmail The SenderEmail
     */
    @JsonProperty( "sender_email" )
    public void setSenderEmail( String strSenderEmail )
    {
        _strSenderEmail = strSenderEmail;
    }

    /**
     * Returns the Recipient.
     *
     * @return The Recipient
     */
    @JsonProperty( "recipient" )
    public String getRecipient(  )
    {
        return _strRecipient;
    }

    /**
     * Sets the Recipient.
     *
     * @param strRecipient The Recipient
     */
    @JsonProperty( "recipient" )
    public void setRecipient( String strRecipient )
    {
        _strRecipient = strRecipient;
    }

    /**
     * Returns the Subject.
     *
     * @return The Subject
     */
    @JsonProperty( "subject" )
    public String getSubject(  )
    {
        return _strSubject;
    }

    /**
     * Sets the Subject.
     *
     * @param strSubject The Subject
     */
    @JsonProperty( "subject" )
    public void setSubject( String strSubject )
    {
        _strSubject = strSubject;
    }

    /**
     * Returns the Message.
     *
     * @return The Message
     */
    @JsonProperty( "message" )
    public String getMessage(  )
    {
        return _strMessage;
    }

    /**
     * Sets the Message.
     *
     * @param strMessage The Message
     */
    @JsonProperty( "message" )
    public void setMessage( String strMessage )
    {
        _strMessage = strMessage;
    }

    /**
     * Gets the cc.
     *
     * @return the cc
     */
    @JsonProperty( "cc" )
    public String getCc(  )
    {
        return _strCc;
    }

    /**
     * Sets the cc.
     *
     * @param strCc the new cc
     */
    @JsonProperty( "cc" )
    public void setCc( String strCc )
    {
        _strCc = strCc;
    }

    /**
     * Gets the cci.
     *
     * @return the cci
     */
    @JsonProperty( "cci" )
    public String getCci(  )
    {
        return _strCci;
    }

    /**
     * Sets the cci.
     *
     * @param strCci the new cci
     */
    @JsonProperty( "cci" )
    public void setCci( String strCci )
    {
        _strCci = strCci;
    }
}
