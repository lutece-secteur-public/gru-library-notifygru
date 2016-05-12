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

import fr.paris.lutece.plugins.librarynotifygru.business.INotification;

import java.util.Map;


// TODO: Auto-generated Javadoc
/**
 * The Interface IsendNotificationAsJson.
 *
 * @author root
 */
public interface IsendNotificationAsJson
{
    /** The Constant HTTP_CODE_RESPONSE_CREATED. */
    int HTTP_CODE_RESPONSE_CREATED = 201;

     void send( INotification notification, String strToken, Map<String, String> headers, String url );
     
    /**
     * Send.
     *
     * @param notification the notification
     * @param strToken the str token
     * @param headers the headers
     */
    void send( INotification notification, String strToken, Map<String, String> headers );

    /**
     * Send.
     *
     * @param notification the notification
     * @param strToken the str token
     */
    void send( INotification notification, String strToken );

    /**
     * Send.
     *
     * @param notification the notification
     */
    void send( INotification notification );

    /**
     * Gets the token.
     *
     * @param strToken the str token
     * @return the token
     */
    String getToken( String strToken );
}
