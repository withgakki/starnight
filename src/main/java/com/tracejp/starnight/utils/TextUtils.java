package com.tracejp.starnight.utils;

import com.hankcs.hanlp.HanLP;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/6/23 18:36
 */
public class TextUtils {

    private static final String SPLIT_FILTER = "+-!\"`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？ ";

    /**
     * 相似度计算 - TF-IDF算法
     */
    public static double computeTFIDF(String s1, String s2) {
        if (StringUtils.isEmpty(s1) || StringUtils.isEmpty(s2)) {
            return 0.0;
        }
        Map<String, Integer> wordFreq1 = computeTermFrequency(s1);
        Map<String, Integer> wordFreq2 = computeTermFrequency(s2);

        // 从两个文本中获取所有独特的单词
        Set<String> words = new HashSet<>();
        words.addAll(wordFreq1.keySet());
        words.addAll(wordFreq2.keySet());

        // 计算两个文本的 TF-IDF 向量
        Map<String, Double[]> tfidfVectors = new HashMap<>();

        for (String word : words) {
            int freq1 = wordFreq1.getOrDefault(word, 0);
            int freq2 = wordFreq2.getOrDefault(word, 0);

            double tf1 = freq1 / (double) s1.length();
            double tf2 = freq2 / (double) s2.length();

            double idf = Math.log(2.0 / (computeTermFrequency(word, s1) + computeTermFrequency(word, s2)));

            tfidfVectors.put(word, new Double[]{tf1 * idf, tf2 * idf});
        }

        // 计算两个 TF-IDF 向量之间的余弦相似性
        double numerator = 0.0;
        double denominator1 = 0.0;
        double denominator2 = 0.0;

        for (Double[] tfidf : tfidfVectors.values()) {
            numerator += tfidf[0] * tfidf[1];
            denominator1 += tfidf[0] * tfidf[0];
            denominator2 += tfidf[1] * tfidf[1];
        }

        double denominator = Math.sqrt(denominator1) * Math.sqrt(denominator2);

        if (denominator == 0.0) {
            return 0.0;
        }

        return numerator / denominator;
    }

    /**
     * 词频计算
     */
    public static Map<String, Integer> computeTermFrequency(String document) {
        Map<String, Integer> termFrequency = new HashMap<>();
        for (String word : splitWords(document)) {
            termFrequency.merge(word, 1, Integer::sum);
        }
        return termFrequency;
    }

    public static int computeTermFrequency(String word, String corpus) {
        int count = 0, index = 0;
        while ((index = corpus.indexOf(word, index)) != -1) {
            count++;
            index += word.length();
        }
        return count;
    }

    /**
     * 中文分词
     *
     * @param sentence 句子
     * @return 分词结果
     */
    public static List<String> splitWords(String sentence) {
        return HanLP.segment(sentence).stream()
                .map(item -> item.word)
                .filter(s -> !SPLIT_FILTER.contains(s))
                .collect(Collectors.toList());
    }

}
