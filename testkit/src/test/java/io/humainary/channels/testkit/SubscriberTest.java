/*
 * Copyright © 2022 JINSPIRED B.V.
 */

package io.humainary.channels.testkit;

import io.humainary.channels.Channels.Channel;
import io.humainary.channels.Channels.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.humainary.channels.Channels.context;
import static io.humainary.devkit.testkit.TestKit.capture;
import static io.humainary.devkit.testkit.TestKit.recorder;
import static io.humainary.substrates.Substrates.Environment.EMPTY;
import static io.humainary.substrates.Substrates.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The test class for the {@link Subscriber} and {@link Subscription} interfaces.
 *
 * @author wlouth
 * @since 1.0
 */

final class SubscriberTest {

  private static final Integer VALUE_1 = 1;
  private static final Integer VALUE_2 = 2;

  private Context< Integer > context;
  private Channel< Integer > c1;
  private Channel< Integer > c2;

  @BeforeEach
  void setup () {

    context =
      context (
        Integer.class,
        EMPTY
      );

    c1 =
      context.channel (
        name (
          "#1"
        )
      );


    c2 =
      context.channel (
        name (
          "#2"
        )
      );

  }

  @Test
  void subscribe () {

    final var recorder =
      recorder (
        context
      );

    recorder.start ();

    c1.send ( VALUE_1 );
    c2.send ( VALUE_1 );
    c1.send ( VALUE_2 );
    c2.send ( VALUE_2 );

    final var capture =
      recorder
        .stop ()
        .orElseThrow (
          AssertionError::new
        );

    assertEquals (
      capture (
        c1,
        VALUE_1
      ).to (
        c2,
        VALUE_1
      ).to (
        c1,
        VALUE_2
      ).to (
        c2,
        VALUE_2
      ),
      capture
    );

  }

}
