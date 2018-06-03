# inetutils4j
Helper classes for internet related operations, like proxy settings and 
downloading files.

## API

### Proxy
There are three types of proxies supported:
* HTTP
* FTP
* SOCKS

Use the `com.github.fracpete.inetutils4j.api.Proxy` class to set or query 
current proxy settings:

```java
import com.github.fracpete.inetutils4j.api.Proxy;
import com.github.fracpete.inetutils4j.api.Proxy.ProxyType;
...
Proxy.setProxy(ProxyType.HTTP, "http://proxy.example.com", 3128);
``` 

You can also supply the port in the URL:

```java
import com.github.fracpete.inetutils4j.api.Proxy;
import com.github.fracpete.inetutils4j.api.Proxy.ProxyType;
...
Proxy.setProxy(ProxyType.HTTP, "http://proxy.example.com:3128");
``` 

If you need to supply a user and password for the proxy, use this:

```java
import com.github.fracpete.inetutils4j.api.Proxy;
import com.github.fracpete.inetutils4j.api.Proxy.ProxyType;
...
Proxy.setProxy(ProxyType.HTTP, "http://USER:PASSWORD@proxy.example.com:3128");
``` 


### Downloading files

The `download` method of the `com.github.fracpete.inetutils4j.api.Internet`
class performs the actual download. Whether you want to capture any output
from running it in verbose, is handled by an instance of 
`com.github.fracpete.inetutils4j.core.OutputCapture`. Use `NullCapture`
to avoid any output or `DefaultCapture` to simply output to stdout/stderr.

```java
import com.github.fracpete.inetutils4j.api.Internet;
import com.github.fracpete.inetutils4j.core.DefaultCapture;
...
String msg = Internet.download(
  "http://myserver.example.com/myfile.txt",
  "/home/user/myfile.txt",
  true,
  new DefaultCapture());
System.err.println("An error occurred:\n" + msg);
```

**NB:** The `download` method automatically handles redirects.


### Downloading binary data

The `binaryContent` method of the `com.github.fracpete.inetutils4j.api.Internet`
class performs the download of a remote resources. Whether you want to capture any output
from running it in verbose, is handled by an instance of 
`com.github.fracpete.inetutils4j.core.OutputCapture`. Use `NullCapture`
to avoid any output or `DefaultCapture` to simply output to stdout/stderr.

```java
import com.github.fracpete.inetutils4j.api.Internet;
import com.github.fracpete.inetutils4j.core.DefaultCapture;
...
byte[] data = Internet.binaryContent(
  "http://myserver.example.com/myfile.txt",
  true,
  new DefaultCapture());
if (data == null)
  System.err.println("An error occurred!");
else
  System.out.println(data.length + " bytes downloaded");
```

**NB:** The `binaryContent` method automatically handles redirects.


### Downloading textual data

The `textualContent` method of the `com.github.fracpete.inetutils4j.api.Internet`
class performs the download of a remote resources. Whether you want to capture any output
from running it in verbose, is handled by an instance of 
`com.github.fracpete.inetutils4j.core.OutputCapture`. Use `NullCapture`
to avoid any output or `DefaultCapture` to simply output to stdout/stderr.

```java
import com.github.fracpete.inetutils4j.api.Internet;
import com.github.fracpete.inetutils4j.core.DefaultCapture;
...
String html = Internet.textualContent(
  "http://myserver.example.com/myfile.txt",
  "ISO-8859-1",
  true,
  new DefaultCapture());
if (html == null)
  System.err.println("An error occurred!");
else
  System.out.println(html);
```

**NB:** The `textualContent` method automatically handles redirects.


## Command-line tools

### Download
```
usage: com.github.fracpete.inetutils4j.tools.Download
       [-h] -r REMOTE -l LOCAL [-v] [--http_proxy HTTP_PROXY]
       [--ftp_proxy FTP_PROXY] [--socks_proxy SOCKS_PROXY]

Allows the download of remote files.
If proxies should require user/password, then  you need to include these in
the URL. For instance, for a HTTP proxy, use this format:
  http://USER:PASSWORD@proxy.example.com:3128/

optional arguments:
  -h, --help             show this help message and exit
  -r REMOTE, --remote REMOTE
                         The URL of the remote file to download
  -l LOCAL, --local LOCAL
                         The local file to download the remote file to.
  -v, --verbose          increase verbosity
  --http_proxy HTTP_PROXY
                         The URL of the HTTP proxy to use (including port)
  --ftp_proxy FTP_PROXY  The URL of the FTP proxy to use (including port)
  --socks_proxy SOCKS_PROXY
                         The URL of the socks proxy to use (including port)
```

## Maven

Add the following artifact to your dependencies of your `pom.xml`:

```xml
    <dependency>
      <groupId>com.github.fracpete</groupId>
      <artifactId>inetutils4j</artifactId>
      <version>0.0.2</version>
    </dependency>
```

## Releases

The following releases are available:

* [0.0.2](https://github.com/fracpete/inetutils4j/releases/download/inetutils4j-0.0.2/inetutils4j-0.0.2-spring-boot.jar)
* [0.0.1](https://github.com/fracpete/inetutils4j/releases/download/inetutils4j-0.0.1/inetutils4j-0.0.1-spring-boot.jar)
