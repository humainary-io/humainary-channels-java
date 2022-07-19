/*
 * Copyright © 2022 JINSPIRED B.V.
 */

package io.humainary.channels.spi;

import io.humainary.channels.Channels.Context;
import io.humainary.spi.Providers.Provider;
import io.humainary.substrates.Substrates.Environment;

/**
 * The service provider interface for the humainary channels runtime.
 * <p>
 * Note: An SPI implementation of this interface is free to override
 * the default methods implementation included here.
 *
 * @author wlouth
 * @since 1.0
 */

public interface ChannelsProvider
  extends Provider {

  < T > Context< T > context (
    Class< T > type
  );

  < T > Context< T > context (
    Class< T > type,
    Environment environment
  );

}
