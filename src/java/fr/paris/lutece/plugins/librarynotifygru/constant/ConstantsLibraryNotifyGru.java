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
package fr.paris.lutece.plugins.librarynotifygru.constant;


// TODO: Auto-generated Javadoc
/**
 * The Class ConstantsLibraryNotifyGru.
 */
public final class ConstantsLibraryNotifyGru
{
    /** The Constant URL_NOTIFICATION_ENDPOINT. */
    //URL
    public static final String URL_NOTIFICATION_ENDPOINT = "library-notifygru.urlNotificationEndpoint";

    /** The Constant URL_TOKEN. */
    public static final String URL_TOKEN = "library-notifygru.urlApiManager";

    /** The Constant TYPE_AUTHENTIFICATION. */
    //CONSTANT FOR SENDING JSON FLUX
    public static final String TYPE_AUTHENTIFICATION = "Bearer";
    public static final String PROPERTY_HEADER_CONTENT_TYPE = "Content-Type";
    public static final String PROPERTY_HEADER_ACCEPT_TYPE = "Accept";
    public static final String END_POINT_SUCCESS_MSG = "received";
    public static final String END_POINT_SUCCESS_KEY = "status";
    public static final String END_POINT_ACKNOWLEDGE = "acknowledge";
    //{"acknowledge": {"status": "received"}}
    /** The Constant PARAMS_ACCES_TOKEN. */
    public static final String PARAMS_ACCES_TOKEN = "access_token";
    public static final String AUTHORIZATION = "Authorization";

    /** The Constant PARAMS_GRANT_TYPE. */
    public static final String PARAMS_GRANT_TYPE = "grant_type";

    /** The Constant PARAMS_GRANT_TYPE_VALUE. */
    public static final String PARAMS_GRANT_TYPE_VALUE = "client_credentials";

    /** The Constant DEFAULT_STRING. */
    public static final String DEFAULT_STRING = "";

    /** The Constant DEFAULT_INT. */
    public static final int DEFAULT_INT = -1;

    /** The Constant DEFAULT_LONG. */
    public static final long DEFAULT_LONG = -1;

    //INFORMATION ABOUT TOKEN

    /** The Constant CONTENT_FORMAT. */
    //CONTENT FORMAT
    public static final String CONTENT_FORMAT_CHARSET = "application/json; charset=utf-8";
    public static final String CONTENT_FORMAT = "application/json";

    /** The Constant CONTENT_FORMAT_TOKEN. */
    public static final String CONTENT_FORMAT_TOKEN = "application/x-www-form-urlencoded";

    /** The Constant ERROR_MESSAGE. */
    //HTTP ERROR MESSAGE
    public static final String ERROR_MESSAGE = "ClientNotification Failed : HTTP error code : ";

    /**
     * Instantiates a new constants library notify gru.
     *
     * @throws Exception the exception
     */
    private ConstantsLibraryNotifyGru(  ) throws Exception
    {
        throw new Exception(  );
    }
}
