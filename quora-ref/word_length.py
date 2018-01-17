files = ['quora/q' + str(i) + '.txt' for i in range(1, 17) ]

word_count = 0
word_length = 0
for f in files:
    with open(f, 'r') as txt:
        for line in txt:
            words = line.split(' ')
            words = [w.strip().strip(',.;:%!?()[]{}-/\\\n\t\'"').lower() for w in words ]
            words = [w for w in words if w.isalpha()]
            word_count += len(words)
            word_length += sum([ len(list(wd)) for wd in words ])

print(word_length/float(word_count))