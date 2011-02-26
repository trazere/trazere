/*
 *  Copyright 2006-2010 Julien Dufour
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
import com.trazere.util.collection.CollectionUtils;
import com.trazere.util.function.Function1;
import com.trazere.util.function.Function2;
import com.trazere.util.lang.HashCode;
import com.trazere.util.lang.LangUtils;
import com.trazere.util.type.Either;
import com.trazere.util.type.Tuple2;
import com.trazere.util.type.Tuple3;
import com.trazere.util.type.Tuple4;
import com.trazere.util.type.Tuple5;
import java.util.List;

/**
 * DOCME
 */
public class CoreParsers {
	private static final EOFParser<?, ?> _EOF = eof("end of file");
	
	public static <Token, Result> Parser<Token, Result> identity(final Parser<Token, Result> subParser, final String description) {
		final class IdentityParser
		extends Sequence1Parser<Token, Result, Result> {
			public IdentityParser() {
				super(subParser, description);
			}
			
			// Parser.
			
			@Override
			protected Result combine(final Result result) {
				return result;
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
					final IdentityParser parser = (IdentityParser) object;
					return LangUtils.equals(_description, parser._description) && _subParser1.equals(parser._subParser1);
				} else {
					return false;
				}
			}
		}
		return new IdentityParser();
	}
	
	public static <Token, SubResult, Result> Parser<Token, Result> lift(final Parser<Token, ? extends SubResult> subParser, final Result result, final String description) {
		final class LiftParser
		extends Sequence1Parser<Token, SubResult, Result> {
			public LiftParser() {
				super(subParser, description);
			}
			
			// Parser.
			
			private final Result _result = result;
			
			@Override
			protected Result combine(final SubResult subResult1) {
				return _result;
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode hashCode = new HashCode(this);
				hashCode.append(_description);
				hashCode.append(_subParser1);
				hashCode.append(_result);
				return hashCode.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final LiftParser parser = (LiftParser) object;
					return LangUtils.equals(_description, parser._description) && _subParser1.equals(parser._subParser1) && LangUtils.equals(_result, parser._result);
				} else {
					return false;
				}
			}
		}
		return new LiftParser();
	}
	
	public static <Token, SubResult, Result> Parser<Token, Result> map(final Parser<Token, ? extends SubResult> subParser, final Function1<? super SubResult, ? extends Result, ? extends ParserException> function, final String description) {
		assert null != function;
		
		final class MapParser
		extends Sequence1Parser<Token, SubResult, Result> {
			public MapParser() {
				super(subParser, description);
			}
			
			// Parser.
			
			private final Function1<? super SubResult, ? extends Result, ? extends ParserException> _function = function;
			
			@Override
			protected Result combine(final SubResult subResult)
			throws ParserException {
				return _function.evaluate(subResult);
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode hashCode = new HashCode(this);
				hashCode.append(_description);
				hashCode.append(_subParser1);
				hashCode.append(_function);
				return hashCode.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final MapParser parser = (MapParser) object;
					return LangUtils.equals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _function.equals(parser._function);
				} else {
					return false;
				}
			}
		}
		return new MapParser();
	}
	
	@SuppressWarnings("unchecked")
	public static <Token, Result> EOFParser<Token, Result> eof() {
		return (EOFParser<Token, Result>) _EOF;
	}
	
	public static <Token, Result> EOFParser<Token, Result> eof(final String description) {
		return new EOFParser<Token, Result>(description);
	}
	
	public static <Token, Result> OptionParser<Token, Result> option(final Parser<Token, Result> subParser, final String description) {
		return new OptionParser<Token, Result>(subParser, description);
	}
	
	public static <Token, Result> ManyParser<Token, Result> many(final Parser<Token, Result> subParser, final String description) {
		return new ManyParser<Token, Result>(subParser, description);
	}
	
	public static <Token, Result> Many1Parser<Token, Result> many1(final Parser<Token, Result> subParser, final String description) {
		return new Many1Parser<Token, Result>(subParser, description);
	}
	
	public static <Token, Result> ManyNParser<Token, Result> manyN(final Parser<Token, Result> subParser, final String description) {
		return new ManyNParser<Token, Result>(subParser, description);
	}
	
	public static <Token, SubResult, Result> FoldParser<Token, SubResult, Result> fold(final Parser<Token, ? extends SubResult> subParser, final Function2<? super Result, ? super SubResult, ? extends Result, ? extends ParserException> function, final Result initialValue, final String description) {
		assert null != function;
		
		final class FoldParser_
		extends FoldParser<Token, SubResult, Result> {
			public FoldParser_() {
				super(subParser, initialValue, description);
			}
			
			// Parser.
			
			private final Function2<? super Result, ? super SubResult, ? extends Result, ? extends ParserException> _function = function;
			
			@Override
			protected Result fold(final Result previousValue, final SubResult subResult)
			throws ParserException {
				return _function.evaluate(previousValue, subResult);
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode hashCode = new HashCode(this);
				hashCode.append(_description);
				hashCode.append(_subParser);
				hashCode.append(_initialValue);
				hashCode.append(_function);
				return hashCode.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final FoldParser_ parser = (FoldParser_) object;
					return LangUtils.equals(_description, parser._description) && _subParser.equals(parser._subParser) && LangUtils.equals(_initialValue, parser._initialValue) && _function.equals(parser._function);
				} else {
					return false;
				}
			}
		}
		return new FoldParser_();
	}
	
	public static <Token, SubResult, Result> Fold1Parser<Token, SubResult, Result> fold1(final Parser<Token, ? extends SubResult> subParser, final Function2<? super Result, ? super SubResult, ? extends Result, ? extends ParserException> function, final Result initialValue, final String description) {
		assert null != function;
		
		final class FoldParser_
		extends Fold1Parser<Token, SubResult, Result> {
			public FoldParser_() {
				super(subParser, initialValue, description);
			}
			
			// Parser.
			
			private final Function2<? super Result, ? super SubResult, ? extends Result, ? extends ParserException> _function = function;
			
			@Override
			protected Result fold(final Result previousValue, final SubResult subResult)
			throws ParserException {
				return _function.evaluate(previousValue, subResult);
			}
			
			// Object.
			
			@Override
			public int hashCode() {
				final HashCode hashCode = new HashCode(this);
				hashCode.append(_description);
				hashCode.append(_subParser);
				hashCode.append(_initialValue);
				hashCode.append(_function);
				return hashCode.get();
			}
			
			@Override
			public boolean equals(final Object object) {
				if (this == object) {
					return true;
				} else if (null != object && getClass().equals(object.getClass())) {
					final FoldParser_ parser = (FoldParser_) object;
					return LangUtils.equals(_description, parser._description) && _subParser.equals(parser._subParser) && LangUtils.equals(_initialValue, parser._initialValue) && _function.equals(parser._function);
				} else {
					return false;
				}
			}
		}
		return new FoldParser_();
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
					return LangUtils.equals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2);
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
					return LangUtils.equals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3);
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
					return LangUtils.equals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4);
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
					return LangUtils.equals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4) && _subParser5.equals(parser._subParser5);
				} else {
					return false;
				}
			}
		}
		return new SequenceParser();
	}
	
	public static <Token, Result> Parser<Token, List<Result>> separator(final Parser<Token, ? extends Result> valueParser, final Parser<Token, ?> delimiterParser, final String description) {
		return new SeparatorParser<Token, Result>(valueParser, delimiterParser, description);
	}
	
	public static <Token, Result> Parser<Token, List<Result>> separator1(final Parser<Token, ? extends Result> valueParser, final Parser<Token, ?> delimiterParser, final String description) {
		return new Separator1Parser<Token, Result>(valueParser, delimiterParser, description);
	}
	
	public static <Token, Result> Parser<Token, List<Result>> separatorN(final Parser<Token, ? extends Result> valueParser, final Parser<Token, ?> delimiterParser, final String description) {
		return new SeparatorNParser<Token, Result>(valueParser, delimiterParser, description);
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
					return LangUtils.equals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2);
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
					return LangUtils.equals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3);
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
					return LangUtils.equals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4);
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
					return LangUtils.equals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4) && _subParser5.equals(parser._subParser5);
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
					return LangUtils.equals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2);
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
					return LangUtils.equals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3);
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
					return LangUtils.equals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4);
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
					return LangUtils.equals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4) && _subParser5.equals(parser._subParser5);
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
					return LangUtils.equals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3);
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
					return LangUtils.equals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4);
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
					return LangUtils.equals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4) && _subParser5.equals(parser._subParser5);
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
					return LangUtils.equals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4);
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
					return LangUtils.equals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4) && _subParser5.equals(parser._subParser5);
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
					return LangUtils.equals(_description, parser._description) && _subParser1.equals(parser._subParser1) && _subParser2.equals(parser._subParser2) && _subParser3.equals(parser._subParser3) && _subParser4.equals(parser._subParser4) && _subParser5.equals(parser._subParser5);
				} else {
					return false;
				}
			}
		}
		return new FifthParser();
	}
	
	public static <Token, Result> ChoiceParser<Token, Result> choice(final Parser<Token, ? extends Result> subParser1, final Parser<Token, ? extends Result> subParser2, final String description) {
		return choice(CollectionUtils.<Parser<Token, ? extends Result>>list(subParser1, subParser2), description);
	}
	
	public static <Token, Result> ChoiceParser<Token, Result> choice(final Parser<Token, ? extends Result> subParser1, final Parser<Token, ? extends Result> subParser2, final Parser<Token, ? extends Result> subParser3, final String description) {
		return choice(CollectionUtils.<Parser<Token, ? extends Result>>list(subParser1, subParser2, subParser3), description);
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
		// Prevent instantiation.
	}
}