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
 * Download.java
 * Copyright (C) 2018 University of Waikato, Hamilton, NZ
 */

package com.github.fracpete.inetutils4j.tools;

import com.github.fracpete.inetutils4j.api.Internet;
import com.github.fracpete.inetutils4j.api.Proxy;
import com.github.fracpete.inetutils4j.api.Proxy.ProxyType;
import com.github.fracpete.inetutils4j.core.DefaultCapture;
import com.github.fracpete.inetutils4j.core.NullCapture;
import com.github.fracpete.inetutils4j.core.OutputCapture;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

/**
 * Command-line tool for downloading files.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class Download {

  public static void main(String[] args) {
    ArgumentParser parser;
    Namespace ns;

    parser = ArgumentParsers.newArgumentParser(Download.class.getName());
    parser.description(
      "Allows the download of remote files.\n"
	+ "If proxies should require user/password, then you need to include these in the URL. "
	+ "For instance, for a HTTP proxy, use this format:\n"
	+ "  http://USER:PASSWORD@proxy.example.com:3128/");

    parser.addArgument("-r", "--remote")
      .dest("remote")
      .help("The URL of the remote file to download")
      .required(true);
    parser.addArgument("-l", "--local")
      .dest("local")
      .help("The local file to download the remote file to.")
      .required(true);
    parser.addArgument("-v", "--verbose")
      .dest("verbose")
      .help("increase verbosity")
      .setDefault(false)
      .action(Arguments.storeTrue());

    // proxy
    parser.addArgument("--http_proxy")
      .dest("http_proxy")
      .help("The URL of the HTTP proxy to use (including port)")
      .required(false)
      .setDefault("");
    parser.addArgument("--ftp_proxy")
      .dest("ftp_proxy")
      .help("The URL of the FTP proxy to use (including port)")
      .required(false)
      .setDefault("");
    parser.addArgument("--socks_proxy")
      .dest("socks_proxy")
      .help("The URL of the socks proxy to use (including port)")
      .required(false)
      .setDefault("");

    try {
      ns = parser.parseArgs(args);
    }
    catch (ArgumentParserException e) {
      parser.handleError(e);
      return;
    }

    // proxy
    if (!ns.getString("http_proxy").isEmpty())
      Proxy.setProxy(ProxyType.HTTP, ns.getString("http_proxy"));
    if (!ns.getString("ftp_proxy").isEmpty())
      Proxy.setProxy(ProxyType.FTP, ns.getString("ftp_proxy"));
    if (!ns.getString("socks_proxy").isEmpty())
      Proxy.setProxy(ProxyType.SOCKS, ns.getString("socks_proxy"));

    // download
    OutputCapture capture;
    if (ns.getBoolean("verbose"))
      capture = new DefaultCapture();
    else
      capture = new NullCapture();
    String msg = Internet.download(
      ns.getString("remote"),
      ns.getString("local"),
      ns.getBoolean("verbose"),
      capture);
    if (msg != null)
      System.err.println(msg);
  }
}
