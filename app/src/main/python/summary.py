
# def nlp():
#     from transformers import pipeline
#     summarizer = pipeline("summarization", model="knkarthick/bart-large-xsum-samsum")
#     conversation = '''Jeff: Can I train a 🤗 Transformers model on Amazon SageMaker? 
#     Philipp: Sure you can use the new Hugging Face Deep Learning Container. 
#     Jeff: ok.
#     Jeff: and how can I get started? 
#     Jeff: where can I find documentation? 
#     Philipp: ok, ok you can find everything here. https://huggingface.co/blog/the-partnership-amazon-sagemaker-and-hugging-face                                           
#     '''
#     return summarizer(conversation)


def main():
    import nltk
    from nltk.corpus import stopwords
    from nltk.tokenize import word_tokenize, sent_tokenize

    text = '''Jeff: Can I train a 🤗 Transformers model on Amazon SageMaker? 
        Philipp: Sure you can use the new Hugging Face Deep Learning Container. 
        Jeff: ok.
        Jeff: and how can I get started? 
        Jeff: where can I find documentation? 
        Philipp: ok, ok you can find everything here. https://huggingface.co/blog/the-partnership-amazon-sagemaker-and-hugging-face                                           
        '''

    stopwords = set(stopwords.words("english"))
    words = word_tokenize(text)

    freqTable = dict()
    for word in words:
        word= word.lower()
        if word in stopwords:
            continue
        if word in freqTable:
            freqTable[word] += 1
        else:
            freqTable[word] = 1

    sentences = sent_tokenize(text)
    sentenceValue = dict()

    for sentence in sentences:
        for word, freq in freqTable.items():
            if word in sentence.lower():
                if sentence in sentenceValue:
                    sentenceValue[sentence] +=1
                else:
                    sentenceValue[sentence] =1

    sumValues = 0
    for sentence in sentenceValue:
        sumValues += sentenceValue[sentence]

    average = int(sumValues/len(sentenceValue))

    summary = ''

    for sentence in sentences:
        if(sentence in sentenceValue) and (sentenceValue[sentence] >(1.2*average)):
            summary += ' '+sentence
    return summary