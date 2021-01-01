/*
 * Hyper Ahern
 *
 * 3120 -Assignment 3
 *
 * Oct 26th, 2020
 *
 * This program takes in a text file with up to 500 integers, creates 2 threads, sends half of the original list to
 * each, sorts them in halves within each of the threads, and then sends it to a final thread to have the halves
 * merged into a final sorted array.
 *
 *                            NOTE:
 * I use single letter variables because each one is used for ONLY that loop. Usually I would never name my variables a
 * single letter but in this instance, it was more efficient. Please also note that we were allowed to use a sorting
 * algorithm and a merger algorithm that was not our own as long as we credit it. The objective of this assignment was
 * not about the sorting but instead about the pthreads. All credit is found at the bottom of the code and includes all
 * the websites and resources I used. Finally, please note that I used the same variables and the same function names
 * and the same structure as the skeleton that was given in the assignment because I was instructed to so I copied and
 * pasted it to use as a reference. I kept the built in comments as well.
 *
 */

#include <stdlib.h>
#include <pthread.h>
#include <string.h>
#include <stdio.h>

void *sorter(void *parameter_input);    /* thread that performs basic sorting algorithm */
void *merger(void *parameter_input);    /* thread that performs merging of results */

int list[500];
int number_of_elements;
int sorted_list[500];

typedef struct{
    int starting_index;
    int ending_index;
} parameters;

int main (int argc, const char * argv[]){

    // Opening the file IntegerList.txt
    char *token_for_strtok;
    char temp_array[500];
    FILE *integer_list_txt;
    integer_list_txt = fopen("IntegerList.txt", "r");

    // Scanning and placing the contents of IntegerList.txt into a temporary array delimited by commas
    fscanf(integer_list_txt, "%s", temp_array);
    token_for_strtok = strtok(temp_array, ",");
    int q=0;

    // Iterating through the rest of the IntegerList.txt file and force converting strings to integers using Atoi
    while(token_for_strtok != NULL) {
        list[q] = atoi(token_for_strtok);
        token_for_strtok = strtok(NULL, ",");
        q++;
    }

    // This is to track the actual number of elements in the file (up to a maximum of 100 according to the assignment)
    number_of_elements = q;

    // Creating the threads
    pthread_t thread[3];

    /* create the first sorting thread */
    parameters *list_of_numbers = (parameters *) malloc (sizeof(parameters));
    (*list_of_numbers).starting_index = 0;
    (*list_of_numbers).ending_index = number_of_elements / 2;
    pthread_create(&thread[0], 0, sorter, list_of_numbers);

    /* create the second sorting thread */
    list_of_numbers = (parameters *) malloc (sizeof(parameters));
    (*list_of_numbers).starting_index = (number_of_elements / 2 + 1);
    (*list_of_numbers).ending_index = number_of_elements;
    pthread_create(&thread[1], 0, sorter, list_of_numbers);

    // "now wait for the 2 sorting threads to finish"
    int a;
    for (a = 0; a < 3 - 1; a++){
        pthread_join(thread[a], NULL);
    }

    // Freeing memory
    free(list_of_numbers);

    // create the merge thread
    list_of_numbers = (parameters *) malloc(sizeof(parameters));
    (*list_of_numbers).starting_index = 0;
    (*list_of_numbers).ending_index = (499);
    pthread_create(&thread[2], 0, merger, list_of_numbers);

    // "wait for the merge thread to finish"
    pthread_join(thread[2], NULL);

    // Freeing memory
    free(list_of_numbers);


    // "output the sorted array"
    printf("\nThe Output of IntegerList.txt in sorted order is: \n");
    int print=0;

    while(print<500){
        if(sorted_list[print] != 0){
            printf("%d\n", sorted_list[print]);
        }
        print++;
    }
    printf("\n");

    return 0;
}

/* Bubble Sort
 *
 * Since I am not worried about efficiency in this program (due to there being only a maxiumum of 500 input size), I
 * decided to go with a basic brute force algorithm. It iterates through each element, compares it to the next one, then
 * it swaps places with the next item in the list until it is sorted. I could have used a recursive call to do divide
 * and conquer or some other sorting algorithm like mergesort, etc. if I wanted nlog(n) time but this was simpler
 *
 */
