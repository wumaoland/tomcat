//
//  ========================================================================
//  Copyright (c) 1995-2013 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

package org.lmmarise.jetty.demo;

import org.eclipse.jetty.util.log.JavaUtilLog;
import org.eclipse.jetty.util.log.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.LogManager;

public final class LoggingUtil
{
    public static void config()
    {
        Log.setLog(new JavaUtilLog());
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        URL url = cl.getResource("logging.properties");
        if (url != null)
        {
            try (InputStream in = url.openStream())
            {
                LogManager.getLogManager().readConfiguration(in);
            }
            catch (IOException e)
            {
                e.printStackTrace(System.err);
            }
        }
        else
        {
            System.err.println("Unable to find logging.properties in classpath");
        }
    }
}
