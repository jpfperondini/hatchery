package io.kongcode.hatchery.test.core;

import java.time.Instant;

/**
 * Created by JoãoPedro on 14/08/2015.
 */
class BaseObject {

    private final Instant instant = Instant.now();

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        BaseObject that = (BaseObject) o;

        return !(instant != null ? !instant.equals(that.instant) : that.instant != null);

    }

    @Override public int hashCode() {
        return instant != null ? instant.hashCode() : 0;
    }
}
