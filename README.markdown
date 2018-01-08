# An opinionated React/Redux + Spring Boot Starter App

[![Build Status](https://travis-ci.org/wlindner/ReBoot.svg?branch=master)](https://travis-ci.org/wlindner/ReBoot)

![ReBoot](https://upload.wikimedia.org/wikipedia/en/3/37/Reboottitlecard.gif "ReBoot")

Be more productive with this simple project that uses the [spring dev tools](https://spring.io/blog/2015/06/17/devtools-in-spring-boot-1-3)
and [react transform](https://github.com/gaearon/babel-plugin-react-transform) for hot reloading.

Everything: backend, frontend and styles will be hot reloaded automatically.

This project also sets up spring security and [spring-sessions](http://projects.spring.io/spring-session/), which will
automatically store your sessions in Redis, allowing you to scale on multiple servers.

Both the frontend and the backend are fully tested.

## Developing

The Java code is available in the `backend` sub-project.
The `frontend` sub-project contains the javascript code.

### Running the backend (recommended)

The recommended way to launch the server is to use your favorite java IDE.
The main method of the application is in the `BootReactApplication` class.

### Running the frontend (recommended)

**You will need node 6.0+ and yarn to run the dev server and build the project**

The frontend dev server will be automatically launched when you start the backend.

I strongly recommend that you install `yarn` on your development machine.

To install download the dependencies needed by the frontend do:

```
cd frontend
yarn
```

If you do not have the required binaries on your machine, you can use `./gradlew frontend:yarn_install` and `./gradlew frontend:start`.
Those two command will download the required node/yarn versions automatically and use them to run the node tasks.

### Alternatives for running the projects

There is also a gradle task to run the spring server: `./gradlew bootRun`.

### Hot reloading

With the dev server running, saving your javascript files or stylus assets will automatically trigger the hot reloading
(without browser refresh) of the application.

For the backend, recompiling the project in your IDE will trigger the reloading of the application's class loader.

### Sessions

Sessions are stored in Redis with spring-sessions.
Spring-sessions allows you to transparently persist the HttpSession on Redis.
This allows to distribute the load on multiple servers if you choose to.

The application relies on a stateless REST api.
When they authenticate, clients will be given a token.
They will save this token in their local storage and send it as an HTTP header (`x-auth-token`).
This allows the retrieval of the session data in Redis.

If you want to use a real redis, you can run the application with the `redis` profile.

If the `redis` profile is not active, your session will be stored in a map.
See: [EmbeddedSessionConfig](https://github.com/wlindner/ReBoot/blob/master/backend/src/main/java/react/config/redis/EmbeddedSessionConfig.java).
This is great in development but you should avoid it in production.

### Active profiles

If your run your project with gradle, the system properties won't be passed on to Spring.
See [this issue](https://github.com/spring-projects/spring-boot/issues/832) for workarounds.

The simplest way to go is to specify active profiles in your IDE.

[Check out the doc](http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-profiles.html) to learn
more about profiles in Spring Boot.

To run the jar in production mode use the following command:

```
java -jar boot-react-0.0.1-SNAPSHOT.jar --spring.profiles.active=production                                                       16:57:01
```

### Security

The application is configured to work with Spring Security.
It uses an in-memory authentication but you are free use
[other implementations](http://docs.spring.io/spring-security/site/docs/4.0.2.RELEASE/reference/htmlsingle/#jc-authentication)
or to [roll your own](http://docs.spring.io/spring-security/site/docs/4.0.2.RELEASE/reference/htmlsingle/#core-services).

### Redux

This project uses [Redux](https://github.com/rackt/react-redux) to handle state and actions.
It is a simple library with very powerful dev tools.

Dan Abramov, the author of Redux, published a [great Redux video tutorial](https://egghead.io/series/getting-started-with-redux).

I also suggest reading the [redux quick start](https://github.com/rackt/react-redux/blob/master/docs/quick-start.md) to understand
how to architecture you application and the difference between containers and components.

Components are simple display elements that receive everything they need (state and actions) via props.

Containers are connected to Redux and as such, they can pull whatever properties they are interested in from the state
and bind actions to dispatch.
Those containers are only connecting simple components to Redux.

We can [write tests](http://rackt.github.io/redux/docs/recipes/WritingTests.html) on connected components,
but it is more effective to test them in isolation from Redux.

### Router push state

The project uses [react-router](https://github.com/rackt/react-router) to handle routes.
You can choose several modes to handles the router history.
By default, the project uses the browser history,
which creates the nicest URLs (**/login**, **/private**, etc.).

In development, we use a dev server that
[proxies](https://github.com/wlindner/ReBoot/blob/master/frontend/server.js#L21-L24) requests to the index.

In production, we have to use a special [resource handler](https://github.com/wlindner/ReBoot/blob/master/backend/src/main/java/react/config/SinglePageAppConfig.java)
to redirect all non-asset requests to the index.

You can remove it if you choose to use memory history (no URL change) or hash history
(**/\#/login**, **/#/private**).

## Running the tests

The check tasks will run the tests in both the frontend and the backend:

```
./gradlew check
```

You can run the backend/frontend tests only with:

```
./gradlew backend/frontend:test
```

To test the backend, we use a simple [library](https://github.com/geowarin/spring-spock-mvc) that wraps
spring mvc tests and makes them a bit nicer to read.
See the [auth-spec](https://github.com/wlindner/ReBoot/blob/master/backend/src/test/groovy/react/auth/AuthenticationSpec.groovy) for an example.

To test the frontend, we use [enzyme](https://github.com/airbnb/enzyme).

## Shipping

This command will generate an optimized bundle and include it in the jar.

```
./gradlew clean assemble
```

The jar will be available in `./backend/build/libs/boot-react-0.0.1-SNAPSHOT.jar`

You can then launch it with:

```
java -jar -Dspring.profiles.active=production backend/build/libs/boot-react-0.0.1-SNAPSHOT.jar
```

In production, you should use a real Redis instance so, please uncomment and edit the configuration file
(`backend/src/main/resources/application.yml`).

With spring boot 1.3, you can install the application [as a linux service](http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/html/deployment-install.html#deployment-service)

NB: each application can be assembled with the `assemble` task so you can use `frontend:assemble` or `backend:assemble`.
The backend task depends on the frontend task.
