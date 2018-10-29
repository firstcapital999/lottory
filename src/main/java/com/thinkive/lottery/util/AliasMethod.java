package com.thinkive.lottery.util;

import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.Random;

/**
 * @描述: AliasMethod 算法，摘自网络。算法论文地址：http://www.keithschwarz.com/darts-dice-coins/
 * 中文翻译地址：http://www.doc88.com/p-947522544141.html
 * @版权: Copyright (c) 2016
 * @公司: 思迪科技
 * @作者: 曹友安
 * @版本: 1.0
 * @创建日期: 2016年6月16日
 * @创建时间: 上午10:38:33
 */
public class AliasMethod {

    private final double[] probability;

    private final int[] alias;

    private final int length;

    private final Random rand;

    public AliasMethod(List<Double> prob) {
        this(prob, new Random());
    }

    public AliasMethod(List<Double> prob, Random rand) {
        /* Begin by doing basic structural checks on the inputs. */
        if (prob == null || rand == null) {
            throw new NullPointerException();
        }
        if (prob.size() == 0) {
            throw new IllegalArgumentException("Probability vector must be nonempty.");
        }

        this.rand = rand;
        this.length = prob.size();
        this.probability = new double[length];
        this.alias = new int[length];

        double[] probtemp = new double[length];
        Deque<Integer> small = new ArrayDeque<Integer>();
        Deque<Integer> large = new ArrayDeque<Integer>();

        /* divide elements into 2 groups by probability */
        for (int i = 0; i < length; i++) {
            probtemp[i] = prob.get(i) * length; /* initial probtemp */
            if (probtemp[i] < 1.0) {
                small.add(i);
            }
            else {
                large.add(i);
            }
        }

        while (!small.isEmpty() && !large.isEmpty()) {
            int less = small.pop();
            int more = large.pop();
            probability[less] = probtemp[less];
            alias[less] = more;
            probtemp[more] = probtemp[more] - (1.0 - probability[less]);
            if (probtemp[more] < 1.0) {
                small.add(more);
            }
            else {
                large.add(more);
            }
        }
        /*
         * At this point, everything is in one list, which means that the
         * remaining probabilities should all be 1/n. Based on this, set them
         * appropriately.
         */
        while (!small.isEmpty()) {
            probability[small.pop()] = 1.0;
        }
        while (!large.isEmpty()) {
            probability[large.pop()] = 1.0;
        }
    }

    /**
     * Samples a value from the underlying distribution.
     */
    public int next() {
        /* Generate a fair die roll to determine which column to inspect. */
        int column = rand.nextInt(length);

        /* Generate a biased coin toss to determine which option to pick. */
        boolean coinToss = rand.nextDouble() < probability[column];

        /* Based on the outcome, return either the column or its alias. */
        return coinToss ? column : alias[column];
    }

    /* 概率测试 */
    public static void main(String[] argv) {
        for (int i = 0; i < 20; i++) {

            List<Double> prob = new ArrayList<Double>();

            prob.add(0.00008); /* 0.01% 几率命中 */
            prob.add(0.00016); /* 50% 几率命中     */
            prob.add(0.00016);
            prob.add(0.00125);
            prob.add(0.00764);
            prob.add(0.99071);
            int[] cnt = new int[prob.size()];

            AliasMethod am = new AliasMethod(prob);

            for (int w = 0; w < 4; w++) {
                cnt[am.next()]++;
            }

            StringBuffer lent = new StringBuffer();
            for (int j = 0; j < cnt.length; j++) {
                lent.append(cnt[j] + "|");
                SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println(data.format(new Date()));
            }
            System.out.println(lent + "");
        }
    }
}
