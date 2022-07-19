/*
 * Copyright © 2022 JINSPIRED B.V.
 */

package io.humainary.channels;

import io.humainary.channels.spi.ChannelsProvider;
import io.humainary.spi.Providers;
import io.humainary.substrates.Substrates;
import io.humainary.substrates.Substrates.Environment;
import io.humainary.substrates.Substrates.Instrument;
import io.humainary.substrates.Substrates.Name;
import io.humainary.substrates.Substrates.Type;

import static io.humainary.substrates.Substrates.name;
import static io.humainary.substrates.Substrates.type;

/**
 * An open and extensible interface that offers a generic data flow interface for in-memory movement of observability-related data
 * between sources and outlets within an observed process or service. Much like the Substrates project, it is a project that is utilized
 * by other Humainary projects but at runtime within the SPI infrastructure. It is observability for observability.
 */

public final class Channels {

  private static final ChannelsProvider PROVIDER =
    Providers.create (
      "io.humainary.channels.spi.factory",
      "io.kanalis.channels.spi.alpha.ProviderFactory",
      ChannelsProvider.class
    );

  private Channels () {
  }

  /**
   * This can be used to inspect the generic class type of the context or channel
   */

  public static final Name TYPE = name ( "io.humainary.channels.channel.type.class" );

  /**
   * Returns the default {@link Context}.
   *
   * @return The default {@link Context}
   * @see ChannelsProvider#context(Class)
   */

  public static < T > Context< T > context (
    final Class< T > type
  ) {

    return
      PROVIDER.context (
        type
      );

  }


  /**
   * Returns a {@link Context} mapped based on one or more properties within {@link Environment} provided.
   * <p>
   * Implementation Notes:
   * — The context returned should equal a context returned with the same environment properties.
   *
   * @param environment the configuration used in the mapping and construction of a {@link Context}
   * @return A {@link Context} constructed from and mapped to the {@link Environment}
   * @see ChannelsProvider#context(Class, Environment)
   */

  public static < T > Context< T > context (
    final Class< T > type,
    final Environment environment
  ) {

    return
      PROVIDER.context (
        type,
        environment
      );

  }


  /**
   * A context represents some configured boundary within a process space.
   * <p>
   * Note: An SPI implementation of this interface is free to override
   * the default methods implementation included here.
   *
   * @see Channels#context(Class, Environment)
   */

  public interface Context< T >
    extends Substrates.Context< Channel< T >, T > {

    /**
     * Returns the {@link Channel} mapped to the specified {@code Name}
     *
     * @param name the {@code Name} to be used to look and possibly create the {@link Channel}
     * @return A non-null {@link Channel} reference
     * @see #get(Name)
     */

    Channel< T > channel (
      final Name name
    );

  }

  /**
   * An interface that represents an instrument used for transmitting payloads
   */

  public interface Channel< T > extends Instrument {


    /**
     * The {@link Type} used to identify the interface of this referent type
     */

    Type TYPE = type ( Channel.class );


    /**
     * Sends an emittance along the channel
     *
     * @param value the value to be sent
     */

    void send (
      T value
    );

  }

}
