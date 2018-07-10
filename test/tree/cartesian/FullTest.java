/*
 * Copyright (C) 2018 pkehl
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package tree.cartesian;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pkehl
 */
public class FullTest {
	
	public FullTest() {
	}
	
	String numbersAsStrings[]= {"4", "2", "7", "3", "1", "5", "0", "8", "-1", "6", "9", "-2"};
	
	@Test
	public void parseArgs() {
		Integer nums[]= Engine.parseArgs(numbersAsStrings);
		assertArrayEquals("Not parsed well.", nums, new Integer[]{
			4, 2, 7, 3, 1, 5, 0, 8, -1, 6, 9, -2
		} );
	}
	
	String expectedOutput=
"                       -2\n" +
"                -1~~~~~^\n" +
"            0~~~^\n" +
"        1~~~^\n" +
"  2~~~~~^\n" +
"  ^~~~3\n" +
"4~^\n" +
"        ^~5\n" +
"                ^~~6\n" +
"    7~^\n" +
"            ^~8\n" +
"                   ^~9";
	
	private void assertEngineBuildsAndFormats( Engine engine ) {
		String formatted= engine.buildAndFormat(numbersAsStrings);
		assertEquals( formatted, expectedOutput );
	}
	
	@Test
	public void all() {
		assertEngineBuildsAndFormats( new EngineSimpleRecursive() );
		assertEngineBuildsAndFormats( new EngineKeepLastInserted() );
	}
}
