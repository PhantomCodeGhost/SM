# Write a Python program to create a tuple using two different tuples

tuple1 = (1, 2, 3, 4, 5)
tuple2 = (6, 7, 8, 9)

combined = tuple1 + tuple2
print(combined)

# Q.2) Write a Python program to sort (ascending and descending) a dictionary by value

data = {"Apple":5, "Mango":7, "Grapes":1,"Banana":2}

asc_sort =  dict(sorted(data.items(), key=lambda x:x[1]))
desc_sort = dict(sorted(data.items(), key=lambda x:x[1], reverse=True))

print(asc_sort)
print(desc_sort)

# ------------------- OR --------------------------

# Q.2) Write a Python program to count the occurrences of each word in a given sentence.

sentence = "This is a dog"

words = sentence.lower().split()

word_count = {}
for word in words:
    word_count[word] = word_count.get(word, 0)+1
print(word_count) 
