package org.xzh.springboot.junit;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class IgnoreMain {

	@Ignore("Test is ignored as a demonstration")
	@Test
	public void testSame() {
	    Assert.assertThat(1, CoreMatchers.is(1));
	}
}
