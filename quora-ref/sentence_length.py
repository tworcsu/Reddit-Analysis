def get_word_count(f):
    with open(f, 'r') as txt:
        word_count = 0
        for line in txt:
            words = line.split(' ')
            words = [w.strip().strip(',.;:%!?()[]{}-/\\\n\t\'"').lower() for w in words ]
            words = [w for w in words if w.isalpha()]
            word_count += len(words)
        return word_count

def get_sentence_score(f):
    sentence_count = 0
    with open(f, 'r') as txt:
        for line in txt:
            line_mod = line.replace('?', '.')
            line_mod = line_mod.replace('!', '.')
            line_mod = line_mod.replace('\n', '.')
            sentences = line_mod.split('.')
            sentences = [ s.strip() for s in sentences if s.strip() != '']
            sentence_count += len(sentences)
    return sentence_count

files = ['quora/q' + str(i) + '.txt' for i in range(1, 17) ]

tot_s = 0
tot_w = 0
for f in files:
    tot_s += get_sentence_score(f)
    tot_w += get_word_count(f)

print(tot_w/float(tot_s))