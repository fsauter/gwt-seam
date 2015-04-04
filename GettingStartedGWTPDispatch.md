

## Requirements ##

  * [GWTP 0.6](http://code.google.com/p/gwt-platform/downloads/detail?name=gwtp-all-0.6.zip)

## Notice ##

You should already be familiar with the [GWTP Dispatch Module](http://code.google.com/p/gwt-platform/wiki/GettingStartedDispatch) and a working GWTP project skeleton in your src folder which inherits the GWTP Dispatch Module.

## Setting up server ##

  1. Copy (override) gwt-servlet.jar to EAR project, lib folder. (this jar is from gwt and not from seam)
  1. download gwt-seam.jar from the download section
  1. Add gwt-seam.jar to
    * your lib folder
    * the build path
    * the deployed ear jars

## The configuration ##

Create the following component:

```
@Name(DispatchConfiguration.COMPONENT_NAME)
@SecurityCookieFilterConfig(filterClass = HttpSessionSecurityCookieFilter.class, cookieName = "MYCOOKIE")
public class SeamDispatchConfiguration implements DispatchConfiguration {

	@Override
	public void configureHandlers(HandlerRegistry handlerRegistry) {
		handlerRegistry.bindHandler(HelloWorldAction.class, HelloWorldHandler.class, LoggedInActionValidator.class);
	}

}
```

Please be aware of the `@Name(DispatchConfiguration.COMPONENT_NAME)` annotation (it has to be this name and not any other). This component is equivalent to [GWTP's Guice Handler Module](http://code.google.com/p/gwt-platform/source/browse/gwtp-core/gwtp-dispatch-server/src/main/java/com/gwtplatform/dispatch/server/guice/HandlerModule.java).

## The action and result ##
Only thing to know: The serviceName/endpoint have to be `seam/resource/gwtRpc`.

```
@GenDispatch(serviceName=DispatchConfiguration.DEFAULT_ENDPOINT, isSecure=false)
public class HelloWorld {

	@In(1) String name;
	
	@Out(1) String greeting;
}
```

## The handler ##
It is important that this handler is annotated with `@Name` to tell anybody (particularly seam) that this is a component which can be managed by seam. The `ScopeType` should be Application so seam does not have to create each time a new instance

```
@Name("helloWorldHandler")
@Scope(ScopeType.APPLICATION)
public class HelloWorldHandler implements ActionHandler<HelloWorldAction, HelloWorldResult> {

	@Override
	public HelloWorldResult execute(HelloWorldAction action, ExecutionContext context) throws ActionException {
		HelloWorldResult result = new HelloWorldResult("Hello " + action.getName() + "!");
		return result;
	}

	@Override
	public void undo(HelloWorldAction action, HelloWorldResult result, ExecutionContext context) throws ActionException {
		// Nothing to do.
	}

	@Override
	public Class<HelloWorldAction> getActionType() {
		return HelloWorldAction.class;
	}
}
```


## The validator ##
It is important that this validator is also annotated with `@Name`.

The great thing is, you've got access to all contexts (e.g. you could say `@In MyCompontent myComponent` etc.)

```
@Name("loggedInActionValidator")
@Scope(ScopeType.APPLICATION)
public class LoggedInActionValidator implements ActionValidator {
	
	@Override
	public boolean isValid(Action<? extends Result> action) throws ActionException {
		return Identity.instance().isLoggedIn();
	}

}
```

## Protecting against XSRF attacks ##

Have a look at the following topic for an short introduction: <a href='http://code.google.com/p/gwt-platform/wiki/GettingStartedDispatch#Protecting_against_XSRF_attacks'>GWTP & XSRF attacks</a>

You can add either `HttpSessionSecurityCookieFilter` or `RandomSessionSecurityCookieFilter` to your list of filters. To do so, add the following annotation at the top of your `DispatchConfiguration` component:

```
...
import com.gwtplatform.dispatch.server.seam.HttpSessionSecurityCookieFilter;
....
@SecurityCookieFilterConfig(filterClass = HttpSessionSecurityCookieFilter.class, cookieName = "MYCOOKIE")
public class SeamDispatchConfiguration implements DispatchConfiguration {
...
```

```
...
import com.gwtplatform.dispatch.server.seam.RandomSessionSecurityCookieFilter;
....
@SecurityCookieFilterConfig(filterClass = RandomSessionSecurityCookieFilter.class, cookieName = "MYCOOKIE")
public class SeamDispatchConfiguration implements DispatchConfiguration {
...
```

By default gwt-seam is using the following filter url pattern:
```
SecurityCookieFilterConfig.DEFAULT_URL_PATTERN // -> /seam/resource/*
```
You can change this pattern by adding the property `urlPattern` to your `@SecurityCookieFilterConfig` (equivalent to the guice `filter("*.html").through( HttpSessionSecurityCookieFilter.class );` line)