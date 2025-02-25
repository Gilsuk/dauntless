package portfolio.dauntless.test;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("acceptance")
class SampleTest {

    @Test
    void hello() {
        String hello = new Sample().hello();
        assertThat(hello).isEqualTo("hello");
    }
}