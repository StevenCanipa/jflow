package xawd.jflow;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.PrimitiveIterator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;

import xawd.jflow.iterables.IterableDoubles;
import xawd.jflow.iterables.IterableInts;
import xawd.jflow.iterables.IterableLongs;
import xawd.jflow.iterators.SkippableDoubleIterator;
import xawd.jflow.zippedpairs.DoublePair;
import xawd.jflow.zippedpairs.DoubleWith;
import xawd.jflow.zippedpairs.DoubleWithLong;
import xawd.jflow.zippedpairs.IntWithDouble;

/**
 * @author ThomasB
 * @since 20 Apr 2018
 */
public interface DoubleFlow extends SkippableDoubleIterator
{
	DoubleFlow map(final DoubleUnaryOperator f);

	<T> Flow<T> mapToObject(DoubleFunction<T> f);

	LongFlow mapToLong(DoubleToLongFunction f);

	IntFlow mapToInt(DoubleToIntFunction f);

	<T> Flow<DoubleWith<T>> zipWith(final Iterator<T> other);

	Flow<DoublePair> zipWith(final PrimitiveIterator.OfDouble other);

	Flow<DoubleWithLong> zipWith(final PrimitiveIterator.OfLong other);

	Flow<IntWithDouble> zipWith(final PrimitiveIterator.OfInt other);

	DoubleFlow combineWith(final OfDouble other, final DoubleBinaryOperator combiner);

	Flow<IntWithDouble> enumerate();

	DoubleFlow take(final int n);

	DoubleFlow takeWhile(final DoublePredicate p);

	DoubleFlow skip(final int n);

	DoubleFlow skipWhile(final DoublePredicate p);

	DoubleFlow filter(final DoublePredicate p);

	DoubleFlow append(double... xs);

	DoubleFlow append(PrimitiveIterator.OfDouble other);

	DoubleFlow insert(PrimitiveIterator.OfDouble other);

	DoubleFlow insert(double... xs);

	OptionalDouble min();

	double min(double defaultValue);

	double minByKey(double defaultValue, final DoubleUnaryOperator key);

	OptionalDouble minByKey(final DoubleUnaryOperator key);

	<C extends Comparable<C>> OptionalDouble minByObjectKey(final DoubleFunction<C> key);

	OptionalDouble max();

	double max(double defaultValue);

	double maxByKey(double defaultValue, final DoubleUnaryOperator key);

	OptionalDouble maxByKey(final DoubleUnaryOperator key);

	<C extends Comparable<C>> OptionalDouble maxByObjectKey(final DoubleFunction<C> key);

	boolean allMatch(final DoublePredicate predicate);

	boolean anyMatch(final DoublePredicate predicate);

	boolean noneMatch(final DoublePredicate predicate);

	int count();

	double reduce(double id, DoubleBinaryOperator reducer);

	OptionalDouble reduce(DoubleBinaryOperator reducer);

	DoubleFlow accumulate(DoubleBinaryOperator accumulator);

	DoubleFlow accumulate(double id, DoubleBinaryOperator accumulator);

	default double[] toArray()
	{
		final ArrayAccumulators.OfDouble accumulater = ArrayAccumulators.doubleAccumulator();
		while (hasNext()) {
			accumulater.add(nextDouble());
		}
		return accumulater.compress();
	}

	default <K, V> Map<K, V> toMap(final DoubleFunction<K> keyMapper, final DoubleFunction<V> valueMapper)
	{
		final Map<K, V> collected = new HashMap<>();
		while (hasNext()) {
			final double next = nextDouble();
			final K key = keyMapper.apply(next);
			if (collected.containsKey(key)) {
				throw new IllegalStateException();
			}
			else {
				collected.put(key, valueMapper.apply(next));
			}
		}
		return collected;
	}

	default <K> Map<K, double[]> groupBy(final DoubleFunction<K> classifier)
	{
		final Map<K, ArrayAccumulators.OfDouble> accumulationMap = new HashMap<>();
		while (hasNext()) {
			final double next = nextDouble();
			final K key = classifier.apply(next);
			accumulationMap.putIfAbsent(key, ArrayAccumulators.doubleAccumulator());
			accumulationMap.get(key).add(next);
		}
		final Map<K, double[]> grouped = new HashMap<>(accumulationMap.size());
		for (final K key : accumulationMap.keySet()) {
			grouped.put(key, accumulationMap.get(key).compress());
		}
		return grouped;
	}

	default <T> Flow<DoubleWith<T>> zipWith(final Iterable<T> other)
	{
		return zipWith(other.iterator());
	}

	default Flow<DoublePair> zipWith(final IterableDoubles other)
	{
		return zipWith(other.iterator());
	}

	default Flow<DoubleWithLong> zipWith(final IterableLongs other)
	{
		return zipWith(other.iterator());
	}

	default Flow<IntWithDouble> zipWith(final IterableInts other)
	{
		return zipWith(other.iterator());
	}

	default DoubleFlow combineWith(final IterableDoubles other, final DoubleBinaryOperator combiner)
	{
		return combineWith(other.iterator(), combiner);
	}

	default DoubleFlow append(final IterableDoubles other)
	{
		return append(other.iterator());
	}

	default DoubleFlow insert(final IterableDoubles other)
	{
		return insert(other.iterator());
	}
}
