# Project 2 (uMessage) Feedback #
	
## CSE 332 Autumn 2020 ##
	

	
Team: John Li (johnli7) and Arpan Singh (arpans)
	
<br>
	

	
## Unit Tests ##

**MinFourHeap** ∞ Timeout `(16/18)`
> ✓ Passed *testHasWork* <br>
> ✓ Passed *testHasWorkAfterAdd* <br>
> ✓ Passed *testHasWorkAfterAddRemove* <br>
> ✓ Passed *testPeekHasException* <br>
> ✓ Passed *testNextHasException* <br>
> ✓ Passed *testClear* <br>
> ✓ Passed *checkStructure* <br>
> ✓ Passed *testHeapWith5Items* <br>
> ✓ Passed *testHugeHeap* <br>
> ✓ Passed *testOrderingDoesNotMatter* <br>
> ✓ Passed *testWithCustomComparable* <br>
> ✓ Passed *testStructureInorderInput* <br>
> ✓ Passed *testStructureReverseOrderInput* <br>
> ✓ Passed *testStructureInterleavedInput* <br>
> ✓ Passed *testStructureRandomInput* <br>
> ✓ Passed *testStructureWithDups* <br>
> `∞ Timeout` *stressTest* <br>
> `∞ Timeout` *fuzzyStressTest* <br>

**AVLTree**  `(6/6)`
> ✓ Passed *initialize* <br>
> ✓ Passed *insert* <br>
> ✓ Passed *getters* <br>
> ✓ Passed *sorted_duplicate_input* <br>
> ✓ Passed *unsorted_duplicate_input* <br>
> ✓ Passed *structure* <br>

**MoveToFrontList**  `(6/6)`
> ✓ Passed *initialize* <br>
> ✓ Passed *insert* <br>
> ✓ Passed *getters* <br>
> ✓ Passed *sorted_duplicate_input* <br>
> ✓ Passed *unsorted_duplicate_input* <br>
> ✓ Passed *structure* <br>

**HashTable**  `(6/6)`
> ✓ Passed *initialize* <br>
> ✓ Passed *insert* <br>
> ✓ Passed *getters* <br>
> ✓ Passed *sorted_duplicate_input* <br>
> ✓ Passed *unsorted_duplicate_input* <br>
> ✓ Passed *comparator* <br>
> ✓ Passed *negative_hash_codes* <br>

**HeapSort**  `(5/5)`
> ✓ Passed *integer_inorder* <br>
> ✓ Passed *integer_reverseorder* <br>
> ✓ Passed *integer_interleaved* <br>
> ✓ Passed *integer_random* <br>
> ✓ Passed *string* <br>
> ✓ Passed *dataCount_string* <br>

**QuickSort**  `(5/5)`
> ✓ Passed *integer_inorder* <br>
> ✓ Passed *integer_reverseorder* <br>
> ✓ Passed *integer_interleaved* <br>
> ✓ Passed *integer_random* <br>
> ✓ Passed *string* <br>
> ✓ Passed *dataCount_string* <br>

**TopKSort**  `(6/6)`
> ✓ Passed *integer_random_100* <br>
> ✓ Passed *string_20* <br>
> ✓ Passed *dataCount_ngram_counts_inorder* <br>
> ✓ Passed *dataCount_ngram_counts_reverseorder* <br>
> ✓ Passed *dataCount_ngram_counts_interleaved* <br>
> ✓ Passed *dataCount_ngram_counts_random* <br>

**CircularArrayHashCode**  `(0/2)`
> ✓ Passed *generate_hash_codes* <br>
> `✘ Failed` *hash_overlap* <br>
> `✘ Failed` *high_variance* <br>
> `✘ Failed` *with_null_chars* <br>

**CircularArrayComparator**  `(2/2)`
> ✓ Passed *vary_length* <br>
> ✓ Passed *vary_order* <br>

**NGramToNextChoicesMap** ✝ Exception `(0/2)`
> `✝ Exception` *poem* <br>
> `.util.NoSuchElementException` *java* <br>
> `✝ Exception` *large* <br>
> `.util.NoSuchElementException` *java* <br>

**UMessage** ✝ Exception `(2/6)`
> ✓ Passed *simple_HashTable_AVL* <br>
> `✝ Exception` *simple_HashTable_HashTable* <br>
> `✘ Failed` *simple_HashTable_AVL_topk* <br>
> `✝ Exception` *simple_HashTable_HashTable_topk* <br>
> ✓ Passed *poem_HashTable_AVL* <br>
> ✓ Passed *poem_HashTable_HashTable* <br>
> ✓ Passed *poem_HashTable_MTF* <br>
> `✘ Failed` *poem_HashTable_AVL_topk* <br>
> `✘ Failed` *poem_HashTable_HashTable_topk* <br>
> `✘ Failed` *poem_HashTable_MTF_topk* <br>
> `✘ Failed` *large_HashTable_AVL_topk* <br>
> `✘ Failed` *large_HashTable_HashTable_topk* <br>

## Miscellaneous ##
`(-3/0)` Your AVL tree has redundant rotation methods
