# What is FluentLenium ?

[![Travis](https://img.shields.io/travis/FluentLenium/FluentLenium.svg)](https://travis-ci.org/FluentLenium/FluentLenium)
[![Coveralls](https://img.shields.io/coveralls/FluentLenium/FluentLenium.svg)](https://coveralls.io/github/FluentLenium/FluentLenium)
[![Maintainability](https://api.codeclimate.com/v1/badges/27aabb596e9d9eee7182/maintainability)](https://codeclimate.com/github/FluentLenium/FluentLenium/maintainability)
[![Maven Central](https://img.shields.io/maven-central/v/org.fluentlenium/fluentlenium-parent.svg)](http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22org.fluentlenium%22%20AND%20a%3A%22fluentlenium-parent%22)
[![Website](https://img.shields.io/website-up-down-green-red/http/fluentlenium.org.svg)](http://fluentlenium.org)

FluentLenium helps you writing readable, reusable, reliable and resilient UI functional tests for the browser.

FluentLenium provides a Java [fluent interface](http://en.wikipedia.org/wiki/Fluent_interface) to
[Selenium](http://www.seleniumhq.org/), and brings some magic to avoid common issues faced by Selenium users.

FluentLenium is shipped with adapters for [JUnit4](https://junit.org/junit4/), [JUnit5](https://junit.org/junit5/), [TestNG](http://testng.org/doc/index.html), [Spock](http://spockframework.org/) and [Cucumber](https://cucumber.io), but it can also be used standalone.

FluentLenium best integrates with [AssertJ](http://joel-costigliola.github.io/assertj/), but you can also choose to use
the assertion framework you want.

# Choose the right version

FluentLenium 4.x is the newest version of FluentLenium it is based on JDK 11 it includes latest enhancements and features, but Selenium 3 is required to run it.

FluentLenium 3.x is based on JDK 1.8 - we are not going to add new features to this version but still planning work on bugfixes.

FluentLenium 1.x is in maintenance state, and no new feature will be added anymore. It requires Selenium 2 and
Java 7, but can also be used with Java 8. Selenium 3 is not supported in this version though.

Starting from FluentLenium 3.1.0 you can use all sparks of Java 8, including lambdas. It is a nice extension in
comparison to Selenium 3 which is still basing on Guava objects. Please take a look on documentation to find `await`
lambda usage example.

If you want to keep up to date please upgrade your testing framework to FluentLenium 4.x

# Quickstart with JUnit and AssertJ

- Add dependencies to your `pom.xml`.

```xml
<properties>
    <!-- Configure this property to latest available version -->
    <fluentlenium.version>4.0.0</fluentlenium.version>
    <!-- Make sure the selenium.version won't be overriden by another pom.xml -->
    <selenium.version>3.141.59</selenium.version>
</properties>

<dependency>
    <groupId>org.fluentlenium</groupId>
    <artifactId>fluentlenium-junit</artifactId>
    <version>4.0.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.fluentlenium</groupId>
    <artifactId>fluentlenium-assertj</artifactId>
    <version>4.0.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.12</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>htmlunit-driver</artifactId>
    <version>2.33.3</version>
    <scope>test</scope>
</dependency>
```

- Basic FluentLenium test

```java
import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.core.hook.wait.Wait;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@Wait
public class DuckDuckGoTest extends FluentTest {
    @Test
    public void titleOfDuckDuckGoShouldContainSearchQueryName() {
        goTo("https://duckduckgo.com");
        $("#search_form_input_homepage").fill().with("FluentLenium");
        $("#search_button_homepage").submit();
        assertThat(window().title()).contains("FluentLenium");
    }
}
```

- A little bit more advanced version of the same FluentLenium test

```java
import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.junit.Test;

public class DuckDuckGoTest extends FluentTest {
    @Page
    DuckDuckMainPage duckDuckMainPage;

    @Test
    public void titleOfDuckDuckGoShouldContainSearchQueryName() {
        String searchPhrase = "searchPhrase";

        goTo(duckDuckMainPage)
                .typeSearchPhraseIn(searchPhrase)
                .submitSearchForm()
                .assertIsPhrasePresentInTheResults(searchPhrase);
    }
}
```

```java
import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

@PageUrl("https://duckduckgo.com")
public class DuckDuckMainPage extends FluentPage {
    private static final String SEARCH_FORM_HOMEPAGE = "#search_form_homepage";

    @FindBy(css = "#search_form_input_homepage")
    private FluentWebElement searchInput;

    @FindBy(css = "#search_button_homepage")
    private FluentWebElement searchButton;

    public DuckDuckMainPage typeSearchPhraseIn(String searchPhrase) {
        searchInput.write(searchPhrase);
        return this;
    }

    public DuckDuckMainPage submitSearchForm() {
        searchButton.submit();
        awaitUntilSearchFormDisappear();
        return this;
    }

    public void assertIsPhrasePresentInTheResults(String searchPhrase) {
        assertThat(window().title()).contains(searchPhrase);
    }

    private DuckDuckMainPage awaitUntilSearchFormDisappear() {
        await().atMost(5, TimeUnit.SECONDS).until(el(SEARCH_FORM_HOMEPAGE)).not().present();
        return this;
    }
}
```

- Run as a JUnit test.

[More FluentLenium examples are available on github](https://github.com/FluentLenium/FluentLenium/tree/develop/examples).

## Documentation

Full documentation is available on [fluentlenium.org](http://fluentlenium.org), or in the
[docs sources directory](https://github.com/FluentLenium/FluentLenium/tree/develop/docs).

## Contact Us
If you have any comment, remark or issue, please open an issue on
[FluentLenium Issue Tracker](https://github.com/FluentLenium/FluentLenium/issues)
