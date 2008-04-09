/*
 *  Copyright 2008 Julien Dufour
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
package com.trazere.util.parser.core;

import com.trazere.util.collection.CollectionUtils;
import com.trazere.util.parser.Parser;
import com.trazere.util.type.Tuple2;
import com.trazere.util.type.Tuple3;
import com.trazere.util.type.Tuple4;
import com.trazere.util.type.Tuple5;
import java.util.List;

/**
 * DOCME
 */
public class CoreParsers {
	private static final EOFParser<?, ?> EOF = eof("end of file");
	
	@SuppressWarnings("unchecked")
	public static <Token, Result> EOFParser<Token, Result> eof() {
		return (EOFParser<Token, Result>) EOF;
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
	
	public static <Token, SubResult1, SubResult2> Parser<Token, Tuple2<SubResult1, SubResult2>> sequence(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final String description) {
		return new Combine2Parser<Token, SubResult1, SubResult2, Tuple2<SubResult1, SubResult2>>(subParser1, subParser2, description) {
			@Override
			protected Tuple2<SubResult1, SubResult2> combine(final SubResult1 subResult1, final SubResult2 subResult2) {
				return Tuple2.build(subResult1, subResult2);
			}
		};
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3> Parser<Token, Tuple3<SubResult1, SubResult2, SubResult3>> sequence(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final String description) {
		return new Combine3Parser<Token, SubResult1, SubResult2, SubResult3, Tuple3<SubResult1, SubResult2, SubResult3>>(subParser1, subParser2, subParser3, description) {
			@Override
			protected Tuple3<SubResult1, SubResult2, SubResult3> combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3) {
				return Tuple3.build(subResult1, subResult2, subResult3);
			}
		};
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3, SubResult4> Parser<Token, Tuple4<SubResult1, SubResult2, SubResult3, SubResult4>> sequence(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final String description) {
		return new Combine4Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, Tuple4<SubResult1, SubResult2, SubResult3, SubResult4>>(subParser1, subParser2, subParser3, subParser4, description) {
			@Override
			protected Tuple4<SubResult1, SubResult2, SubResult3, SubResult4> combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4) {
				return Tuple4.build(subResult1, subResult2, subResult3, subResult4);
			}
		};
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5> Parser<Token, Tuple5<SubResult1, SubResult2, SubResult3, SubResult4, SubResult5>> sequence(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final Parser<Token, ? extends SubResult5> subParser5, final String description) {
		return new Combine5Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, Tuple5<SubResult1, SubResult2, SubResult3, SubResult4, SubResult5>>(subParser1, subParser2, subParser3, subParser4, subParser5, description) {
			@Override
			protected Tuple5<SubResult1, SubResult2, SubResult3, SubResult4, SubResult5> combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4, final SubResult5 subResult5) {
				return Tuple5.build(subResult1, subResult2, subResult3, subResult4, subResult5);
			}
		};
	}
	
	public static <Token, Result> Parser<Token, List<Result>> intersperse(final Parser<Token, ? extends Result> valueParser, final Parser<Token, ?> delimiterParser, final String description) {
		return new IntersperseParser<Token, Result>(valueParser, delimiterParser, description);
	}
	
	public static <Token, Result> Parser<Token, List<Result>> intersperse1(final Parser<Token, ? extends Result> valueParser, final Parser<Token, ?> delimiterParser, final String description) {
		return new Intersperse1Parser<Token, Result>(valueParser, delimiterParser, description);
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
	
	public static <Token, SubResult, Result> Parser<Token, Result> lift(final Parser<Token, ? extends SubResult> subParser, final Result result, final String description) {
		return new Combine1Parser<Token, SubResult, Result>(subParser, description) {
			@Override
			protected Result combine(final SubResult subResult1) {
				return result;
			}
		};
	}
	
	public static <Token, SubResult1, SubResult2> Parser<Token, SubResult1> first(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final String description) {
		return new Combine2Parser<Token, SubResult1, SubResult2, SubResult1>(subParser1, subParser2, description) {
			@Override
			protected SubResult1 combine(final SubResult1 subResult1, final SubResult2 subResult2) {
				return subResult1;
			}
		};
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3> Parser<Token, SubResult1> first(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final String description) {
		return new Combine3Parser<Token, SubResult1, SubResult2, SubResult3, SubResult1>(subParser1, subParser2, subParser3, description) {
			@Override
			protected SubResult1 combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3) {
				return subResult1;
			}
		};
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3, SubResult4> Parser<Token, SubResult1> first(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final String description) {
		return new Combine4Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult1>(subParser1, subParser2, subParser3, subParser4, description) {
			@Override
			protected SubResult1 combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4) {
				return subResult1;
			}
		};
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5> Parser<Token, SubResult1> first(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final Parser<Token, ? extends SubResult5> subParser5, final String description) {
		return new Combine5Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult1>(subParser1, subParser2, subParser3, subParser4, subParser5, description) {
			@Override
			protected SubResult1 combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4, final SubResult5 subResult5) {
				return subResult1;
			}
		};
	}
	
	public static <Token, SubResult1, SubResult2> Parser<Token, SubResult2> second(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final String description) {
		return new Combine2Parser<Token, SubResult1, SubResult2, SubResult2>(subParser1, subParser2, description) {
			@Override
			protected SubResult2 combine(final SubResult1 subResult1, final SubResult2 subResult2) {
				return subResult2;
			}
		};
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3> Parser<Token, SubResult2> second(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final String description) {
		return new Combine3Parser<Token, SubResult1, SubResult2, SubResult3, SubResult2>(subParser1, subParser2, subParser3, description) {
			@Override
			protected SubResult2 combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3) {
				return subResult2;
			}
		};
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3, SubResult4> Parser<Token, SubResult2> second(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final String description) {
		return new Combine4Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult2>(subParser1, subParser2, subParser3, subParser4, description) {
			@Override
			protected SubResult2 combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4) {
				return subResult2;
			}
		};
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5> Parser<Token, SubResult2> second(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final Parser<Token, ? extends SubResult5> subParser5, final String description) {
		return new Combine5Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult2>(subParser1, subParser2, subParser3, subParser4, subParser5, description) {
			@Override
			protected SubResult2 combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4, final SubResult5 subResult5) {
				return subResult2;
			}
		};
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3> Parser<Token, SubResult3> third(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final String description) {
		return new Combine3Parser<Token, SubResult1, SubResult2, SubResult3, SubResult3>(subParser1, subParser2, subParser3, description) {
			@Override
			protected SubResult3 combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3) {
				return subResult3;
			}
		};
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3, SubResult4> Parser<Token, SubResult3> third(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final String description) {
		return new Combine4Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult3>(subParser1, subParser2, subParser3, subParser4, description) {
			@Override
			protected SubResult3 combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4) {
				return subResult3;
			}
		};
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5> Parser<Token, SubResult3> third(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final Parser<Token, ? extends SubResult5> subParser5, final String description) {
		return new Combine5Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult3>(subParser1, subParser2, subParser3, subParser4, subParser5, description) {
			@Override
			protected SubResult3 combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4, final SubResult5 subResult5) {
				return subResult3;
			}
		};
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3, SubResult4> Parser<Token, SubResult4> fourth(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final String description) {
		return new Combine4Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult4>(subParser1, subParser2, subParser3, subParser4, description) {
			@Override
			protected SubResult4 combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4) {
				return subResult4;
			}
		};
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5> Parser<Token, SubResult4> fourth(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final Parser<Token, ? extends SubResult5> subParser5, final String description) {
		return new Combine5Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult4>(subParser1, subParser2, subParser3, subParser4, subParser5, description) {
			@Override
			protected SubResult4 combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4, final SubResult5 subResult5) {
				return subResult4;
			}
		};
	}
	
	public static <Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5> Parser<Token, SubResult5> fifth(final Parser<Token, ? extends SubResult1> subParser1, final Parser<Token, ? extends SubResult2> subParser2, final Parser<Token, ? extends SubResult3> subParser3, final Parser<Token, ? extends SubResult4> subParser4, final Parser<Token, ? extends SubResult5> subParser5, final String description) {
		return new Combine5Parser<Token, SubResult1, SubResult2, SubResult3, SubResult4, SubResult5, SubResult5>(subParser1, subParser2, subParser3, subParser4, subParser5, description) {
			@Override
			protected SubResult5 combine(final SubResult1 subResult1, final SubResult2 subResult2, final SubResult3 subResult3, final SubResult4 subResult4, final SubResult5 subResult5) {
				return subResult5;
			}
		};
	}
	
	private CoreParsers() {
		// Prevent instantiation.
	}
}
