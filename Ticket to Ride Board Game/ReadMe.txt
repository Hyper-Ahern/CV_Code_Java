In a popular board game, called \Ticket to Ride"1, the goal of the game is to build a rail
network that covers the routes each player is given. A route is specied by the end-point
cities, and is constructed by building segments illustrated on the game board above. For
example, to build a route between Boston and Winnipeg, a player may choose to build the
segments: Boston to Montreal, Montreal to Toronto, Toronto to Duluth, and Duluth to
Winnipeg. The longer the segments the more expensive they are to build, and routes with
more segments take longer to build. Thus, it is in the player's interest to build the routes
they are allocated in the most ecient way possible.

Given a game board of rail segments and a list of routes, your task is to compute the total
cost of building a network of prescribed routes, assuming that the shortest distance for each
route is chosen. For example, given the following game board and routes:

Boston 2 Montreal
Boston 2 New_York
Chicago 4 Toronto
Chicago 3 Pittsburgh
Montreal 3 New_York
Montreal 3 Toronto
New_York 2 Washington
New_York 2 Pittsburgh
Pittsburgh 2 Toronto
Pittsburgh 2 Washington
done
Washington Montreal
Chicago New_York
done

The resulting cost computation would be:

The rail network consists of:
Chicago 3 Pittsburgh
Montreal 3 New_York
New_York 2 Pittsburgh
New_York 2 Washington
The total cost is: 10
