/**
 * 
 */
package com.github.maumay.jflow.testframework;

import com.github.maumay.jflow.impl.AbstractIterator;

/**
 * @author thomasb
 *
 */
public abstract class AbstractTestIterable<I extends AbstractIterator>
{
	public abstract I iter();
}