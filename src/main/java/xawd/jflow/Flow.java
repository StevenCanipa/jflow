package xawd.jflow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PrimitiveIterator;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import xawd.jflow.iterables.DoubleFlowIterable;
import xawd.jflow.iterables.IntFlowIterable;
import xawd.jflow.iterables.LongFlowIterable;
import xawd.jflow.iterators.SkippableIterator;
import xawd.jflow.misc.PredicatePartition;
import xawd.jflow.zippedpairs.DoubleWith;
import xawd.jflow.zippedpairs.IntWith;
import xawd.jflow.zippedpairs.LongWith;
import xawd.jflow.zippedpairs.Pair;
import xawd.lists.listflow.ArrayListFlow;
import xawd.lists.listflow.ListFlow;

/**
 * @author ThomasB
 * @since 20 Apr 2018
 */
public interface Flow<T> extends SkippableIterator<T>
{
	default <R> R build(Function<? super Flow<? extends T>, R> builder)
	{
		return builder.apply(this);
	}

	default void process(Consumer<? super Flow<? extends T>> processor)
	{
		processor.accept(this);
	}

	<R> Flow<R> map(final Function<? super T, R> f);

	IntFlow mapToInt(ToIntFunction<? super T> f);

	DoubleFlow mapToDouble(ToDoubleFunction<? super T> f);

	LongFlow mapToLong(ToLongFunction<? super T> f);

	<R> Flow<Pair<T, R>> zipWith(final Iterator<R> other);

	Flow<IntWith<T>> zipWith(final PrimitiveIterator.OfInt other);

	Flow<DoubleWith<T>> zipWith(final PrimitiveIterator.OfDouble other);

	Flow<LongWith<T>> zipWith(final PrimitiveIterator.OfLong other);

	<U, R> Flow<R> combineWith(final Iterator<U> other, final BiFunction<T, U, R> f);

	Flow<IntWith<T>> enumerate();

	Flow<T> slice(final IntUnaryOperator f);

	Flow<T> take(final int n);

	Flow<T> takeWhile(final Predicate<? super T> p);

	Flow<T> skip(final int n);

	Flow<T> skipWhile(final Predicate<? super T> p);

	Flow<T> filter(final Predicate<? super T> p);

	Flow<T> append(Iterator<? extends T> other);

	Flow<T> insert(Iterator<? extends T> other);

	Flow<T> accumulate(BinaryOperator<T> accumulator);

	<R> Flow<R> accumulate(R id, BiFunction<R, T, R> accumulator);

	<R> Flow<R> flatten(Function<? super T, ? extends Flow<? extends R>> mapping);

	IntFlow flattenToInts(Function<? super T, ? extends IntFlow> mapping);

	LongFlow flattenToLongs(Function<? super T, ? extends LongFlow> mapping);

	DoubleFlow flattenToDoubles(Function<? super T, ? extends DoubleFlow> mapping);

	Optional<T> min(final ToDoubleFunction<? super T> key);

	<C extends Comparable<C>> Optional<T> minByObject(final Function<? super T, C> key);

	Optional<T> max(final ToDoubleFunction<T> key);

	<C extends Comparable<C>> Optional<T> maxByObject(final Function<? super T, C> key);

	T reduce(T id, BinaryOperator<T> reducer);

	Optional<T> reduce(BinaryOperator<T> reducer);

	boolean allMatch(final Predicate<? super T> predicate);

	boolean anyMatch(final Predicate<? super T> predicate);

	boolean noneMatch(final Predicate<? super T> predicate);

	int count();

	PredicatePartition<T> partition(Predicate<? super T> predicate);

	<C extends Collection<T>> C toCollection(final Supplier<C> collectionFactory);

	<K, V> Map<K, V> toMap(final Function<? super T, K> keyMapper, final Function<? super T, V> valueMapper);

	<K> Map<K, List<T>> groupBy(final Function<? super T, K> classifier);


	//********** DEFAULT METHODS ***********//

	default Flow<T> append(@SuppressWarnings("unchecked") final T... ts)
	{
		return append(Arrays.asList(ts));
	}

	default Flow<T> insert(@SuppressWarnings("unchecked") final T... ts)
	{
		return append(Arrays.asList(ts));
	}

	default List<T> toList()
	{
		return toCollection(ArrayList::new);
	}

	default ListFlow<T> toListFlow()
	{
		return toCollection(ArrayListFlow::new);
	}

	default List<T> toImmutableList()
	{
		return Collections.unmodifiableList(toList());
	}

	default Set<T> toImmutableSet()
	{
		return Collections.unmodifiableSet(toSet());
	}

	default Set<T> toSet()
	{
		return toCollection(HashSet::new);
	}

	default <R> Flow<Pair<T, R>> zipWith(final Iterable<R> other)
	{
		return zipWith(other.iterator());
	}

	default Flow<IntWith<T>> zipWith(final IntFlowIterable other)
	{
		return zipWith(other.iter());
	}

	default Flow<DoubleWith<T>> zipWith(final DoubleFlowIterable other)
	{
		return zipWith(other.iter());
	}

	default Flow<LongWith<T>> zipWith(final LongFlowIterable other)
	{
		return zipWith(other.iter());
	}

	default <U, R> Flow<R> combineWith(final Iterable<U> other, final BiFunction<T, U, R> f)
	{
		return combineWith(other.iterator(), f);
	}

	default Flow<T> append(final Iterable<? extends T> other)
	{
		return append(other.iterator());
	}

	default Flow<T> insert(final Iterable<? extends T> other)
	{
		return insert(other.iterator());
	}
}
