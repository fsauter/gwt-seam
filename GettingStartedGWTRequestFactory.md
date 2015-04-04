# Introduction #

Enable `RequestFactory` support for your Seam & GWT project.

# Details #

**Note:** You should already be familiar with the following topic:
http://code.google.com/webtoolkit/doc/latest/DevGuideRequestFactory.html

  * Download gwt-seam-seperate-x.zip and add gwt-seam-request-factory.jar to your deployed ear list.
  * To get started add the following code to your resources/WEB-INF/web.xml:

```xml

<servlet>
<servlet-name>requestFactoryServlet

Unknown end tag for &lt;/servlet-name&gt;


<servlet-class>com.example.module.server.SeamRequestFactoryServlet

Unknown end tag for &lt;/servlet-class&gt;




Unknown end tag for &lt;/servlet&gt;



<servlet-mapping>
<servlet-name>requestFactoryServlet

Unknown end tag for &lt;/servlet-name&gt;


<url-pattern>/gwtRequest

Unknown end tag for &lt;/url-pattern&gt;




Unknown end tag for &lt;/servlet-mapping&gt;


```

  * Then define a `RequestContex` which is using the `SeamComponentServiceLocator` class as locator.

```java

@Service(value = EmployeeRequestService.class, locator = SeamComponentServiceLocator.class)
public interface EmployeeRequest extends RequestContext {
Request<Integer> countEmployees();
}
```
  * And use the following service implementation of the above `RequestContext`.
```java

@Name("employeeRequestService")
public class EmployeeRequestService {

@In(create=true)
EmployeeList employeeList;

public Integer countEmployees() {
return employeeList.getFullResultList().size();
}

}
```

Also see: [GWT RequestFactory with JBoss 6 and Seam 2](http://floonit.blogspot.com/2011/05/gwt-requestfactory-with-jboss-6-and.html)