The `Parser` module provides a combinator based parser library.

# Motivations #

The development of this library was mainly motivated by the need for reading declarative configurations using multiple small parsers. The modular architecture of the project prevented writing a single monolithic grammar. However, some parsers had to be composed with each other in order to avoid duplication of the production rules.

Another motivation was the wish to experiment with the idea of parallel parsing (like the `GLR` parsers). The idea is to explore the possible productions in parallel as long as they match the grammar. This method provides infinite look-ahead without back-tracking and makes possible to provides very detailed failure feedback.