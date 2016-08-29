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
package fr.paris.lutece.plugins.librarynotifygru.constant;


/**
 * The Class ConstantsLibraryNotifyGru.
 */
public final class ConstantsLibraryNotifyGru
{
    /** CONSTANT FOR SENDING JSON FLUX */
    public static final String CONTENT_FORMAT_TOKEN = "application/x-www-form-urlencoded";
    public static final String PROPERTY_HEADER_CONTENT_TYPE = "Content-Type";
    public static final String PROPERTY_HEADER_ACCEPT_TYPE = "Accept";
    public static final String PROPERTY_HEADER_NOTIFICATION_SENDER = "NotificationSender";

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

    /**
     * final class of constants, don't have to be instantiated
     *
     * @throws Exception the exception
     */
    private ConstantsLibraryNotifyGru(  ) throws Exception
    {
        throw new Exception(  );
    }
}
