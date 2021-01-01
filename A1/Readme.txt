From my main directory, please use "cd csci3120" to get to where the assignment is. You will find a zip file, the A1.c unziped code, and the Readme.txt. 

I believe the standard gcc -o A1 A1.c should function here as a compiler followed by ./A1 for running the program.

Just a note, I did not allocate dynamic memory on purpose because the instructions were very clear that these were sentences and that
they would be typed. As such, I could never imagine a sentence that would be 1000+ words long and chose not to complicate my program
by using malloc and realloc but I could have by using sizeof(char)*whatever number of characters were typed in. This was by choice and
I don't believe that a static array is a negative thing for a program this simple. I just wanted to make sure you, the marker, understood
it was a desgin choice of simplicity so that no marks are taking off for that choice.
