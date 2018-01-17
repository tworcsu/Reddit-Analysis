def load_rare_words():
    rare_words = []
    with open('uncommon.txt') as f:
        for w in f:
            rare_words.append(w.strip())
    return rare_words

rare_counter = {}

def get_rarity_score(f, rare_words):
    with open(f, 'r') as txt:
        rare_count = 0
        total_count = 0
        for line in txt:
            words = line.split(' ')
            words = [w.strip().strip(',.;:%!?()[]{}-/\\\n\t\'"').lower() for w in words ]
            words = [w for w in words if w.isalpha()]
            total_count += len(words)

            for w in words:
                if w not in rare_words:
                    if w in rare_counter:
                        rare_counter[w] += 1
                    else:
                        rare_counter[w] = 1
                    rare_count += 1
        return total_count, rare_count

rare_words = load_rare_words()
files = ['quora/q' + str(i) + '.txt' for i in range(1, 17) ]

twords = 0
rwords = 0
for f in files:
    words, rare = get_rarity_score(f, rare_words)
    twords += words
    rwords += rare

for k,v in rare_counter.items():
    print(k, v)
print(rwords/float(twords))
print(rwords/float(len(files)))