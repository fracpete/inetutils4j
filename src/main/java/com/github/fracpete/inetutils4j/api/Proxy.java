/*
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * Proxy.java
 * Copyright (C) 2018 University of Waikato, Hamilton, NZ
 */

package com.github.fracpete.inetutils4j.api;

/**
 * Helper class for Proxy support.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class Proxy {

  /**
   * The proxy type.
   */
  public enum ProxyType {
    HTTP,
    FTP,
    SOCKS,
  }

  /**
   * Removes the port from the URL string.
   *
   * @param host	the host URL to process
   * @return		the processed URL
   */
  public static String removePort(String host) {
    String	result;
    int		port;

    result = host;
    port   = extractPort(host, -1);
    if (port > -1)
      result = result.replace(":" + port, "");

    return result;
  }

  /**
   * Retrieves the port from the URL string.
   *
   * @param host	the host URL to process
   * @param defPort	the default port to use
   * @return		the extracted port, defPort if none found
   */
  public static int extractPort(String host, int defPort) {
    int		result;
    int		pos;
    String	portStr;

    result = defPort;
    pos    = host.lastIndexOf(':');
    if (pos > -1) {
      try {
        portStr = host.substring(pos + 1);
        pos     = portStr.indexOf('/');
        if (pos > -1)
          portStr = portStr.substring(0, pos);
        result = Integer.parseInt(portStr);
      }
      catch (Exception e) {
        // ignored
      }
    }

    return result;
  }

  /**
   * Sets the system-wide proxy. Automatically extracts the port from the URL.
   *
   * @param type	the type of proxy
   * @param host	the host (including port)
   */
  public static void setProxy(ProxyType type, String host) {
    setProxy(type, removePort(host), extractPort(host, -1));
  }

  /**
   * Sets the system-wide proxy.
   *
   * @param type	the type of proxy
   * @param host	the host
   * @param port	the port
   */
  public static void setProxy(ProxyType type, String host, int port) {
    switch (type) {
      case HTTP:
	System.setProperty("http.proxyHost", host);
	System.setProperty("http.proxyPort", "" + port);
	break;
      case FTP:
	System.setProperty("ftp.proxyHost", host);
	System.setProperty("ftp.proxyPort", "" + port);
	break;
      case SOCKS:
	System.setProperty("socksProxyHost", host);
	System.setProperty("socksProxyPort", "" + port);
	break;
      default:
	throw new IllegalStateException("Unhandled proxy type: " + type);
    }
  }

  /**
   * Returns the host, if any.
   *
   * @param type	the type of proxy
   * @return		the host, empty string if none available
   */
  public static String getProxyHost(ProxyType type) {
    switch (type) {
      case HTTP:
	return System.getProperty("http.proxyHost", "");
      case FTP:
	return System.getProperty("ftp.proxyHost", "");
      case SOCKS:
	return System.getProperty("socksProxyHost", "");
      default:
	throw new IllegalStateException("Unhandled proxy type: " + type);
    }
  }

  /**
   * Returns the port, if any.
   *
   * @param type	the type of proxy
   * @return		the port, -1 if none available
   */
  public static int getProxyPort(ProxyType type) {
    switch (type) {
      case HTTP:
	return Integer.parseInt(System.getProperty("http.proxyPort", "-1"));
      case FTP:
	return Integer.parseInt(System.getProperty("ftp.proxyPort", "-1"));
      case SOCKS:
	return Integer.parseInt(System.getProperty("socksProxyPort", "-1"));
      default:
	throw new IllegalStateException("Unhandled proxy type: " + type);
    }
  }
}
