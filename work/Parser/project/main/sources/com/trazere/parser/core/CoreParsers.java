/*
 *  Copyright 2006-2013 Julien Dufour
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.trazere.parser.core;

import com.trazere.parser.Parser;
import com.trazere.parser.ParserException;
import com.trazere.util.collection.Lists;
import com.trazere.util.function.Function1;
import com.trazere.util.function.Function2;
import com.trazere.util.lang.HashCode;
import com.trazere.util.lang.LangUtils;
import com.trazere.util.type.Either;
import com.trazere.util.type.Maybe;
import com.trazere.util.type.Tuple1;
import com.trazere.util.type.Tuple2;
import com.trazere.util.type.Tuple3;
import com.trazere.util.type.Tuple4;
import com.trazere.util.type.Tuple5;
import com.trazere.util.type.Tuple6;
import com.trazere.util.type.Tuple7;
import com.trazere.util.type.Tuple8;
import java.util.List;

/**
 * DOCME
 */
public class CoreParsers {
	public static <Token, Result> Parser<Token, Result> identity(final Parser<Token, Result> subParser, final String description) {
		return new IdentityParser<Token, Result>(subParser, description);
	}
	
	public static <Token, SubResult, Result> Parser<Token, Result> lift(final Parser<Token, ? extends SubResult> subParser, final Result result, final String description) {
		return new LiftParser<Token, SubResult, Result>(subParser, result, description);
	}
	
	public static <Token, Result> Parser<Token, Result> default_(final Parser<Token, Maybe<Result>> subParser, final Result defaultResult, final String description) {
		return new DefaultParser<Token, Result>(subParser, defaultResult, description);
	}
	
	public static <Token, SubResult, Result> Parser<Token, Result> map(final Parser<Token, ? extends SubResult> subParser, final Function1<? super SubResult, ? extends Result, ? extends ParserException> function, final String description) {
		return new MapParser<Token, SubResult, Result>(subParser, function, description);
	}
	
	public static <Token, SubResult, Result> Parser<Token, Result> extract(final Parser<Token, ? extends SubResult> subParser, final Function1<? super SubResult, ? extends Maybe<? extends Result>, ? extends ParserException> extractor, final String description) {
		return new ExtractParser<Token, SubResult, Result>(subParser, extractor, description);
	}
	
	@SuppressWarnings("unchecked")
	public static <Token, Result> EOFParser<Token, Result> eof() {
		return (EOFParser<Token, Result>) _EOF;
	}
	
	private static final EOFParser<?, ?> _EOF = eof("end of file");
	
	public static <Token, Result> EOFParser<Token, Result> eof(final String description) {
		return new EOFParser<Token, Result>(description);
	}
	
	public static <Token, Result> OptionParser<Token, Result> option(final Parser<Token, Result> subParser, final String description) {
		return new OptionParser<Token, Result>(subParser, description);
	}
	
	public static <Token, Value> ManyParser<Token, Value> many(final Parser<Token, Value> subParser, final String description) {
		return new ManyParser<Token, Value>(subParser, description);
	}
	
	public static <Token, Value> ManyParser<Token, Value> many1(final Parser<Token, Value> subParser, final String description) {
		return new ManyParser<Token, Value>(subParser, 1, Integer.MAX_VALUE, description);
	}
	
