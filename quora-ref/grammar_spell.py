import language_tool

def get_grammar_score(f, lang_checker):
    error_count = 0
    total_count = 0
    with open(f, 'r') as txt:
        for line in txt:
            words = line.split(' ')
            words = [w.strip().strip(',.;:%!?()[]{}-/\\\n\t\'"').lower() for w in words]
            words = [w for w in words if w.isalpha()]
            total_count += len(words)
            errors = lang_checker.check(line)
            error_count += len(errors)
    return total_count, error_count

lang_checker = language_tool.LanguageTool("en-US")
files = ['quora/q' + str(i) + '.txt' for i in range(1, 17) ]

twords = 0
errors = 0
for f in files:
    words, e = get_grammar_score(f, lang_checker)
    twords += words
    errors += e

print(errors/float(twords))
print(errors/float(len(files)))