## Note: This feature is no longer available since version 0.2.1 ##

# Custom endpoint #

By default you have to use ../seam/resource/gwtp as resource endpoint but you can also define your own.

Create a `gwtseam.properties` file with the following contents:
```
gwt.rpc.endpoint=dispatcher
```

Adapt the `RemoteServiceRelativePath` of your services:
```
...
@RemoteServiceRelativePath("../seam/resource/dispatcher")
public interface GreetingService extends RemoteService {
        String greetServer(String name) throws IllegalArgumentException;
}
```

**Note:** The property file must be placed into the jar of your ear.

Example project structure:
```
helloworld.ear
  |+ helloworld.war // Contains web pages etc.
  |+ helloworld.jar // Contains Seam components
    |+ META-INF/
    |+ gwtseam.properties
    |+ seam.properties
    |+ com
      |+ ....
```