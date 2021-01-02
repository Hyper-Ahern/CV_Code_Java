Given a series of "knows" relationships such as "Alice knows Bob", a degree D, and a
person P, outputs the group of people that are within D degrees of separation of person P.
For example, given the input, which consists of a network of acquaintances followed by one
or more queries of the form \degree person" such as:

Alice knows Bob
Carol knows Dave
Carol knows Bob
Bob knows Eve
Eve knows Carol
Eve knows Fred
done
2 Carol
done

The output will be the group of social acquaintances within the specifed degree:

Carol is within 2 degrees of separation from:
Bob
Dave
Eve
