/**
 *
 */
package com.github.maumay.jflow.iterators.impl.scan;

import static java.util.Arrays.asList;

import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;

import com.github.maumay.jflow.impl.AbstractRichIterator;
import com.github.maumay.jflow.testutilities.AbstractRichIterable;
import com.github.maumay.jflow.testutilities.IteratorExampleProviders;
import com.github.maumay.jflow.testutilities.FiniteIteratorTest;

/**
 * @author ThomasB
 *
 */
class AbstractEnhancedIteratorAccumulationTest extends IteratorExampleProviders
		implements FiniteIteratorTest
{

	@Test
	void testAccumulationWithId()
	{
		AbstractRichIterable<String> populated = getObjectIteratorProviders();
		AbstractRichIterable<String> empty = getEmptyObjectIteratorProvider();

		assertIteratorAsExpected(asList("x", "x0", "x01", "x012", "x0123", "x01234"),
				createAccumlationWithIdIteratorProviderFrom(populated, "x", String::concat));
		assertIteratorAsExpected(asList("x"),
				createAccumlationWithIdIteratorProviderFrom(empty, "x", String::concat));
	}

	private <E, R> AbstractRichIterable<R> createAccumlationWithIdIteratorProviderFrom(
			AbstractRichIterable<E> source, R id, BiFunction<R, E, R> accumulator)
	{
		return new AbstractRichIterable<R>() {
			@Override
			public AbstractRichIterator<R> iter()
			{
				return source.iter().scan(id, accumulator);
			}
		};
	}
}
