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
 * Utils.java
 * Copyright (C) 2018 University of Waikato, Hamilton, NZ
 */

package com.github.fracpete.inetutils4j.core;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Helper class.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class Utils {

  /**
   * Returns the stacktrace of the throwable as string.
   *
   * @param t		the throwable to get the stacktrace for
   * @return		the stacktrace
   */
  public static String throwableToString(Throwable t) {
    return throwableToString(t, -1);
  }

  /**
   * Returns the stacktrace of the throwable as string.
   *
   * @param t		the throwable to get the stacktrace for
   * @param maxLines	the maximum number of lines to print, <= 0 for all
   * @return		the stacktrace
   */
  public static String throwableToString(Throwable t, int maxLines) {
    StringWriter writer;
    StringBuilder	result;
    String[]		lines;
    int			i;

    writer = new StringWriter();
    t.printStackTrace(new PrintWriter(writer));

    if (maxLines > 0) {
      result = new StringBuilder();
      lines  = writer.toString().split("\n");
      for (i = 0; i < maxLines; i++) {
	if (i > 0)
	  result.append("\n");
	result.append(lines[i]);
      }
    }
    else {
      result = new StringBuilder(writer.toString());
    }

    return result.toString();
  }

  /**
   * Closes the stream, if possible, suppressing any exception.
   *
   * @param is		the stream to close
   */
  public static void closeQuietly(InputStream is) {
    if (is != null) {
      try {
	is.close();
      }
      catch (Exception e) {
	// ignored
      }
    }
  }

  /**
   * Closes the stream, if possible, suppressing any exception.
   *
   * @param os		the stream to close
   */
  public static void closeQuietly(OutputStream os) {
    if (os != null) {
      try {
	os.flush();
      }
      catch (Exception e) {
	// ignored
      }
      try {
	os.close();
      }
      catch (Exception e) {
	// ignored
      }
    }
  }
}
