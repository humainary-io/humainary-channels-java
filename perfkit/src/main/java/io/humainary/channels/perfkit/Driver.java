
/*
 * Copyright © 2022 JINSPIRED B.V.
 */

package io.humainary.channels.perfkit;


import io.humainary.channels.Channels.Channel;
import io.humainary.channels.Channels.Context;
import io.humainary.devkit.perfkit.PerfKit;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;

import static io.humainary.channels.Channels.context;
import static io.humainary.substrates.Substrates.Environment.EMPTY;
import static io.humainary.substrates.Substrates.*;


@State ( Scope.Benchmark )
public class Driver
  implements PerfKit.Driver {

  private static final Outlet< Integer >     OUTLET     = Outlet.empty ();
  private static final Subscriber< Integer > SUBSCRIBER = subscriber ( OUTLET );
  private static final Name                  NAME       = name ( "channel#1" );
  private static final Integer               VALUE      = 1;

  private Context< Integer > context;
  private Channel< Integer > channel;

  @Setup ( Level.Trial )
  public final void setup ()
  throws IOException {

    final var configuration =
      configuration ();

    context =
      context (
        Integer.class,
        environment (
          lookup (
            path ->
              configuration.apply (
                path.toString ()
              )
          )
        )
      );

    //noinspection resource
    context.subscribe (
      SUBSCRIBER
    );

    channel =
      context.channel (
        NAME
      );

  }

  @Benchmark
  public void context_get () {

    context.get (
      NAME
    );

  }

  @Benchmark
  public void context_channel () {

    context.channel (
      NAME
    );

  }

  @Benchmark
  public void context_subscribe_cancel () {

    context.subscribe (
      SUBSCRIBER
    ).close ();

  }

  @Benchmark
  public void context_consume_cancel () {

    context.consume (
      OUTLET
    ).close ();

  }

  @Benchmark
  public void context_iterator () {

    context.iterator ();

  }


  @Benchmark
  public void context_foreach () {

    for ( final Channel< ? > c : context ) {
      assert c != null;
    }

  }

  @Benchmark
  public void channels_context_default () {

    context (
      Integer.class
    );

  }

  @Benchmark
  public void channels_context_empty () {

    context (
      Integer.class,
      EMPTY
    );

  }

  @Benchmark
  public void channel_send () {

    channel.send (
      VALUE
    );

  }

}