	public static <Token, Value, Result> FoldParser<Token, Value, Result> fold(final Parser<Token, ? extends Value> valueParser, final Function2<? super Result, ? super Value, ? extends Result, ? extends ParserException> function, final int min, final int max, final Result initialValue, final String description) {
		assert null != function;
		
		final class FoldParser
		extends com.trazere.parser.core.FoldParser<Token, Value, Result> {
			public FoldParser() {
				super(valueParser, min, max, initialValue, description);
			}
			
			// Parser.
			
			private final Function2<? super Result, ? super Value, ? extends Result, ? extends ParserException> _function = function;
			
			@Override
			protected Result fold(final Result previousResult, final Value value)
			throws ParserException {
				return _function.evaluate(previousResult, value);
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode result = new HashCode(this);
				result.append(_description);
				result.append(_valueParser);
				result.append(_min);
				result.append(_max);
				result.append(_initialResult);
				result.append(_function);
				return result.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final FoldParser parser = (FoldParser) object;
					return LangUtils.safeEquals(_description, parser._description) && _valueParser.equals(parser._valueParser) && _min == parser._min && _max == parser._max && LangUtils.safeEquals(_initialResult, parser._initialResult) && _function.equals(parser._function);
				} else {
					return false;
				}
			}
		}
		return new FoldParser();
	}
	
	public static <Token, Value, Result> FoldParser<Token, Value, Result> fold(final Parser<Token, ? extends Value> subParser, final Function2<? super Result, ? super Value, ? extends Result, ? extends ParserException> function, final Result initialValue, final String description) {
		return fold(subParser, function, 0, Integer.MAX_VALUE, initialValue, description);
	}
	
	public static <Token, Value, Result> FoldParser<Token, Value, Result> fold1(final Parser<Token, ? extends Value> subParser, final Function2<? super Result, ? super Value, ? extends Result, ? extends ParserException> function, final Result initialValue, final String description) {
		return fold(subParser, function, 1, Integer.MAX_VALUE, initialValue, description);
	}
	
	public static <Token, SubResult1> Parser<Token, Tuple1<SubResult1>> sequence(final Parser<Token, ? extends SubResult1> subParser1, final String description) {
		final class SequenceParser
		extends Sequence1Parser<Token, SubResult1, Tuple1<SubResult1>> {
			public SequenceParser() {
				super(subParser1, description);
			}
			
			// Parser.
			
			@Override
			protected Tuple1<SubResult1> combine(final SubResult1 subResult1) {
				return Tuple1.build(subResult1);
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode result = new HashCode(this);
				result.append(_description);
				result.append(_subParser1);
				return result.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final SequenceParser parser = (SequenceParser) object;
					return LangUtils.safeEquals(_description, parser._description) && _subParser1.equals(parser._subParser1);
				} else {
					return false;
				}
			}
		}
		return new SequenceParser();
	}
	
	public static <Token, SubResult1, SubResult2> Parser<Token, Tuple2<SubResult1, SubResult2>> sequence(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final String description) {
		final class SequenceParser
		extends Sequence2Parser<Token, SubResult1, SubResult2, Tuple2<SubResult1, SubResult2>> {
			public SequenceParser() {
				super(subParser1, subParser2, description);
			}
			
			// Parser.
			
			@Override
			protected Tuple2<SubResult1, SubResult2> combine(final SubResult1 subResult1, final SubResult2 subResult2) {
				return Tuple2.build(subResult1, subResult2);
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode result = new HashCode(this);
				result.append(_description);
				result.append(_subParser1);
				result.append(_subParser2);
				return result.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final SequenceParser parser = (SequenceParser) object;
					return LangUtils.safeEquals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2);
				} else {
					return false;
				}
			}
		}
		return new SequenceParser();
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3> Parser<Token, Tuple3<SubResult1, SubResult2, SubResult3>> sequence(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final String description) {
		final class SequenceParser
		extends Sequence3Parser<Token, SubResult1, SubResult2, SubResult3, Tuple3<SubResult1, SubResult2, SubResult3>> {
			public SequenceParser() {
				super(subParser1, subParser2, subParser3, description);
			}
			
			// Parser.
			
			@Override
			protected Tuple3<SubResult1, SubResult2, SubResult3> combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3) {
				return Tuple3.build(subResult1, subResult2, subResult3);
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode result = new HashCode(this);
				result.append(_description);
				result.append(_subParser1);
				result.append(_subParser2);
				result.append(_subParser3);
				return result.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final SequenceParser parser = (SequenceParser) object;
					return LangUtils.safeEquals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3);
				} else {
					return false;
				}
			}
		}
		return new SequenceParser();
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3, SubResult4> Parser<Token, Tuple4<SubResult1, SubResult2, SubResult3, SubResult4>> sequence(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final String description) {
		final class SequenceParser
		extends Sequence4Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, Tuple4<SubResult1, SubResult2, SubResult3, SubResult4>> {
			public SequenceParser() {
				super(subParser1, subParser2, subParser3, subParser4, description);
			}
			
			// Parser.
			
			@Override
			protected Tuple4<SubResult1, SubResult2, SubResult3, SubResult4> combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4) {
				return Tuple4.build(subResult1, subResult2, subResult3, subResult4);
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode result = new HashCode(this);
				result.append(_description);
				result.append(_subParser1);
				result.append(_subParser2);
				result.append(_subParser3);
				result.append(_subParser4);
				return result.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final SequenceParser parser = (SequenceParser) object;
					return LangUtils.safeEquals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4);
				} else {
					return false;
				}
			}
		}
		return new SequenceParser();
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5> Parser<Token, Tuple5<SubResult1, SubResult2, SubResult3, SubResult4, SubResult5>> sequence(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final Parser<Token, ? extends SubResult5> subParser5, final String description) {
		final class SequenceParser
		extends Sequence5Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, Tuple5<SubResult1, SubResult2, SubResult3, SubResult4, SubResult5>> {
			public SequenceParser() {
				super(subParser1, subParser2, subParser3, subParser4, subParser5, description);
			}
			
			// Parser.
			
			@Override
			protected Tuple5<SubResult1, SubResult2, SubResult3, SubResult4, SubResult5> combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4, final SubResult5 subResult5) {
				return Tuple5.build(subResult1, subResult2, subResult3, subResult4, subResult5);
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode result = new HashCode(this);
				result.append(_description);
				result.append(_subParser1);
				result.append(_subParser2);
				result.append(_subParser3);
				result.append(_subParser4);
				result.append(_subParser5);
				return result.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final SequenceParser parser = (SequenceParser) object;
					return LangUtils.safeEquals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4) && _subParser5.equals(parser._subParser5);
				} else {
					return false;
				}
			}
		}
		return new SequenceParser();
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult6> Parser<Token, Tuple6<SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult6>> sequence(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final Parser<Token, ? extends SubResult5> subParser5, final Parser<Token, ? extends SubResult6> subParser6, final String description) {
		final class SequenceParser
		extends Sequence6Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult6, Tuple6<SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult6>> {
			public SequenceParser() {
				super(subParser1, subParser2, subParser3, subParser4, subParser5, subParser6, description);
			}
			
			// Parser.
			
			@Override
			protected Tuple6<SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult6> combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4, final SubResult5 subResult5, final SubResult6 subResult6) {
				return Tuple6.build(subResult1, subResult2, subResult3, subResult4, subResult5, subResult6);
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode result = new HashCode(this);
				result.append(_description);
				result.append(_subParser1);
				result.append(_subParser2);
				result.append(_subParser3);
				result.append(_subParser4);
				result.append(_subParser5);
				result.append(_subParser6);
				return result.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final SequenceParser parser = (SequenceParser) object;
					return LangUtils.safeEquals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4) && _subParser5.equals(parser._subParser5) && _subParser6.equals(parser._subParser6);
				} else {
					return false;
				}
			}
		}
		return new SequenceParser();
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult6, SubResult7> Parser<Token, Tuple7<SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult6, SubResult7>> sequence(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final Parser<Token, ? extends SubResult5> subParser5, final Parser<Token, ? extends SubResult6> subParser6, final Parser<Token, ? extends SubResult7> subParser7, final String description) {
		final class SequenceParser
		extends Sequence7Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult6, SubResult7, Tuple7<SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult6, SubResult7>> {
			public SequenceParser() {
				super(subParser1, subParser2, subParser3, subParser4, subParser5, subParser6, subParser7, description);
			}
			
			// Parser.
			
			@Override
			protected Tuple7<SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult6, SubResult7> combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4, final SubResult5 subResult5, final SubResult6 subResult6, final SubResult7 subResult7) {
				return Tuple7.build(subResult1, subResult2, subResult3, subResult4, subResult5, subResult6, subResult7);
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode result = new HashCode(this);
				result.append(_description);
				result.append(_subParser1);
				result.append(_subParser2);
				result.append(_subParser3);
				result.append(_subParser4);
				result.append(_subParser5);
				result.append(_subParser6);
				result.append(_subParser7);
				return result.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final SequenceParser parser = (SequenceParser) object;
					return LangUtils.safeEquals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4) && _subParser5.equals(parser._subParser5) && _subParser6.equals(parser._subParser6) && _subParser7.equals(parser._subParser7);
				} else {
					return false;
				}
			}
		}
		return new SequenceParser();
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult6, SubResult7, SubResult8> Parser<Token, Tuple8<SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult6, SubResult7, SubResult8>> sequence(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final Parser<Token, ? extends SubResult5> subParser5, final Parser<Token, ? extends SubResult6> subParser6, final Parser<Token, ? extends SubResult7> subParser7, final Parser<Token, ? extends SubResult8> subParser8, final String description) {
		final class SequenceParser
		extends Sequence8Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult6, SubResult7, SubResult8, Tuple8<SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult6, SubResult7, SubResult8>> {
			public SequenceParser() {
				super(subParser1, subParser2, subParser3, subParser4, subParser5, subParser6, subParser7, subParser8, description);
			}
			
			// Parser.
			
			@Override
			protected Tuple8<SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult6, SubResult7, SubResult8> combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4, final SubResult5 subResult5, final SubResult6 subResult6, final SubResult7 subResult7, final SubResult8 subResult8) {
				return Tuple8.build(subResult1, subResult2, subResult3, subResult4, subResult5, subResult6, subResult7, subResult8);
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode result = new HashCode(this);
				result.append(_description);
				result.append(_subParser1);
				result.append(_subParser2);
				result.append(_subParser3);
				result.append(_subParser4);
				result.append(_subParser5);
				result.append(_subParser6);
				result.append(_subParser7);
				result.append(_subParser8);
				return result.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final SequenceParser parser = (SequenceParser) object;
					return LangUtils.safeEquals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4) && _subParser5.equals(parser._subParser5) && _subParser6.equals(parser._subParser6) && _subParser7.equals(parser._subParser7) && _subParser8.equals(parser._subParser8);
				} else {
					return false;
				}
			}
		}
		return new SequenceParser();
	}
	
	public static <Token, Value> Parser<Token, List<Value>> separator(final Parser<Token, ? extends Value> valueParser, final Parser<Token, ?> delimiterParser, final String description) {
		return new SeparatorParser<Token, Value>(valueParser, delimiterParser, description);
	}
	
	public static <Token, Value> Parser<Token, List<Value>> separator1(final Parser<Token, ? extends Value> valueParser, final Parser<Token, ?> delimiterParser, final String description) {
		return new SeparatorParser<Token, Value>(valueParser, delimiterParser, 1, Integer.MAX_VALUE, description);
	}
	
	public static <Token, Value> Parser<Token, List<Value>> intersperse(final Parser<Token, ? extends Value> valueParser, final Parser<Token, ? extends Value> delimiterParser, final String description) {
		return new IntersperseParser<Token, Value>(valueParser, delimiterParser, description);
	}
	
	public static <Token, Value> Parser<Token, List<Value>> intersperse1(final Parser<Token, ? extends Value> valueParser, final Parser<Token, ? extends Value> delimiterParser, final String description) {
		return new IntersperseParser<Token, Value>(valueParser, delimiterParser, 1, Integer.MAX_VALUE, description);
	}
	
	public static <Token, SubResult1, SubResult2> Parser<Token, SubResult1> first(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final String description) {
		final class FirstParser
		extends Sequence2Parser<Token, SubResult1, SubResult2, SubResult1> {
			public FirstParser() {
				super(subParser1, subParser2, description);
			}
			
			// Parser.
			
			@Override
			protected SubResult1 combine(final SubResult1 subResult1, final SubResult2 subResult2) {
				return subResult1;
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode result = new HashCode(this);
				result.append(_description);
				result.append(_subParser1);
				result.append(_subParser2);
				return result.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final FirstParser parser = (FirstParser) object;
					return LangUtils.safeEquals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2);
				} else {
					return false;
				}
			}
		}
		return new FirstParser();
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3> Parser<Token, SubResult1> first(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final String description) {
		final class FirstParser
		extends Sequence3Parser<Token, SubResult1, SubResult2, SubResult3, SubResult1> {
			public FirstParser() {
				super(subParser1, subParser2, subParser3, description);
			}
			
			// Parser.
			
			@Override
			protected SubResult1 combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3) {
				return subResult1;
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode result = new HashCode(this);
				result.append(_description);
				result.append(_subParser1);
				result.append(_subParser2);
				result.append(_subParser3);
				return result.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final FirstParser parser = (FirstParser) object;
					return LangUtils.safeEquals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3);
				} else {
					return false;
				}
			}
		}
		return new FirstParser();
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3, SubResult4> Parser<Token, SubResult1> first(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final String description) {
		final class FirstParser
		extends Sequence4Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult1> {
			public FirstParser() {
				super(subParser1, subParser2, subParser3, subParser4, description);
			}
			
			// Parser.
			
			@Override
			protected SubResult1 combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4) {
				return subResult1;
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode result = new HashCode(this);
				result.append(_description);
				result.append(_subParser1);
				result.append(_subParser2);
				result.append(_subParser3);
				result.append(_subParser4);
				return result.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final FirstParser parser = (FirstParser) object;
					return LangUtils.safeEquals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4);
				} else {
					return false;
				}
			}
		}
		return new FirstParser();
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5> Parser<Token, SubResult1> first(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final Parser<Token, ? extends SubResult5> subParser5, final String description) {
		final class FirstParser
		extends Sequence5Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult1> {
			public FirstParser() {
				super(subParser1, subParser2, subParser3, subParser4, subParser5, description);
			}
			
			// Parser.
			
			@Override
			protected SubResult1 combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4, final SubResult5 subResult5) {
				return subResult1;
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode result = new HashCode(this);
				result.append(_description);
				result.append(_subParser1);
				result.append(_subParser2);
				result.append(_subParser3);
				result.append(_subParser4);
				result.append(_subParser5);
				return result.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final FirstParser parser = (FirstParser) object;
					return LangUtils.safeEquals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4) && _subParser5.equals(parser._subParser5);
				} else {
					return false;
				}
			}
		}
		return new FirstParser();
	}
	
	public static <Token, SubResult1, SubResult2> Parser<Token, SubResult2> second(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final String description) {
		final class SecondParser
		extends Sequence2Parser<Token, SubResult1, SubResult2, SubResult2> {
			public SecondParser() {
				super(subParser1, subParser2, description);
			}
			
			// Parser.
			
			@Override
			protected SubResult2 combine(final SubResult1 subResult1, final SubResult2 subResult2) {
				return subResult2;
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode result = new HashCode(this);
				result.append(_description);
				result.append(_subParser1);
				result.append(_subParser2);
				return result.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final SecondParser parser = (SecondParser) object;
					return LangUtils.safeEquals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2);
				} else {
					return false;
				}
			}
		}
		return new SecondParser();
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3> Parser<Token, SubResult2> second(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final String description) {
		final class SecondParser
		extends Sequence3Parser<Token, SubResult1, SubResult2, SubResult3, SubResult2> {
			public SecondParser() {
				super(subParser1, subParser2, subParser3, description);
			}
			
			// Parser.
			
			@Override
			protected SubResult2 combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3) {
				return subResult2;
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode result = new HashCode(this);
				result.append(_description);
				result.append(_subParser1);
				result.append(_subParser2);
				result.append(_subParser3);
				return result.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final SecondParser parser = (SecondParser) object;
					return LangUtils.safeEquals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3);
				} else {
					return false;
				}
			}
		}
		return new SecondParser();
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3, SubResult4> Parser<Token, SubResult2> second(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final String description) {
		final class SecondParser
		extends Sequence4Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult2> {
			public SecondParser() {
				super(subParser1, subParser2, subParser3, subParser4, description);
			}
			
			// Parser.
			
			@Override
			protected SubResult2 combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4) {
				return subResult2;
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode result = new HashCode(this);
				result.append(_description);
				result.append(_subParser1);
				result.append(_subParser2);
				result.append(_subParser3);
				result.append(_subParser4);
				return result.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final SecondParser parser = (SecondParser) object;
					return LangUtils.safeEquals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4);
				} else {
					return false;
				}
			}
		}
		return new SecondParser();
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5> Parser<Token, SubResult2> second(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final Parser<Token, ? extends SubResult5> subParser5, final String description) {
		final class SecondParser
		extends Sequence5Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult2> {
			public SecondParser() {
				super(subParser1, subParser2, subParser3, subParser4, subParser5, description);
			}
			
			// Parser.
			
			@Override
			protected SubResult2 combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4, final SubResult5 subResult5) {
				return subResult2;
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode result = new HashCode(this);
				result.append(_description);
				result.append(_subParser1);
				result.append(_subParser2);
				result.append(_subParser3);
				result.append(_subParser4);
				result.append(_subParser5);
				return result.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final SecondParser parser = (SecondParser) object;
					return LangUtils.safeEquals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4) && _subParser5.equals(parser._subParser5);
				} else {
					return false;
				}
			}
		}
		return new SecondParser();
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3> Parser<Token, SubResult3> third(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final String description) {
		final class ThirdParser
		extends Sequence3Parser<Token, SubResult1, SubResult2, SubResult3, SubResult3> {
			public ThirdParser() {
				super(subParser1, subParser2, subParser3, description);
			}
			
			// Parser.
			
			@Override
			protected SubResult3 combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3) {
				return subResult3;
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode result = new HashCode(this);
				result.append(_description);
				result.append(_subParser1);
				result.append(_subParser2);
				result.append(_subParser3);
				return result.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final ThirdParser parser = (ThirdParser) object;
					return LangUtils.safeEquals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3);
				} else {
					return false;
				}
			}
		}
		return new ThirdParser();
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3, SubResult4> Parser<Token, SubResult3> third(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final String description) {
		final class ThirdParser
		extends Sequence4Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult3> {
			public ThirdParser() {
				super(subParser1, subParser2, subParser3, subParser4, description);
			}
			
			// Parser.
			
			@Override
			protected SubResult3 combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4) {
				return subResult3;
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode result = new HashCode(this);
				result.append(_description);
				result.append(_subParser1);
				result.append(_subParser2);
				result.append(_subParser3);
				result.append(_subParser4);
				return result.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final ThirdParser parser = (ThirdParser) object;
					return LangUtils.safeEquals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4);
				} else {
					return false;
				}
			}
		}
		return new ThirdParser();
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5> Parser<Token, SubResult3> third(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final Parser<Token, ? extends SubResult5> subParser5, final String description) {
		final class ThirdParser
		extends Sequence5Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult3> {
			public ThirdParser() {
				super(subParser1, subParser2, subParser3, subParser4, subParser5, description);
			}
			
			// Parser.
			
			@Override
			protected SubResult3 combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4, final SubResult5 subResult5) {
				return subResult3;
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode result = new HashCode(this);
				result.append(_description);
				result.append(_subParser1);
				result.append(_subParser2);
				result.append(_subParser3);
				result.append(_subParser4);
				result.append(_subParser5);
				return result.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final ThirdParser parser = (ThirdParser) object;
					return LangUtils.safeEquals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4) && _subParser5.equals(parser._subParser5);
				} else {
					return false;
				}
			}
		}
		return new ThirdParser();
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3, SubResult4> Parser<Token, SubResult4> fourth(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final String description) {
		final class FourthParser
		extends Sequence4Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult4> {
			public FourthParser() {
				super(subParser1, subParser2, subParser3, subParser4, description);
			}
			
			// Parser.
			
			@Override
			protected SubResult4 combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4) {
				return subResult4;
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode result = new HashCode(this);
				result.append(_description);
				result.append(_subParser1);
				result.append(_subParser2);
				result.append(_subParser3);
				result.append(_subParser4);
				return result.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final FourthParser parser = (FourthParser) object;
					return LangUtils.safeEquals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4);
				} else {
					return false;
				}
			}
		}
		return new FourthParser();
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5> Parser<Token, SubResult4> fourth(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final Parser<Token, ? extends SubResult5> subParser5, final String description) {
		final class FourthParser
		extends Sequence5Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult4> {
			public FourthParser() {
				super(subParser1, subParser2, subParser3, subParser4, subParser5, description);
			}
			
			// Parser.
			
			@Override
			protected SubResult4 combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4, final SubResult5 subResult5) {
				return subResult4;
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode result = new HashCode(this);
				result.append(_description);
				result.append(_subParser1);
				result.append(_subParser2);
				result.append(_subParser3);
				result.append(_subParser4);
				result.append(_subParser5);
				return result.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final FourthParser parser = (FourthParser) object;
					return LangUtils.safeEquals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4) && _subParser5.equals(parser._subParser5);
				} else {
					return false;
				}
			}
		}
		return new FourthParser();
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5> Parser<Token, SubResult5> fifth(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final Parser<Token, ? extends SubResult5> subParser5, final String description) {
		final class FifthParser
		extends Sequence5Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult5> {
			public FifthParser() {
				super(subParser1, subParser2, subParser3, subParser4, subParser5, description);
			}
			
			// Parser.
			
			@Override
			protected SubResult5 combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4, final SubResult5 subResult5) {
				return subResult5;
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode result = new HashCode(this);
				result.append(_description);
				result.append(_subParser1);
				result.append(_subParser2);
				result.append(_subParser3);
				result.append(_subParser4);
				result.append(_subParser5);
				return result.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final FifthParser parser = (FifthParser) object;
					return LangUtils.safeEquals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4) && _subParser5.equals(parser._subParser5);
				} else {
					return false;
				}
			}
		}
		return new FifthParser();
	}
	
	public static <Token, Result> ChoiceParser<Token, Result> choice(final Parser<Token, ? extends Result> subParser1, final Parser<Token, ? extends Result> subParser2, final String description) {
		return choice(Lists.<Parser<Token, ? extends Result>>fromValues(subParser1, subParser2), description);
	}
	
	public static <Token, Result> ChoiceParser<Token, Result> choice(final Parser<Token, ? extends Result> subParser1, final Parser<Token, ? extends Result> subParser2, final Parser<Token, ? extends Result> subParser3, final String description) {
		return choice(Lists.<Parser<Token, ? extends Result>>fromValues(subParser1, subParser2, subParser3), description);
	}
	
	public static <Token, Result> ChoiceParser<Token, Result> choice(final List<? extends Parser<Token, ? extends Result>> subParsers, final String description) {
		return new ChoiceParser<Token, Result>(subParsers, description);
	}
	
	public static <Token, R1, R2> Parser<Token, Either<R1, R2>> either(final Parser<Token, ? extends R1> leftParser, final Parser<Token, ? extends R2> rightParser, final String description) {
		assert null != leftParser;
		assert null != rightParser;
		
		final Parser<Token, Either<R1, R2>> leftParser_ = map(leftParser, Either.<R1, R2, ParserException>leftFunction(), null);
		final Parser<Token, Either<R1, R2>> rightParser_ = map(rightParser, Either.<R1, R2, ParserException>rightFunction(), null);
		return CoreParsers.choice(leftParser_, rightParser_, description);
	}
	
	private CoreParsers() {
		// Prevents instantiation.
	}
}
