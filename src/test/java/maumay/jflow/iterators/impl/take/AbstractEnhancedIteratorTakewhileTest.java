/**
 *
 */
package maumay.jflow.iterators.impl.take;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import maumay.jflow.iterators.AbstractEnhancedIterator;
import maumay.jflow.iterators.misc.Pair;
import maumay.jflow.testutilities.AbstractEnhancedIterable;
import maumay.jflow.testutilities.IteratorExampleProvider;
import maumay.jflow.testutilities.IteratorTest;

/**
 * @author t
 *
 */
public class AbstractEnhancedIteratorTakewhileTest extends IteratorExampleProvider
		implements IteratorTest
{
	@Test
	void test()
	{
		final AbstractEnhancedIterable<String> populated = getObjectTestIteratorProvider();
		final AbstractEnhancedIterable<String> empty = getEmptyObjectTestIteratorProvider();

		final List<Pair<List<String>, Predicate<String>>> testData = asList(
				Pair.of(asList(), string -> !string.equals("0")),
				Pair.of(asList("0", "1", "2"), string -> !string.equals("3")),
				Pair.of(asList("0", "1", "2", "3", "4"), string -> !string.equals("5")));

		testData.stream().forEach(testCase -> {
			assertObjectIteratorAsExpected(testCase._1(),
					createTakewhileIteratorProviderFrom(populated, testCase._2()));
			assertObjectIteratorAsExpected(asList(),
					createTakewhileIteratorProviderFrom(empty, testCase._2()));
		});
	}

	private <T> AbstractEnhancedIterable<T> createTakewhileIteratorProviderFrom(
			AbstractEnhancedIterable<T> src, Predicate<T> predicate)
	{
		return new AbstractEnhancedIterable<T>() {
			@Override
			public AbstractEnhancedIterator<T> iter()
			{
				return src.iter().takeWhile(predicate);
			}
		};
	}

}