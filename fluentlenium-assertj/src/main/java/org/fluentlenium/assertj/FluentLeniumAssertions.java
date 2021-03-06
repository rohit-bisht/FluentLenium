package org.fluentlenium.assertj;

import org.assertj.core.api.Assertions;
import org.fluentlenium.assertj.custom.AlertAssert;
import org.fluentlenium.assertj.custom.FluentListAssert;
import org.fluentlenium.assertj.custom.FluentWebElementAssert;
import org.fluentlenium.assertj.custom.PageAssert;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.alert.AlertImpl;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;

/**
 * FluentLenium assertions entry point.
 */
public final class FluentLeniumAssertions extends Assertions {

    private FluentLeniumAssertions() {
        //only static
    }

    /**
     * Perform assertions on alert.
     *
     * @param actual actual alert
     * @return Alert assertion object
     */
    public static AlertAssert assertThat(AlertImpl actual) {
        return new AlertAssert(actual);
    }

    /**
     * Perform assertions on a {@link FluentPage}.
     *
     * @param actual actual page
     * @return Page assertion object
     */
    public static PageAssert assertThat(FluentPage actual) {
        return new PageAssert(actual);
    }

    /**
     * Perform assertions on a {@link FluentWebElement}.
     *
     * @param actual actual element
     * @return Element assertion object
     */
    public static FluentWebElementAssert assertThat(FluentWebElement actual) {
        return new FluentWebElementAssert(actual);
    }

    /**
     * Perform assertions on a {@link FluentList}.
     *
     * @param actual actual element list
     * @return Element list assertion object
     */
    public static FluentListAssert assertThat(FluentList<? extends FluentWebElement> actual) {
        return new FluentListAssert(actual);
    }

}
