

# Introduction #

**If you are using GWT 1.x you can stop reading here and use the [built-in GWT support](http://docs.jboss.org/seam/2.2.1.CR1/reference/en-US/html/gwt.html#d0e23554).**

Otherwise, the gwt-seam-service will save you much time.

# Using seam-gen #

  1. Make sure your web.xml contains the `SeamResourceServlet`
```
   <servlet>
      <servlet-name>Seam Resource Servlet</servlet-name>
      <servlet-class>org.jboss.seam.servlet.SeamResourceServlet</servlet-class>
   </servlet>

   <servlet-mapping>
      <servlet-name>Seam Resource Servlet</servlet-name>
      <url-pattern>/seam/resource/*</url-pattern>
   </servlet-mapping>
```
  1. Download gwt-seam-seperate-x.zip from the download section
  1. If you are using a seam-gen generated project you must do the following:
```
MyGWTSeamProject
  |+ ....
  |+ lib
    |+ ....
    |+ gwt-servlet.jar //replace this with your GWT SDK gwt-servlet.jar
    |+ gwt-seam-rpc.jar
    |+ ....
  |+ ....
  |+ deployed-jars-ear.list //add jboss-seam-remoting.jar & gwt-servlet.jar & gwt-seam-rpc.jar
```
    * So in fact, all you have to do is update gwt-servlet.jar, copy gwt-seam-service.jar into your lib folder and add these to jars to the  deployed ear jars list. This setup should also work for Eclipse generated Seam Web projects.
  1. Change all service endpoints to `..seam/resource/gwtRpc`.
```
...
@RemoteServiceRelativePath("../seam/resource/gwtRpc")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
}
```
  1. Create your Service implementation (important: `@Name` & `@WebRemote`)
```
@Name("org.jboss.seam.example.remoting.gwt.client.GreetingService")
public class ServiceImpl implements MyService {
   @WebRemote // should be found in jboss-seam-remoting.jar -> org.jboss.seam.annotations.remoting.WebRemote
   public String askIt(String question) {
....
```
  1. Redeploy your project, point the GWT module page and enjoy the success!

# Using eclipse #

**TODO**


---


_Note: Radovan Sninský and Virgo created the quick fix which is included in the gwt-seam.jar:
http://virgo47.wordpress.com/2010/02/09/jboss-seam-gwt-2-support/_