void *sorter(void *parameter_input){

    parameters* input = (parameters *)parameter_input;

    //SORT
    int starting_index = (*input).starting_index;
    int ending_index = (*input).ending_index + 1;
    int e= starting_index;
    int f = starting_index;
    int temp_var;

    while(e < ending_index){
        while(f < ending_index - e - 1){
            int next = f + 1;
            if(list[f] > list[next]){
                temp_var = list[f];
                list[f] = list[next];
                list[next] = temp_var;
            }
            f++;
        }
        e++;
    }

    int b;
    for(b=starting_index; b < ending_index; b++){
        sorted_list[b]=list[b] ;
    }
    pthread_exit(0);
}

/* Brute force merging
 *
 * I decided again since the maximum input is 500, to simply use a very basic but inefficient merging algorithm.
 *
 */
void *merger(void *parameter_input){

    parameters* input = (parameters *)parameter_input;
    int starting_index = (*input).starting_index;
    int ending_index = (*input).ending_index + 1;
    int c= starting_index;
    int d = starting_index;
    int temp_var;

    // Doing the actual merging using Brute force
    while(c < ending_index){
        for(d; d < ending_index - c; d++){
            int next = d + 1;
            if(sorted_list[d] > sorted_list[next]){
                temp_var = sorted_list[d];
                sorted_list[d] = sorted_list[next];
                sorted_list[next] = temp_var;
            }
        }

        c++;
    }
    pthread_exit(0);
}


/*
 * I used the template that was given by Dr. Ye, the templates that were given by Patricia (our TA) on the tutorials,
 * and several online resources that are listed below:
 *
 * All of the given help documentation by Dr. Ye.
 *
 * https://www.youtube.com/watch?v=lQP4X3odvHE   - To re- learn about memory management
 *
 * https://www.youtube.com/watch?v=pB-nvbCg3yc   - To re- learn about array of strings and used concatenation method
 *
 * https://stackoverflow.com/questions/23961147/implicit-declaration-of-function-strtok-r-wimplicit-
 * function-declaration-in - To learn about an error in strtok
 *
 * https://stackoverflow.com/questions/30065675/what-does-scanf-nc-mean    - To learn about how to scanf properly which
 * is where I got the scanf ("%[^\n]%*c", array_temp); and followed the docs to learn to parse with strtok.
 *
 * https://stackoverflow.com/questions/43102842/reading-integers-from-txt-file-in-c (I took a few lines of
 * code to assist with tokens)
 *
 * https://www.geeksforgeeks.org/static-variables-in-c/ & https://www.tutorialspoint.com/cprogramming/c_scope_rules.htm
 * for help with static variables in C
 *
 *  https://www.hegg.com/homework-help/questions-and-answers/write-multithreaded-sorting-program-c-works-follows-list-
 *  integers-divided-two-smaller-list-q34985369 -  Took a few lines of code from here and also used it, with Dr.Ye's
 *  template to think about how to approach the problem. Took time off after looking at it to avoid plagerism.
 *
 * https://stackoverflow.com/questions/8257714/how-to-convert-an-int-to-string-in-c#:~:text=You%20can%20use%20
 * itoa(),to%20convert%20any%20value%20beforehand.  - For help with atoi()/sprintf() (converting int to string)
 *
 * https://stackoverflow.com/questions/12944758/reading-integers-from-a-file-in-c - for assitance with reading files.
 *
 *https://www.youtube.com/watch?v=hiG5G2caZ38 - took a few lines of code form here to help with reading a file and using
 * strtok() on commas as a delimiter.
 *
 * https://www.random.org/integer-sets/?sets=1&num=500&min=1&max=500&commas=on&order=index&format=html&rnd=new - to get
 * 500 random numbers generated seperated by commas for testing
 *
 * https://stackoverflow.com/questions/53392304/writing-an-array-of-integers-into-a-file-using-c - for attempting to 
 * write to a file (unsuccessfully) and for a few pointers on reading files
 */
